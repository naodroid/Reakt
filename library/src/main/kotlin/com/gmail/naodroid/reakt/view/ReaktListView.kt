package com.gmail.naodroid.reakt.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ReusableReakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext.commonProcess

/**
 * Created by nao on 15/05/22.
 */

public class ReaktListView<T> : ListView {

    //--------------------------------------------------------------
    //constructor
    constructor(context: Context) : super(context) {
        setAdapter(adapter)
    }
    constructor(context : Context, attr : AttributeSet) : super(context, attr) {
        setAdapter(adapter)
    }
    constructor(context: Context, attr : AttributeSet, defStyle : Int) : super(context, attr, defStyle ){
        setAdapter(adapter)
    }


    private var innerItems : List<T>? = null
    /**
     * items for using listview
     */
    public var listItems : Iterable<T>?
        get() = innerItems
        set(value) = updateItems(value)

    public var listItemsBind : () -> Iterable<T>?
        get() = throw UnsupportedOperationException()
        set(value) = Reakt.addBinding { listItems = value() }

    //create cell view from item
    public var cellCreator : (() -> ReusableReakt<T>)? = null
    //adapter
    private val adapter = ItemAdapter()



    //--------------------------------------------------------------
    //item management
    private fun updateItems(items : Iterable<T>?) {
        if (!hasUpdated(innerItems, items)) {
            return
        }
        innerItems = items?.toList()
        adapter.notifyDataSetChanged()
    }
    private fun hasUpdated(oldItems : Iterable<T>?, newItems : Iterable<T>?) : Boolean {
        //FIXME: if item member is updated, this method returns false and list will not updated
        if (oldItems?.count() != newItems?.count()) {
            return true
        }
        val count = oldItems?.count() ?: 0
        for (i in 0..(count - 1)) {
            if (oldItems?.elementAt(i) != newItems?.elementAt(i)) {
                return true
            }
        }
        return false
    }

    inner class ItemAdapter() : BaseAdapter() {
        override fun getCount(): Int {
            return this@ReaktListView.listItems?.count() ?: 0
        }
        override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
            val item = this@ReaktListView.listItems!!.elementAt(position)
            val block = this@ReaktListView.cellCreator
            if (block != null) {
                val related = view?.getTag() as? ReusableReakt<T>
                
                if (related != null) {
                    related.updateWithItem(item)
                    return view
                }
                val reakt = block()
                reakt.updateWithItem(item)
                val ret = reakt.toView()
                ret.setTag(reakt)
                return ret
            }
            return null
        }
        override fun getItem(position: Int): Any? {
            return this@ReaktListView.listItems?.elementAt(position)
        }
        override fun getItemId(position: Int): Long {
            return 0
        }
    }
}

//--------------------------------------------------------------
//Reakt extension
//----------------------------------------------------------------
//creating
fun Reakt.listView<T>(block : ReaktListView<T>.() -> Unit) : ReaktListView<T> {
    return listView(null, block)
}
fun Reakt.listView<T>(style : ViewStyle<in ReaktListView<T>>?, block : ReaktListView<T>.() -> Unit) : ReaktListView<T> {
    val listView = ReaktListView<T>(this.context)
    commonProcess(listView, style, block)
    return listView
}

public var Reakt.ReaktListViewStyle : ViewStyle<ReaktListView<*>>
    get() = throw UnsupportedOperationException()
    set(value) = Reakt.registerDefaultStyle(javaClass<ReaktListView<*>>(), value)
