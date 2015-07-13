package com.gmail.naodroid.reakt.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.apply

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


/**
 * inflate from layout resource
 * @param layoutResId layout resource id
 */
public fun Reakt.inflate(layoutResId : Int) : View {
	val inflater = LayoutInflater.from(this.context)
	val view = inflater.inflate(layoutResId, null);
	commonProcess(view, null, {})
	return view
}

/**
 * create custom view
 * Ex:
 * customView(MyView(context)) {
 *    layoutWidth=fill
 *    layoutHeight=dp(180)
 * }
 * @param view view for add to this reakt
 * @param block view customization
 * @return view, same as view parameter
 */
public fun <T: View> Reakt.customView(view : T, style: ViewStyle<in T>?, block: T.() -> Unit) : T {
    commonProcess(view, style, block)
	return view
}

/**
 * do any operation after view creation.
 * 
 * ex:
 * inflate(R.layout.main).let {
 *    //write any code for inflated view
 * }
 */
public fun <T : View> T.let(block: T.() -> Unit) : Unit {
    this.block()
}



