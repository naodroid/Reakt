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
class SimpleProgressBar : ProgressBar {

    private var _progressBackgroundColor : Int = Color.GRAY
    var progressBackgroundColor : Int
        get() = this._progressBackgroundColor
        set(value) {
            this._progressBackgroundColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }
    
    private var _progressColor : Int = Color.BLUE
    var progressColor : Int
        get() = this._progressColor
        set(value) {
            this._progressColor = value
            this@SimpleProgressBar.updateLayerDrawable()
        }
    
    private var _progressOrientation : Int = Gravity.LEFT
    var progressOrientation : Int
        get() = this._progressOrientation
        set(value) {
            this._progressOrientation = value
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

