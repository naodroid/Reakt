package com.gmail.naodroid.reakt.ext

import android.widget.ProgressBar
import android.widget.TextView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao22_000 on 2015/06/01.
 */

//----------------------------------------------------------------
//creating
fun Reakt.progressBar(block : ProgressBar.() -> Unit) : ProgressBar {
    return progressBar(null, block)
}
fun Reakt.progressBar(style : ViewStyle<in ProgressBar>?, block : ProgressBar.() -> Unit) : ProgressBar {
    val progressBar = ProgressBar(this.context)
    commonProcess(progressBar, style, block)
    return progressBar
}

var Reakt.ProgressBarStyle : ViewStyle<ProgressBar>
    get() = throw UnsupportedOperationException()
    set(value) = Reakt.registerDefaultStyle(ProgressBar::class.java, value)


//-----------------------------------------------------------------
//progress
var ProgressBar.progress : Int
    get() = this.getProgress()
    set(value) = this.setProgress(value)
var ProgressBar.progressBind : () -> Int
    get() = throw UnsupportedOperationException()
    set(value) {
        Reakt.addBinding { setProgress(value()) }
    }
//secondary
var ProgressBar.secondaryProgress : Int
    get() = this.getSecondaryProgress()
    set(value) = this.setSecondaryProgress(value)
var ProgressBar.secondaryProgressBind : () -> Int
    get() = throw UnsupportedOperationException()
    set(value) {
        Reakt.addBinding { setSecondaryProgress(value()) }
    }


//max
var ProgressBar.max : Int
    get() = this.getMax()
    set(value) = this.setMax(max)

