package com.gmail.naodroid.reakt.ext

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/21.
 */

//----------------------------------------------------------------
//view size
public val fill : Int = android.view.ViewGroup.LayoutParams.MATCH_PARENT
public val wrap : Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT

public fun Context.dip(value: Int): Int =
		(value * (getResources()?.getDisplayMetrics()?.density ?: 0f)).toInt()
public fun Context.dip(value: Float): Int =
		(value * (getResources()?.getDisplayMetrics()?.density ?: 0f)).toInt()

public fun View.dip(value: Int): Int = getContext().dip(value)
public fun View.dip(value: Float): Int = getContext().dip(value)

//convert HEX RGB to ARGB.
// ex: 0xFFFFF -> 0xFFFFFFFF
public fun convertRGB(color : Int) : Int {
	if (color == 0) {
		//transparent
		return color
	} else if ((color ushr 24) == 0) {
		//add 0xFF00000
		return (0xFF shl 24) or color
	}
	return color
}

/**
 * call this function when creating new Reakt view method.
 * like this
 * fun Reakt.customView(block : CustomView.() -> ()) : CustomView {
 *     val view = new CustomView(this.context)
 *     //custom your view
 *     commonBlock(view, block)
 *     return view
 * }
 * 
 */
fun Reakt.commonProcess<T : View>(view : T, style : ViewStyle<in T>?, block : T.() -> Unit) : Unit {
	//add to parent
	val parent = this.currentViewGroup()
	if (parent != null) {
		parent.addView(view)
	}
	//if viewgroup, then register to reakt as current ViewGroup
	val isGroup = view is ViewGroup
	if (isGroup) {
		this.pushStack(view as ViewGroup)
	}
	//apply style. 
	if (style != null) {
		style.apply(view) 
	} else {
		getDefaultStyle(view)?.apply(view)
	}
	//run block
	view.block()

	if (isGroup) {
		this.popStack()
	}
}



