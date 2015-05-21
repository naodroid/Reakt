package com.gmail.naodroid.reakt.ext

import android.view.View
import android.view.ViewGroup
import com.gmail.naodroid.reakt.Reakt

/**
 * Created by nao on 15/05/21.
 */


//----------------------------------------------------------------
//Layout
data class LayoutSize(val width : Int, val height : Int)

public var View.layoutWidth : Int
	get() = getLayoutParams().width
	set(value) { getLayoutParams().width = value }
public var View.layoutHeight : Int
	get() = getLayoutParams().height
	set(value) { getLayoutParams().height = value }

public var View.layoutSize : LayoutSize
	get() = LayoutSize(this.layoutWidth, this.layoutHeight)
	set(value) {
		this.layoutWidth = value.width
		this.layoutHeight = value.height
	}

public val wrap_wrap : LayoutSize = LayoutSize(wrap, wrap)
public val wrap_fill : LayoutSize = LayoutSize(wrap, fill)
public val fill_wrap : LayoutSize = LayoutSize(fill, wrap)
public val fill_fill : LayoutSize = LayoutSize(fill, fill)


//----------------------------------------------------------------
//Margin
private fun View.getMarginLayoutParams() : ViewGroup.MarginLayoutParams? {
	return getLayoutParams() as? ViewGroup.MarginLayoutParams
}
public var View.margin : Int
	get() = 0  //getter not supported
	set(value) { 
		val margin = getMarginLayoutParams()
		if (margin == null) {
			return
		}
		margin.topMargin = value
		margin.bottomMargin = value
		margin.leftMargin = value
		margin.rightMargin = value
	}
public var View.marginTop : Int
	get() = getMarginLayoutParams()?.topMargin ?: 0
	set(value) { getMarginLayoutParams()?.topMargin = value }
public var View.marginBottom : Int
	get() = getMarginLayoutParams()?.bottomMargin ?: 0
	set(value) { getMarginLayoutParams()?.bottomMargin = value }
public var View.marginLeft : Int
	get() = getMarginLayoutParams()?.leftMargin ?: 0
	set(value) { getMarginLayoutParams()?.leftMargin = value }
public var View.marginRight : Int
	get() = getMarginLayoutParams()?.rightMargin ?: 0
	set(value) { getMarginLayoutParams()?.rightMargin = value }




