package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo
import io.github.dvegasa.volsuapplicationalpha.repos.TimeCalculator
import java.util.*

class ScheduleViewModel : ViewModel() {
    private val scheduleRepo = ScheduleRepo()
    private val timeCalc = TimeCalculator()

    val chosenTitle = MutableLiveData<Int>().default(0).apply {
        observeForever {
            // Переключение заголовка из spintb
        }
    }

    // Уже адаптировано. Адаптация данных на уровне Repository, в данном случае ScheduleRepo
    val weekSchedule = scheduleRepo.getFakeWeekSchedule()

    val subjStatuses = MutableLiveData<List<SubjectStatus>>()

    val pickedDayweekTab = MutableLiveData<Int>().default(
        timeCalc.getCurrentDayweek().value
    )

    val timerCaption = MutableLiveData<String>().default("Стандартный текстик")

    val timerMain = MutableLiveData<String>().default("42 минуты")

    init {
        // TODO: Каждые 15 секунд обновлять информацию таймеров
        weekSchedule.observeForever {
            updateSubjStatuses()
        }
    }
    private fun updateSubjStatuses() {
        val todayDayweekIndex = timeCalc.getCurrentDayweek().value - 1
        Log.d("ed__", "todayDayweekIndex: $todayDayweekIndex")

        // val todaySubjes = weekSchedule.value?.get(todayDayweekIndex)
        Log.d("ed__", "weekSchedule.value: ${weekSchedule.value}")

//        subjStatuses.value = timeCalc.getTodaySubjStatuses(todaySubjes)
    }

}
