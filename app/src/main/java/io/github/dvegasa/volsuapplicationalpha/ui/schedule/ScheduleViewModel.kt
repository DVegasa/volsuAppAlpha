package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
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

    // val subjStatuses = MutableLiveData<List<TimeStatus>>()

    val pickedDayweekTab = MutableLiveData<Int>().default(
        TimeCalculator.getCurrentDayweek().value
    )

    val timerSubjToFinish = timeCalc.timerSubjToFinish

    //////////// Инициализация
    init {
        timeCalc.startTimerSubjToFinish()
        weekSchedule.observeForever {
            // updateSubjStatuses()
        }
        updateSubjStatuses()
    }

    //////////// Приватные методы
    private fun updateSubjStatuses() {
        val todayDayweekIndex = TimeCalculator.getCurrentDayweek().value - 1
        val tempList = weekSchedule.value!!.toMutableList()
        val todaySubjes = tempList[todayDayweekIndex]
        val list = timeCalc.updateSubjStatuses(todaySubjes)
        tempList[todayDayweekIndex] = list
        weekSchedule.value = tempList
    }

}
