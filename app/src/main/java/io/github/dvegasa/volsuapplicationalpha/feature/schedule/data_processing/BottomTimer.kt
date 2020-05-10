package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleSubject

/**
 * Created by Ed Khalturin @DVegasa
 */
class BottomTimer {

    private val _timerText = MutableLiveData("N минут")
    val timerText: LiveData<String>
        get() = _timerText


    fun startTimer() {
        _timerText.postValue("NN минут")
    }

    fun stopTimer() {

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

//    private val timerRunnable = object : Runnable {
//        override fun run() {
//            if (weekSchedule.value != null) {
//                val subjes =
//                    try {
//                        weekSchedule.value!!.schedule(TimeCalculator.currentDayweek)
//                    } catch (e: Exception) {
//                        weekSchedule.value!!.schedule(Dayweek.MONDAY)
//                    }
//                val t = if (isThisWeekZnam) subjes.znam else subjes.chis
//                val range = t?.firstNonOknoIndex()..t.lastNonOknoIndex()
//
//                if (ScheduleTimetable.getSubjectIndexByTime(
//                        Time.current
//                    ) in range) {
//                    val delta =
//                        TimeCalculator.getTimerValue()
//                    val v = if (delta >= 0) TimeCalculator.stringMin(
//                        delta
//                    ) else null
//                    this@ScheduleViewModel.timeUntilSubjectEnd.postValue(v)
//                } else {
//                    this@ScheduleViewModel.timeUntilSubjectEnd.postValue(null)
//                }
//            }
//            handler.postDelayed(this,
//                TIMER_DELAY_RATE
//            )
//        }
//    }
}