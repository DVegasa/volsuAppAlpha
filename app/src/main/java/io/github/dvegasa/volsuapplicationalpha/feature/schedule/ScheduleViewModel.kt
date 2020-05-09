package io.github.dvegasa.volsuapplicationalpha.feature.schedule

import android.os.Handler
import androidx.lifecycle.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.ScheduleRepo
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.ScheduleTimetable
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Time
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.Status
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex

const val TIMER_DELAY_RATE = 5 * 1000L

class ScheduleViewModel : ViewModel() {
    //////////// Объекты
    private val scheduleRepo =
        ScheduleRepo()
    private val handler = Handler()

    //////////// UI состояния
    val chosenTitle = MutableLiveData(0)
    val pickedDayweekTab = MutableLiveData(TimeCalculator.currentDayweek.value)
    val isZnamPicked = MutableLiveData(false)

    //////////// Объекты данных
    private val weekScheduleRequestStatus = MutableLiveData<RequestStatus>()
    private val weekSchedule = scheduleRepo.getScheduleWeek(weekScheduleRequestStatus).apply {
        observeForever {
            if (it != null) {
                startTimerUntilSubjectEnd()
            }
        }
    }

    val isThisWeekZnam = false

    private val timerRunnable = object : Runnable {
        override fun run() {
            if (weekSchedule.value != null) {
                val subjes = weekSchedule.value!!.schedule(TimeCalculator.currentDayweek)
                val t = if (isThisWeekZnam) subjes.znam else subjes.chis
                val range = t.firstNonOknoIndex()..t.lastNonOknoIndex()

                if (ScheduleTimetable.getSubjectIndexByTime(
                        Time.current
                    ) in range) {
                    val delta =
                        TimeCalculator.getTimerValue()
                    val v = if (delta >= 0) TimeCalculator.stringMin(
                        delta
                    ) else null
                    this@ScheduleViewModel.timeUntilSubjectEnd.postValue(v)
                } else {
                    this@ScheduleViewModel.timeUntilSubjectEnd.postValue(null)
                }
            }
            handler.postDelayed(this,
                TIMER_DELAY_RATE
            )
        }
    }

    val timeUntilSubjectEnd = MutableLiveData<String>()

    //////////// Состояния загрузки
    val isDataLoading = Transformations.map(weekScheduleRequestStatus) {
        it.status == Status.LOADING
    }

    //////////// Представление объектов для UI
    val scheduleByDayweek by lazy {
        mapOf(
            Dayweek.MONDAY to Transformations.map(weekSchedule) { it?.monday },
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

    fun requestUpdateScheduleWeek() {
        scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)
    }

    private fun startTimerUntilSubjectEnd() {
        handler.postDelayed(timerRunnable, 0)
    }

    private fun stopTimerUntilSubjectEnd() {
        handler.removeCallbacks(timerRunnable)
    }

    override fun onCleared() {
        stopTimerUntilSubjectEnd()
        super.onCleared()
    }
}
