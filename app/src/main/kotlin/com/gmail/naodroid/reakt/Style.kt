package com.gmail.naodroid.reakt

import android.view.View

/**
 * Created by nao on 15/05/21.
 */

public class Style<T : View>(val block : T.() -> Unit) {
	
	
	public fun apply(view : T) {
		view.block()
	}
}

public fun <T : View> T.apply(style : Style<in T>) {
	val block = style.block
	this.block()
}