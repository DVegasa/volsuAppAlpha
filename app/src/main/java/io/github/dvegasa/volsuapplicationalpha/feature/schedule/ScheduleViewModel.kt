package io.github.dvegasa.volsuapplicationalpha.feature.schedule

import android.os.Handler
import android.util.Log
import androidx.lifecycle.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Time
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.Status
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex

class ScheduleViewModel : ViewModel() {
    //////////// Объекты
    private val scheduleRepo = ScheduleRepo()

    private val bottomTimer = BottomTimer()

    //////////// UI состояния
    val chosenTitle = MutableLiveData(0)

    val pickedDayweekTab = MutableLiveData(Dayweek.current.value)

    val isZnamPicked = MutableLiveData(scheduleRepo.isThisWeekZnam())

    //////////// Объекты данных
    private val weekScheduleRequestStatus = MutableLiveData<RequestStatus>()
    private val weekSchedule = scheduleRepo.getScheduleWeek(weekScheduleRequestStatus).apply {
        observeForever {
            if (weekScheduleRequestStatus.value?.status == Status.OK && it != null) {
                bottomTimer.startTimer()
            }
        }
    }

    //////////// Состояния загрузки
    val isDataLoading = Transformations.map(weekScheduleRequestStatus) {
        it.status == Status.LOADING
    }

    //////////// Представление объектов для UI
    val scheduleByDayweek: Map<Dayweek, LiveData<ScheduleDay>> by lazy {
        mapOf(
            Dayweek.MONDAY to TimeStatusCalculator(weekSchedule, Dayweek.MONDAY).initTimeValues(),
            Dayweek.TUESDAY to TimeStatusCalculator(weekSchedule, Dayweek.TUESDAY).initTimeValues(),
            Dayweek.WEDNESDAY to TimeStatusCalculator(weekSchedule, Dayweek.WEDNESDAY).initTimeValues(),
            Dayweek.THURSDAY to TimeStatusCalculator(weekSchedule, Dayweek.THURSDAY).initTimeValues(),
            Dayweek.FRIDAY to TimeStatusCalculator(weekSchedule, Dayweek.FRIDAY).initTimeValues(),
            Dayweek.SATURDAY to TimeStatusCalculator(weekSchedule, Dayweek.SATURDAY).initTimeValues()
        )
    }

    val errorMessage: LiveData<String> = Transformations.map(weekScheduleRequestStatus) {
        if (it.status == Status.ERROR) it.msg
        else null
    }

    val bottomTimerText = bottomTimer.timerText

    //////////////////

    fun requestUpdateScheduleWeek() {
        scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)
    }

    override fun onCleared() {
        bottomTimer.stopTimer()
        super.onCleared()
    }
}
