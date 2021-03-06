package com.gmail.naodroid.reakt.sample

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.FrameLayout
import com.gmail.naodroid.reakt.Reakt
import com.gmail.naodroid.reakt.ext.gravity
import com.gmail.naodroid.reakt.ext.textBind
import com.gmail.naodroid.reakt.ext.textView
import com.gmail.naodroid.reakt.ext.verticalLayout
import java.util.*

/**
 * Created by nao on 15/05/23.
 */
public class ClockView : FrameLayout {

    private val mReakt : Reakt

    constructor(context : Context) : super(context) {
        mReakt = Reakt(context) {
            verticalLayout {
                gravity = Gravity.CENTER

                textView(CommonStyle.mainText) {
                    textBind = { currentTimeText() }
                }
            }
        }
        this.addView(mReakt.toView())
    }

    private val mHandler = Handler(Looper.getMainLooper())

    //--------------------------------------------------------------
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startTimer()
    }

    override fun onDetachedFromWindow() {
        stopTimer()
        super.onDetachedFromWindow()
    }

    fun startTimer() {
        intervalProcess()
    }
    fun intervalProcess() {
        mReakt.update()
        mHandler.postDelayed({
            intervalProcess()
        }, 1000)
    }
    fun stopTimer() {
        mHandler.removeMessages(0)
    }


    fun currentTimeText() : String {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val min = c.get(Calendar.MINUTE)
        val sec = c.get(Calendar.SECOND)
        return two(hour) + ":" + two(min) + ":" + two(sec)
    }
    private fun two(v : Int) : String {
        return CommonStyle.convertNumber(v, 2)
    }
}
