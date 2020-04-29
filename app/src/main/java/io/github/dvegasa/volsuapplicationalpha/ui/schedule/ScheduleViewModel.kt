package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.icu.text.TimeZoneFormat
import android.os.Handler
import android.util.Log
import androidx.lifecycle.*
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.Time
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleTimetable
import io.github.dvegasa.volsuapplicationalpha.utils.default
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex

const val TIMER_DELAY_RATE = 20 * 1000L

class ScheduleViewModel : ViewModel() {
    //////////// Объекты
    private val scheduleRepo = ScheduleRepo()
    private val handler = Handler()

    //////////// LiveData
    val chosenTitle = MutableLiveData<Int>().default(0)

    val weekSchedule = scheduleRepo.getFakeScheduleWeek()

    val pickedDayweekTab = MutableLiveData<Int>().default(
        TimeCalculator.currentDayweek.value
    )

    private val timerRunnable = object : Runnable {
        override fun run() {
            val subjes = weekSchedule.value!!.schedule(TimeCalculator.currentDayweek)
            val t = if (isThisWeekZnam) subjes.znam else subjes.chis
            val range = t.firstNonOknoIndex()..t.lastNonOknoIndex()

            if (ScheduleTimetable.getSubjectIndexByTime(Time.current) in range) {
                val delta = TimeCalculator.getTimerValue()
                val v = if (delta >= 0) TimeCalculator.stringMin(delta) else ""
                this@ScheduleViewModel.timeUntilSubjectEnd.postValue(v)
            } else {
                this@ScheduleViewModel.timeUntilSubjectEnd.postValue("")
            }
            handler.postDelayed(this, TIMER_DELAY_RATE)
        }
    }

    val timeUntilSubjectEnd = MutableLiveData<String>().default("")

    val isThisWeekZnam = false

    fun startTimerUntilSubjectEnd() {
        handler.postDelayed(timerRunnable, 0)
    }

    fun stopTimerUntilSubjectEnd() {
        handler.removeCallbacks(timerRunnable)
    }
}
