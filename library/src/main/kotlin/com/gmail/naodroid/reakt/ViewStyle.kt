package com.gmail.naodroid.reakt

import android.view.View

/**
 * Created by nao on 15/05/21.
 */

class ViewStyle<T : View>(val block : T.() -> Unit) {
	
	
	public fun apply(view : T) {
		view.block()
	}
}

fun <T : View> T.apply(style : ViewStyle<in T>) {
	val block = style.block
	this.block()
}