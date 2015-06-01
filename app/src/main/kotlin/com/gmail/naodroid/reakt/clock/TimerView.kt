package com.gmail.naodroid.reakt.clock

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ViewStyle
import com.gmail.naodroid.reakt.ext.*

/**
 * Created by nao on 15/05/23.
 */

public class TimerView : FrameLayout {

    private val mReakt : Reakt

    private var mTimerRunning = false
    private var mLastSecond = 0
    private val mHandler = Handler()

    //--------------------------------------------------------------
    constructor(context : Context) : super(context) {
        mReakt = Reakt(context) {
            verticalLayout {
                gravity = Gravity.CENTER

                //remain time
                textView(CommonStyle.mainText) {
                    textBind = {remainTimeText()}
                }
                //inputting
                gridLayout {
                    layoutSize = wrap_wrap
                    columnCount = 3
                    rowCount = 4

                    for (i in 1..12) {
                        //10 and 12 , use to layout spacing. always hidden
                        val num = if (i == 11) 0 else i
                        val visible = if (i == 10 || i == 12) View.INVISIBLE else View.VISIBLE

                        button(numberButtonStyle) {
                            text = "" + num
                            visibility = visible
                            onClick = {onNumberClick(num)}
                            alphaBind = { if (mTimerRunning) 0.5f else 1.0f }
                        }
                    }
                }
                button(numberButtonStyle) {
                    layoutWidth = dip(200)
                    textBind = {if (mTimerRunning) "CANCEL" else "START"}
                    alphaBind = { if (mLastSecond == 0) 0.5f else 1.0f }
                    onClick = {
                        if (mTimerRunning) onCancelClick()
                        else onStartClick()
                    }
                }
            }
        }
        this.addView(mReakt.toView())
    }

    //change XX:YY pattern integer to XX * 60 + YY
    fun toSecond(displayTime: Int) : Int {
        val min = displayTime / 100
        val sec = displayTime % 100
        return min * 60 + sec
    }
    fun toDisplayTime(second : Int) : Int {
        val min = second / 60
        val sec = second % 60
        return min * 100 + sec
    }


    fun onNumberClick(num : Int) {
        if (mTimerRunning) {
            return
        }
        val displayTime = toDisplayTime(mLastSecond)
        val next = (displayTime * 10 + num) % 10000
        mLastSecond = toSecond(next)
        mReakt.update()
    }

    fun onStartClick() {
        if (mTimerRunning || mLastSecond == 0) {
            return
        }
        startTimer()
    }
    fun onCancelClick() {
        if (!mTimerRunning) {
            return
        }
        stopTimer()
    }
    //
    fun startTimer() {
        mTimerRunning = true
        mReakt.update()
        intervalProcess()
    }
    fun stopTimer() {
        mTimerRunning = false
        mReakt.update()
    }

    fun intervalProcess() {
        if (!mTimerRunning) {
            return
        }
        mLastSecond--;
        mReakt.update()

        if (mLastSecond > 0) {
            mHandler.postDelayed({
                intervalProcess()
            }, 1000)
        } else {
            stopTimer()
        }
    }

    fun remainTimeText() : String {
        val min = mLastSecond / 60
        val sec = mLastSecond % 60
        return CommonStyle.convertNumber(min, 2) + ":" + CommonStyle.convertNumber(sec, 2)
    }

    //--------------------------------------------------------------
    //style
    val numberButtonStyle = ViewStyle<Button> {
        layoutWidth = dip(80)
        layoutHeight = dip(50)
        textSize = 24f
    }

}