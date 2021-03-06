package com.gmail.naodroid.reakt.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.widget.TextView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/20.
 */

//----------------------------------------------------------------
//creating
fun Reakt.textView(block : TextView.() -> Unit) : TextView {
	return textView(null, block)
}
fun Reakt.textView(style : ViewStyle<in TextView>?, block : TextView.() -> Unit) : TextView {
	val textView = TextView(this.context)
	commonProcess(textView, style, block)
	return textView
}

var Reakt.TextViewStyle : ViewStyle<TextView>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(TextView::class.java, value)


//----------------------------------------------------------------
//properties
//text(String)
var TextView.text : String
	get() = getText().toString()
	set(value) = setText(value)
var TextView.textBind : () -> String
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setText(value()) }

//Text(resource Id)
var TextView.textId : Int
	get() = throw UnsupportedOperationException()
	set(value) = setText(textId)
public var TextView.textIdBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setText(value()) }

//TextColor
var TextView.textColor : Int
	get() = throw UnsupportedOperationException()
	set(value) = setTextColor(convertRGB(value))
var TextView.textColorBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setTextColor(value()) }

//TextSize
var TextView.textSize : Float
	get() = getTextSize()
	set(value) = setTextSize(value)
var TextView.textSizeBind : () -> Float
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setTextSize(value()) }

var TextView.textSizeDip : Float
	get() = getTextSize()
	set(value) = setTextSize(TypedValue.COMPLEX_UNIT_DIP, value)
var TextView.textSizeDipBind : () -> Float
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setTextSize(TypedValue.COMPLEX_UNIT_DIP, value()) }
//Gravity
var TextView.gravity : Int
	get() = this.getGravity()
	set(value) = this.setGravity(value)
var TextView.gravityBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setGravity(value()) }


//----------------------------------------------------------------
//Event
var TextView.onTextChanged : (String) -> Unit
	get() = throw UnsupportedOperationException()
	set(value) = addTextChangedListener(TextWatcherImpl(value))

//
private class TextWatcherImpl(val onTextChanged : (String) -> Unit) : TextWatcher {
	override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
		onTextChanged(s.toString())
	}
	override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
	}
	override fun afterTextChanged(s: Editable?) {
	}
}













