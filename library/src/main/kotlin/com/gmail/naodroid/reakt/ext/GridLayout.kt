package com.gmail.naodroid.reakt.ext

import android.view.View
import android.widget.GridLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/22.
 */

//----------------------------------------------------------------
//Create
//vertical
fun Reakt.gridLayout(block : GridLayout.() -> Unit) : GridLayout {
	return gridLayout(null, block);
}
fun Reakt.gridLayout(style : ViewStyle<in GridLayout>?, block : GridLayout.() -> Unit) : GridLayout {
	val layout = GridLayout(this.context)
	commonProcess(layout, style, block)
	return layout
}

//----------------------------------------------------------------
//properties
var GridLayout.rowCount : Int
	get() = getRowCount()
	set(value) = setRowCount(value)

var GridLayout.columnCount : Int
	get() = getColumnCount()
	set(value) = setColumnCount(value)

//----------------------------------------------------
//View extension for grid layout

class GridSpec(val span : Int, val weight : Float)


var View.columnSpec : GridSpec
	get() = throw UnsupportedOperationException()
	set(value) = updateGridLayoutParams(this, null, value)
var View.rowSpec : GridSpec
	get() = throw UnsupportedOperationException()
	set(value) = updateGridLayoutParams(this, value, null)
var GridLayout.orientation : Int
	get() = getOrientation()
	set(value) = setOrientation(value)

///
private fun updateGridLayoutParams(view : View, row : GridSpec?, column : GridSpec?) {
	val current = view.getLayoutParams() as? GridLayout.LayoutParams
	if (current == null) {
		return
	}
	val rowNew = createLayoutSpecFromBaseLayoutSpec(row, current.rowSpec)
	val columnNew = createLayoutSpecFromBaseLayoutSpec(column, current.columnSpec)

	val newParams = GridLayout.LayoutParams(rowNew, columnNew)
	view.setLayoutParams(newParams)
}
private fun createLayoutSpecFromBaseLayoutSpec(newSpec : GridSpec?, oldSpec : GridLayout.Spec) : GridLayout.Spec {
	if (newSpec == null) {
		return oldSpec
	}
	return GridLayout.spec(GridLayout.UNDEFINED, newSpec.span, newSpec.weight)
}


private fun View.getGridLayoutParams() : GridLayout.LayoutParams? {
	return getLayoutParams() as? GridLayout.LayoutParams
}
