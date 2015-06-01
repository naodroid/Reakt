package com.gmail.naodroid.reakt.ext

import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext

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

public var Reakt.TextViewStyle : ViewStyle<TextView>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(javaClass<TextView>(), value)


//----------------------------------------------------------------
//properties
//text(String)
public var TextView.text : String
	get() = getText().toString()
	set(value) = setText(value)
public var TextView.textBind : () -> String
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setText(value()) }

//Text(resource Id)
public var TextView.textId : Int
	get() = throw UnsupportedOperationException()
	set(value) = setText(textId)
public var TextView.textIdBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setText(value()) }

//TextColor
public var TextView.textColor : Int
	get() = throw UnsupportedOperationException()
	set(value) = setTextColor(convertRGB(value))
public var TextView.textColorBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setTextColor(value()) }

//TextSize
public var TextView.textSize : Float
	get() = getTextSize()
	set(value) = setTextSize(value)
public var TextView.textSizeBind : () -> Float
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setTextSize(value()) }

//Gravity
public var TextView.gravity : Int
	get() = this.getGravity()
	set(value) = this.setGravity(value)
public var TextView.gravityBind : () -> Int
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.addBinding { setGravity(value()) }


//----------------------------------------------------------------
//Event
public var TextView.onTextChanged : (String) -> Unit
	get() = throw UnsupportedOperationException()
	set(value) = addTextChangedListener(ext.TextWatcherImpl(value))

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













