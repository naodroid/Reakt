package com.gmail.naodroid.reakt.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.widget.ProgressBar
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext.commonProcess

/**
 * Created by naodroid on 2015/06/01.
 * Simple progressbar.
 * Easy to use change color, you don't need to create drawable xml.
 * (Foreground and background color can be changed.
 */
public class SimpleProgressBar : ProgressBar {

    public var backgroundColor : Int = Color.GRAY
        get() = $backgroundColor
        set(value) {
            $backgroundColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }
    public var progressColor : Int = Color.BLUE
        get() = $progressColor
        set(value) {
            $progressColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }

    constructor(context : Context) : super(context) {
        updateLayerDrawable()
    }


    private fun updateLayerDrawable() {
        val bg = ColorDrawable(backgroundColor)
        val bar = ColorDrawable(progressColor)

        val layer = LayerDrawable(Array(bg, bar))
        layer.setId(0, android.R.id.background)
        layer.setId(1, android.R.id.progress)

        this.setProgressDrawable(layer)
    }
}

//creating
fun Reakt.simpleProgressBar(block : SimpleProgressBar.() -> Unit) : SimpleProgressBar {
    return simpleProgressBar(null, block)
}
fun Reakt.simpleProgressBar(style : ViewStyle<in SimpleProgressBar>?, block : SimpleProgressBar.() -> Unit) : SimpleProgressBar {
    val progressBar = SimpleProgressBar(this.context)
    commonProcess(progressBar, style, block)
    return progressBar
}

