package com.gmail.naodroid.reakt.ext

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.Style

/**
 * Created by nao on 15/05/23.
 */

//----------------------------------------------------------------
//creating
fun Reakt.imageButton(block : ImageButton.() -> Unit) : ImageButton {
    return imageButton(null, block)
}
fun Reakt.imageButton(style : Style<in ImageButton>?, block : ImageButton.() -> Unit) : ImageButton {
    val textView = ImageButton(this.context)
    commonProcess(textView, style, block)
    return textView
}


//-----------------------------------------
//Default style
public var Reakt.ImageButtonStyle : Style<ImageButton>
    get() = throw UnsupportedOperationException()
    set(value) = Reakt.registerDefaultStyle(javaClass<ImageButton>(), value)


