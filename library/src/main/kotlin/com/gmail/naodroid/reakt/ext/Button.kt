package com.gmail.naodroid.reakt.ext

import android.widget.Button
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/22.
 */

//----------------------------------------------------------------
//creating
fun Reakt.button(block : Button.() -> Unit) : Button {
	return button(null, block)
}
fun Reakt.button(style : ViewStyle<in Button>?, block : Button.() -> Unit) : Button {
	val textView = Button(this.context)
	commonProcess(textView, style, block)
	return textView
}

var Reakt.ButtonStyle : ViewStyle<Button>
	get() = throw UnsupportedOperationException()
	set(value) = Reakt.registerDefaultStyle(Button::class.java, value)



