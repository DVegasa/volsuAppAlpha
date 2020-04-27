package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.pojos.TimeStatus
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo
import io.github.dvegasa.volsuapplicationalpha.utils.default


class ScheduleViewModel : ViewModel() {
    //////////// Объекты
    private val scheduleRepo = ScheduleRepo()
    private val timeCalc = TimeCalculator()

    //////////// LiveData
    val chosenTitle = MutableLiveData<Int>().default(0).apply {
        observeForever {
            // Переключение заголовка из spintb
        }
    }

    // Уже адаптировано. Адаптация данных на уровне Repository, в данном случае ScheduleRepo
    val weekSchedule = scheduleRepo.getFakeWeekSchedule()

    val subjStatuses = MutableLiveData<List<TimeStatus>>()

    val pickedDayweekTab = MutableLiveData<Int>().default(
        timeCalc.getCurrentDayweek().value
    )

    val timerSubjToFinish = timeCalc.timerSubjToFinish

    //////////// Инициализация
    init {
        timeCalc.startTimerSubjToFinish()
        weekSchedule.observeForever {
            updateSubjStatuses()
        }
        updateSubjStatuses()
    }

    //////////// Приватные методы
    private fun updateSubjStatuses() {
        val todayDayweekIndex = timeCalc.getCurrentDayweek().value - 1
        val todaySubjes = weekSchedule.value?.get(todayDayweekIndex)
        subjStatuses.value = timeCalc.getTodaySubjStatuses(todaySubjes!!)
    }

}
