package com.gmail.naodroid.reakt.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ClipDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.view.Gravity
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

    public var progressBackgroundColor : Int = Color.GRAY
        get() = $progressBackgroundColor
        set(value) {
            $progressBackgroundColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }
    public var progressColor : Int = Color.BLUE
        get() = $progressColor
        set(value) {
            $progressColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }
    public var progressOrientation : Int = Gravity.LEFT
        get() = $progressOrientation
        set(value) {
            $progressOrientation = value
            this@SimpleProgressBar.updateLayerDrawable()
        }

    constructor(context : Context) : super(context, null, android.R.style.Widget_ProgressBar_Horizontal) {
        updateLayerDrawable()
    }


    private fun updateLayerDrawable() {
        val bg = ColorDrawable(progressBackgroundColor)
        val bar = ClipDrawable(ColorDrawable(progressColor), this.progressOrientation, ClipDrawable.HORIZONTAL);

        val layer = LayerDrawable(arrayOf(bg, bar))
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

