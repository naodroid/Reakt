package com.gmail.naodroid.reakt.ext

import android.graphics.Color

/**
 * Created by nao on 15/06/04.
 * Extension for color
 */

/**
 * Change hex RGB string to color int.
 * sample  "RRGGBB" (or #RRGGBB, #AARRGGBB) to 0xRRGGBB
 * @see Color.parseColor()
 * @return ARGB hex integer
 */
fun String.toColor() : Int {
    var text = this
    if (!text.startsWith("#")) {
        text = "#" + text
    }
    return Color.parseColor(text);
}


/**
 * convert HEX RGB to ARGB.
 * ex: 0xFFFFF -> 0xFFFFFFFF
*/
fun convertRGB(color : Int) : Int {
    if (color == 0) {
        //transparent
        return color
    } else if ((color ushr 24) == 0) {
        //add 0xFF00000
        return (0xFF shl 24) or color
    }
    return color
}