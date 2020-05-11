package io.github.dvegasa.volsuapplicationalpha.feature.schedule

import androidx.lifecycle.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.Status

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
    private val weekSchedule = scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)

    //////////// Состояния загрузки
    val isDataLoading = Transformations.map(weekScheduleRequestStatus) {
        it.status == Status.LOADING
    }

    //////////// Представление объектов для UI
    val scheduleByDayweek: Map<Dayweek, LiveData<ScheduleDay>> by lazy {
        mapOf(
            Dayweek.MONDAY to TimeStatusCalculator(weekSchedule, Dayweek.MONDAY)
                .initTimeValues(),
            Dayweek.TUESDAY to TimeStatusCalculator(weekSchedule, Dayweek.TUESDAY)
                .initTimeValues(),
            Dayweek.WEDNESDAY to TimeStatusCalculator(weekSchedule, Dayweek.WEDNESDAY)
                .initTimeValues(),
            Dayweek.THURSDAY to TimeStatusCalculator(weekSchedule, Dayweek.THURSDAY)
                .initTimeValues(),
            Dayweek.FRIDAY to TimeStatusCalculator(weekSchedule, Dayweek.FRIDAY)
                .initTimeValues(),
            Dayweek.SATURDAY to TimeStatusCalculator(weekSchedule, Dayweek.SATURDAY)
                .initTimeValues()
        )
    }

    val errorMessage: LiveData<String> = Transformations.map(weekScheduleRequestStatus) {
        if (it.status == Status.ERROR) it.msg
        else null
    }

    val bottomTimerText = Transformations.switchMap(weekSchedule) {
        val todaySubjes =
            if (scheduleRepo.isThisWeekZnam()) weekSchedule.value?.schedule(Dayweek.current)?.znam
            else weekSchedule.value?.schedule(Dayweek.current)?.chis
        bottomTimer.init(todaySubjes)
    }

    //////////////////

    fun requestUpdateScheduleWeek() {
        scheduleRepo.getScheduleWeek(weekScheduleRequestStatus)
    }
}
