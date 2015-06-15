package com.gmail.naodroid.reakt.ext

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/21.
 */


//----------------------------------------------------------------
//Create
//vertical
fun Reakt.verticalLayout(block : LinearLayout.() -> Unit) : LinearLayout {
	return verticalLayout(null, block);
}
fun Reakt.verticalLayout(style : ViewStyle<in LinearLayout>?, block : LinearLayout.() -> Unit) : LinearLayout {
	val layout = LinearLayout(this.context)
	layout.setOrientation(LinearLayout.VERTICAL)
	commonProcess(layout, style, block)
	return layout
}
//horizontal
fun Reakt.horizontalLayout(block : LinearLayout.() -> Unit) : LinearLayout {
	return horizontalLayout(null, block)
}

fun Reakt.horizontalLayout(style : ViewStyle<in LinearLayout>?, block : LinearLayout.() -> Unit) : LinearLayout {
	val layout = LinearLayout(this.context)
	layout.setOrientation(LinearLayout.HORIZONTAL)
	commonProcess(layout, style, block)
	return layout
}

//----------------------------------------------------------------
//properties
public var LinearLayout.gravity : Int
	get() = throw UnsupportedOperationException()
	set(value) = setGravity(value)
