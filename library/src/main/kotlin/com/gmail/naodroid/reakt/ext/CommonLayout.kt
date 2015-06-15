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
//margin class
public data class Margin4(val left: Int, val top: Int, val right: Int, val bottom : Int)
public data class Margin2(val topOrLeft: Int, val bottomOrRight: Int);

//Margin
private fun View.getMarginLayoutParams() : ViewGroup.MarginLayoutParams? {
	return getLayoutParams() as? ViewGroup.MarginLayoutParams
}
public var View.margin : Int
	get() = 0  //getter not supported
	set(value) { 
		this.margin4 = Margin4(value, value, value, value)
	}
public var View.margin4 : Margin4 
	get() = Margin4(this.marginLeft, this.marginTop, this.marginRight, this.marginBottom)
	set(value) {
		val margin = getMarginLayoutParams()
		if (margin == null) {
			return
		}
		margin.leftMargin = value.left
		margin.topMargin = value.top
		margin.rightMargin = value.right
		margin.bottomMargin = value.bottom
	}
public var View.marginVertical : Margin2
	get() = Margin2(this.marginTop, this.marginBottom)
	set(value) {
		this.marginTop = value.topOrLeft
		this.marginBottom = value.bottomOrRight
	}
public var View.marginHorizontal : Margin2
	get() = Margin2(this.marginLeft, this.marginRight)
	set(value) {
		this.marginLeft = value.topOrLeft
		this.marginRight = value.bottomOrRight
	}


public var View.marginLeft : Int
	get() = getMarginLayoutParams()?.leftMargin ?: 0
	set(value) { getMarginLayoutParams()?.leftMargin = value }
public var View.marginTop : Int
	get() = getMarginLayoutParams()?.topMargin ?: 0
	set(value) { getMarginLayoutParams()?.topMargin = value }
public var View.marginRight : Int
	get() = getMarginLayoutParams()?.rightMargin ?: 0
	set(value) { getMarginLayoutParams()?.rightMargin = value }
public var View.marginBottom : Int
	get() = getMarginLayoutParams()?.bottomMargin ?: 0
	set(value) { getMarginLayoutParams()?.bottomMargin = value }


