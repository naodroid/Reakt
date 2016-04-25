package com.gmail.naodroid.reakt.sample

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext.*

/**
 * Created by nao on 15/05/23.
 */

public class ClockActivity : Activity() {

    enum class Tab (val iconId : Int) {
        Clock(R.drawable.tab_icon_clock),
        StopWatch(R.drawable.tab_icon_stopwatch),
        Timer(R.drawable.tab_icon_timer)
    }

    private var _mCurrentTab = Tab.Clock
    private var mCurrentTab : Tab
        get() = this._mCurrentTab
        set(value)  {
            this._mCurrentTab = value
            onTabChanged(value)
        }
    
    private var mReakt : Reakt? = null

    //--------------------------------------------------------------
    //Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val innerViews = arrayOf(ClockView(this), StopWatchView(this), TimerView(this))

        mReakt = Reakt(this) {
            verticalLayout(CommonStyle.background) {
                //main view pager
                viewPager {
                    layoutWidth = fill
                    layoutHeight = 0
                    weight = 1f
                    views = innerViews
                    currentPageBind = {getCurrentTabIndex()}
                    onPageChanged = { page -> this@ClockActivity.onPageChanged(page)}
                }
                //bottom tab buttons
                horizontalLayout {
                    layoutSize = fill_wrap

                    for (tab in Tab.values()) {
                        imageButton(tabButtonStyle) {
                            imageId = tab.iconId
                            alphaBind = { if (tab == mCurrentTab) 1f else 0.5f}
                            onClick = { mCurrentTab = tab }
                        }
                    }
                }
            }
        }
        val view = mReakt?.toView()
        setContentView(view)
    }

    //--------------------------------------------------------------
    //event
    private fun onTabChanged(tab : Tab) {
        mReakt?.update()
    }
    private fun onPageChanged(page : Int) {
        val tabs = Tab.values()
        mCurrentTab = tabs[page]
    }
    private fun getCurrentTabIndex() : Int {
        val tabs = Tab.values()
        val max = tabs.count() - 1
        for (i in 0..max) {
            if (tabs[i] == mCurrentTab) {
                return i
            }
        }
        return -1
    }



    //--------------------------------------------------------------
    //Styles
    val tabButtonStyle = ViewStyle<ImageView> {
        layoutWidth = dip(0)
        layoutHeight = dip(50)
        backgroundColor = Color.TRANSPARENT
        weight = 1f
        scaleType = ImageView.ScaleType.FIT_CENTER
    }
}