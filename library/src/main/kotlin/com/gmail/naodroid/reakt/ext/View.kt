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

var Reakt.ViewStyle : ViewStyle<View>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(View::class.java, value)

//----------------------------------------------------------------
data class Padding4(val left: Int, val top: Int, val right: Int, val bottom: Int)
data class Padding2(val topOrLeft: Int, val bottomOrRight: Int)

//padding
var View.padding : Int
	get() = throw UnsupportedOperationException()
	set(value) = this.setPadding(value, value, value, value)
var View.padding4 : Padding4
	get() = Padding4(this.paddingLeft, this.paddingTop, this.paddingRight, this.paddingBottom)
	set(value) {
		this.setPadding(value.left, value.top, value.right, value.bottom)
	}
var View.paddingVertical : Padding2
	get() = Padding2(this.paddingTop, this.paddingBottom)
	set(value) {
		this.setPadding(this.paddingLeft, value.topOrLeft, this.paddingRight, value.bottomOrRight)
	}

var View.paddingHorizontal : Padding2
	get() = Padding2(this.paddingLeft, this.paddingRight)
	set(value) {
		this.setPadding(value.topOrLeft, this.paddingTop, value.bottomOrRight, this.paddingBottom)
	}

fun View.setPaddingLeft(padding : Int) {
	this.setPadding(padding, this.paddingTop, this.paddingRight, this.paddingBottom);
}
fun View.setPaddingTop(padding : Int) {
	this.setPadding(this.paddingLeft, padding, this.paddingRight, this.paddingBottom);
}
fun View.setPaddingRight(padding : Int) {
	this.setPadding(this.paddingLeft, this.paddingTop, padding, this.paddingBottom);
}
fun View.setPaddingBottom(padding : Int) {
	this.setPadding(this.paddingLeft, this.paddingTop, this.paddingRight, padding);
}




//----------------------------------------------------------------
//Properties

//background
var View.background : Drawable
	get() = this.getBackground()
	set(value) = this.setBackground(value)
var View.backgroundBind : () -> Drawable
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackground(value()) }

//backgroundColor
var View.backgroundColor : Int
	get() = throw UnsupportedOperationException()
	set(value) = setBackgroundColor(convertRGB(value))
var View.backgroundColorBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackgroundColor(convertRGB(value())) }

//background Resource
var View.backgroundResource : Int
	get() = throw UnsupportedOperationException()
	set(value) = this.setBackgroundResource(value)
var View.backgroundResourceBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setBackgroundResource(value()) }

//visibility
var View.visibility : Int
	get() = getVisibility()
	set(value) = setVisibility(value)
var View.visibilityBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setVisibility(value()) }

//alpha
var View.alpha : Float
	get() = getAlpha()
	set(value) = setAlpha(value)
var View.alphaBind : () -> Float
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setAlpha(value()) }




//----------------------------------------------------------------
//Event
var View.onClick : (View) -> Unit 
	get() = throw UnsupportedOperationException()
	set(value) = this.setOnClickListener(value)
	



//----------------------------------------------------------------
//layout
var View.layoutGravity : Int 
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

var View.weight : Float
	get() = throw UnsupportedOperationException()
	set(value) = setLayoutWeightInner(this, value)

private fun setLayoutWeightInner(view : View, weight : Float) {
	val linear = view.getLayoutParams() as? LinearLayout.LayoutParams
	if (linear != null) {
		linear.weight = weight
	}
}
