package com.gmail.naodroid.reakt

import android.content.Context
import android.view.View

/**
 * Created by nao on 15/05/29.
 * Reakt for ReaktListView. To reuse cell view.
 *
 * To use ReusableReakt, call 'reaktItem' to use current item.
 *
 * Example:
 * return ReusableReakt<String> {
 *    textView {
 *       textBind = {reaktItem ?: "Nonne"}
 *    }
 * }
 *
 *
 * 'reaktItem' is related to ReaktListView's list item.
 * When creating cell that placed at N, the reaktItem will equal to listItem[N]
 * 
 * Notice: Don't use 
 * 
 * @see ReaktListView
 */
public class ReusableReakt<T> : Reakt {
    private val block : ReusableReakt<T>.() -> View

    public var reaktItem : T? = null
        private set

    constructor(context: Context, block : ReusableReakt<T>.() -> View) : super(context, block as Reakt.() -> View) {
        this.block = block
    }
    public fun updateWithItem(item : T) {
        this.reaktItem = item
        update()
    }
}
