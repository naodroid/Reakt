package com.gmail.naodroid.reakt.clock

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.FrameLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ReusableReakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext.*
import com.gmail.naodroid.reakt.listview.listView
import java.util.ArrayList

/**
 * Created by nao on 15/05/23.
 */
public class StopWatchView : FrameLayout {

    private val mReakt : Reakt

    private var mTime = 0
    private var mStartTime : Long = 0
    private var mRunning = false
    private val mHandler = Handler(Looper.getMainLooper())
    private val mTimeList = ArrayList<Int>()

    constructor(context : Context) : super(context) {
        mReakt = Reakt(context) {
            verticalLayout {
                //main time
                textView(CommonStyle.mainText) {
                    textBind = {currentText()}
                    margin = dip(20)
                }

                //start & stop
                horizontalLayout {
                    layoutSize = fill_wrap

                    button(buttonStyle) {
                        textBind = { if (mRunning) "STOP" else "START" }
                        onClick = {
                            if (mRunning) onStopClick()
                            else onStartClick()
                        }
                    }
                    button(buttonStyle) {
                        textBind = { if (mRunning) "LAP" else "RESET"}
                        onClick = {
                            if (mRunning) onLapClick()
                            else onResetClick()
                        }
                    }
                }
                //lap
                separatorView()
                //lap
                listView<Int> {
                    layoutSize = LayoutSize(fill, 0)
                    weight = 1f
                    listItemsBind = { mTimeList }
                    cellCreator = { this@StopWatchView.createListReakt() }
                }
            }
        }
        this.addView(mReakt.toView())
    }
    private fun Reakt.separatorView() {
        textView(CommonStyle.text) {
            text = "LAP"
            marginTop = dip(8)
            marginLeft = dip(8)
        }
        view {
            layoutWidth = fill
            layoutHeight = dip(1)
            backgroundColor = Color.WHITE
            marginLeft = dip(8)
            marginRight = dip(8)
        }
    }

    //--------------------------------------------------------------
    //event
    fun onStartClick() {
        mRunning = true
        mStartTime = System.currentTimeMillis()
        startTimer()
        mReakt.update()
    }
    fun onLapClick() {
        val time = currentTime()
        mTimeList.add(time)
        mReakt.update()
    }
    fun onStopClick() {
        mRunning = false
        mTime += (System.currentTimeMillis() - mStartTime).toInt()
        stopTimer()
        mReakt.update()
    }
    fun onResetClick() {
        mTime = 0
        mTimeList.clear()
        mReakt.update()
    }
    fun createListReakt() : ReusableReakt<Int> {
        return ReusableReakt(getContext()) {
            horizontalLayout {
                margin = dip(10)

                textView {
                    textSize = 30f
                    textColor = Color.WHITE
                    marginLeft = dip(30)
                    textBind =  { createTimeText(reaktItem!!) }
                }
            }
        }
    }


    fun startTimer() {
        intervalProcess()
    }
    fun intervalProcess() {
        mReakt.update()
        mHandler.postDelayed({
            intervalProcess()
        }, 10)
    }
    fun stopTimer() {
        mHandler.removeMessages(0)
        mReakt.update()
    }

    fun currentTime() : Int {
        return if (mRunning) {
            mTime + (System.currentTimeMillis() - mStartTime).toInt()
        } else {
            mTime
        }
    }

    fun currentText() : String {
        return createTimeText(currentTime())
    }
    fun createTimeText(time : Int) : String {
        val min = time / 60000
        val sec = (time / 1000) % 60
        val msec = time % 1000 / 10

        val minText = CommonStyle.convertNumber(min, 2)
        val secText = CommonStyle.convertNumber(sec, 2)
        val mSecText = CommonStyle.convertNumber(msec, 3)

        return minText + ":" + secText + ":" + mSecText
    }
    //--------------------------------------------------------------
    //styles
    val buttonStyle = ViewStyle<Button> {
        layoutWidth = 0
        layoutHeight = dip(50)
        weight = 1f
        textSize = 24f
    }

}