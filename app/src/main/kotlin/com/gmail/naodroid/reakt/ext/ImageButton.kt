package com.gmail.naodroid.reakt.ext

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/23.
 */

//----------------------------------------------------------------
//creating
fun Reakt.imageButton(block : ImageButton.() -> Unit) : ImageButton {
    return imageButton(null, block)
}
fun Reakt.imageButton(style : ViewStyle<in ImageButton>?, block : ImageButton.() -> Unit) : ImageButton {
    val textView = ImageButton(this.context)
    commonProcess(textView, style, block)
    return textView
}


//-----------------------------------------
//Default style
public var Reakt.ImageButtonStyle : ViewStyle<ImageButton>
    get() = throw UnsupportedOperationException()
    set(value) = Reakt.registerDefaultStyle(javaClass<ImageButton>(), value)


