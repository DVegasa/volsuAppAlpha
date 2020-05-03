package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.icu.text.TimeZoneFormat
import android.os.Handler
import android.text.method.TransformationMethod
import android.util.Log
import androidx.lifecycle.*
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.Time
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.Status
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
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

    //////////// UI состояния
    val chosenTitle = MutableLiveData<Int>(0)
    val pickedDayweekTab = MutableLiveData<Int>(TimeCalculator.currentDayweek.value)
    val isZnamPicked = MutableLiveData<Boolean>(false)

    //////////// Объекты данных
    private val weekScheduleRequestStatus = MutableLiveData<RequestStatus>()
    private val weekSchedule = scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)

    val isThisWeekZnam = false

    //////////// Состояния загрузки
    val isDataLoading = Transformations.map(weekScheduleRequestStatus) {
        it.status == Status.LOADING
    }

    //////////// Представление объектов для UI
    val scheduleByDayweek by lazy {
        mapOf(
            Dayweek.MONDAY to Transformations.map(weekSchedule) { it?.monday},
            Dayweek.TUESDAY to Transformations.map(weekSchedule) { it?.tuesday },
            Dayweek.WEDNESDAY to Transformations.map(weekSchedule) { it?.wednesday },
            Dayweek.THURSDAY to Transformations.map(weekSchedule) { it?.thursday },
            Dayweek.FRIDAY to Transformations.map(weekSchedule) { it?.friday },
            Dayweek.SATURDAY to Transformations.map(weekSchedule) { it?.saturday }
        )
    }

    val errorMessage: LiveData<String> = Transformations.map(weekScheduleRequestStatus) {
        if (it.status == Status.ERROR) it.msg
        else null
    }

    val bottomTimerText = MutableLiveData<String>(null)

    fun requestUpdateScheduleWeek() {
        scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)
    }

//    private val timerRunnable = object : Runnable {
//        override fun run() {
//            val subjes = weekSchedule.value!!.schedule(TimeCalculator.currentDayweek)
//            val t = if (isThisWeekZnam) subjes.znam else subjes.chis
//            val range = t.firstNonOknoIndex()..t.lastNonOknoIndex()
//
//            if (ScheduleTimetable.getSubjectIndexByTime(Time.current) in range) {
//                val delta = TimeCalculator.getTimerValue()
//                val v = if (delta >= 0) TimeCalculator.stringMin(delta) else ""
//                this@ScheduleViewModel.timeUntilSubjectEnd.postValue(v)
//            } else {
//                this@ScheduleViewModel.timeUntilSubjectEnd.postValue("")
//            }
//            handler.postDelayed(this, TIMER_DELAY_RATE)
//        }
//    }
//
//    val timeUntilSubjectEnd = MutableLiveData<String>().default("")
//
//    fun startTimerUntilSubjectEnd() {
//        handler.postDelayed(timerRunnable, 0)
//    }
//
//    fun stopTimerUntilSubjectEnd() {
//        handler.removeCallbacks(timerRunnable)
//    }
}
