package com.gmail.naodroid.reakt.ext

import android.widget.FrameLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/22.
 */

fun Reakt.frameLayout(block : FrameLayout.() -> Unit) : FrameLayout {
	return frameLayout(null, block)
}
fun Reakt.frameLayout(style : ViewStyle<in FrameLayout>?, block : FrameLayout.() -> Unit) : FrameLayout {
	val layout = FrameLayout(this.context)
	commonProcess(layout, style, block)
	return layout
}

//----------------------------------------------------------------
//properties
var FrameLayout.gravity : Int
	get() = getForegroundGravity()
	set(value) = setForegroundGravity(value)
