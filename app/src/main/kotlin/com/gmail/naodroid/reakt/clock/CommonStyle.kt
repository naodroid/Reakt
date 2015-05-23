package com.gmail.naodroid.reakt.clock

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.gmail.naodroid.reakt.Style
import com.gmail.naodroid.reakt.apply
import com.gmail.naodroid.reakt.ext.*

/**
 * Created by nao on 15/05/23.
 */

object CommonStyle {
    val background = Style<View> {
        backgroundColor = 0x0C00CC
    }
    val text = Style<TextView> {
        textColor = Color.WHITE
    }
    val mainText = Style<TextView> {
        apply(CommonStyle.text)
        textSize = 68f
        gravity = Gravity.CENTER
    }

    fun convertNumber(value : Int, digits : Int) : String {
        var text = "" + value
        while (text.length() < digits) {
            text = "0" + text
        }
        return text
    }
}