package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleSubject
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Time
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.TimeStatus

/**
 * Created by Ed Khalturin @DVegasa
 */

const val BOTTOM_TIMER_UPDATE_RATE = 5 * 1000L

class BottomTimer {

    private val handler = Handler()
    private var subjes: ArrayList<ScheduleSubject>? = null

    private val timerText = MutableLiveData<String>(null)


    fun init(subjes: ArrayList<ScheduleSubject>?): LiveData<String?> {
        if (subjes != null) {
            this.subjes = subjes
            stopTimer()
            startTimer()
        }
        return timerText
    }

    fun stopTimer() {
        handler.removeCallbacks(bottomTimerRunnable)
    }

    private fun startTimer() {
        handler.post(bottomTimerRunnable)
    }

    private val bottomTimerRunnable = object : Runnable {
        override fun run() {
            subjes?.let {
                val curSlot = ScheduleTimetable.getSubjectIndexByTime(Time.current)

                val isAnySubjOngoing = (it.filter { subj ->
                    subj.slot == curSlot && !subj.isOkno()
                }).isNotEmpty()

                if (isAnySubjOngoing) {
                    val timeDelta = Time.current.delta(ScheduleTimetable.subjEnd[curSlot])
                    if (timeDelta != 0) {
                        timerText.postValue(stringMin(timeDelta))
                    } else {
                        timerText.postValue(null)
                    }
                }
            }
            if (subjes == null) timerText.postValue(null)
            handler.postDelayed(this, BOTTOM_TIMER_UPDATE_RATE)
        }
    }

    private fun stringMin(n: Int): String {
        val textForms = arrayOf("минута", "минуты", "минут")
        val n1 = n % 10
        val f: String
        f = when {
            n in 11..19 -> textForms[2]
            n1 in 2..4 -> textForms[1]
            n1 == 1 -> textForms[0]
            else -> textForms[2]
        }
        return "$n $f"
    }
}