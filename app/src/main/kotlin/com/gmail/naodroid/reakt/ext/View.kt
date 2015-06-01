package com.gmail.naodroid.reakt.ext

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/21.
 * View class properties
 */
//----------------------------------------------------------------
//creating(template code)

fun Reakt.view(block : View.() -> Unit) : View {
	return view(null, block)
}
fun Reakt.view(style : ViewStyle<in View>?, block : View.() -> Unit) : View {
	val view = View(this.context)
	commonProcess(view, style, block)
	return view
}

public var Reakt.ViewStyle : ViewStyle<View>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(javaClass<View>(), value)


//----------------------------------------------------------------
//padding
public var View.padding : Int
	get() = throw UnsupportedOperationException()
	set(value) = this.setPadding(value, value, value, value)

//----------------------------------------------------------------
//Properties

//background
public var View.background : Drawable
	get() = this.getBackground()
	set(value) = this.setBackground(value)
public var View.backgroundBind : () -> Drawable
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackground(value()) }

//backgroundColor
public var View.backgroundColor : Int
	get() = throw UnsupportedOperationException()
	set(value) = setBackgroundColor(convertRGB(value))
public var View.backgroundColorBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackgroundColor(convertRGB(value())) }

//background Resource
public var View.backgroundResource : Int
	get() = throw UnsupportedOperationException()
	set(value) = this.setBackgroundResource(value)
public var View.backgroundResourceBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackgroundResource(value()) }

//visibility
public var View.visibility : Int
	get() = getVisibility()
	set(value) = setVisibility(value)
public var View.visibilityBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setVisibility(value()) }

//alpha
public var View.alpha : Float
	get() = getAlpha()
	set(value) = setAlpha(value)
public var View.alphaBind : () -> Float
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setAlpha(value()) }




//----------------------------------------------------------------
//Event
public var View.onClick : (View) -> Unit 
	get() = throw UnsupportedOperationException()
	set(value) = this.setOnClickListener(value)
	



//----------------------------------------------------------------
//layout
public var View.layoutGravity : Int 
	get() = throw UnsupportedOperationException()
	set(value) = setLayoutGravityInner(this, value)

private fun setLayoutGravityInner(view : View, gravity : Int) {
	val params = view.getLayoutParams()
	val frame = params as? FrameLayout.LayoutParams
	if (frame != null) {
		frame.gravity = gravity
	}
	val linear = params as? LinearLayout.LayoutParams
	if (linear != null) {
		linear.gravity = gravity
	}
}

public var View.weight : Float
	get() = throw UnsupportedOperationException()
	set(value) = setLayoutWeightInner(this, value)

private fun setLayoutWeightInner(view : View, weight : Float) {
	val linear = view.getLayoutParams() as? LinearLayout.LayoutParams
	if (linear != null) {
		linear.weight = weight
	}
}
