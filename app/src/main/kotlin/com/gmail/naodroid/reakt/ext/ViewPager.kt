package com.gmail.naodroid.reakt.ext

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle

/**
 * Created by nao on 15/05/23.
 */
fun Reakt.viewPager(block : ViewPager.() -> Unit) : ViewPager {
    return viewPager(null, block)
}
fun Reakt.viewPager(style : ViewStyle<in ViewPager>?, block : ViewPager.() -> Unit) : ViewPager {
    val layout = ViewPager(this.context)
    commonProcess(layout, style, block)
    return layout
}

//----------------------------------------------------------------
//properties
//setup from view list
public var ViewPager.views : Array<out View>
    get() = throw UnsupportedOperationException()
    set(value) {
        val adapter = SimpleViewPagerAdapter()
        adapter.viewList = value
        this.setAdapter(adapter)
    }

public var ViewPager.currentPage : Int
    get() = getCurrentItem()
    set(value) {
        if (getCurrentItem() != value) {
            setCurrentItem(value)
        }
    }
public var ViewPager.currentPageBind : () -> Int
    get() = throw UnsupportedOperationException()
    set(value) = Reakt.addBinding({this.currentPage = value()})
///
public var ViewPager.onPageChanged : (Int) -> Unit
    get() = throw UnsupportedOperationException()
    set(value) {
        setOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                value(position)
            }
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
        })
    }



//--------------------------------------------------------------
//Adapter
private class SimpleViewPagerAdapter : PagerAdapter() {
    var viewList : Array<out View>? = null

    override fun getCount(): Int {
        return viewList?.count() ?: 0
    }
    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view === obj;
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val view = viewList?.get(position)
        if (view != null) {
            container?.addView(view)
        }
        return view
    }
    override fun destroyItem(container: ViewGroup?, position: Int, obj : Any?) {
        val view = viewList?.get(position)
        if (view != null) {
            container?.removeView(view)
        }
    }
}

