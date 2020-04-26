package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo
import java.util.*

class ScheduleViewModel : ViewModel() {
    private val scheduleRepo = ScheduleRepo()

    val chosenTitle = MutableLiveData<Int>().default(0).apply {
        observeForever {
            // TODO Переключение заголовка из spintb
            Log.d("ed__", "VM chosenTitle")
        }
    }

    val weekSchedule = scheduleRepo.getTestWeekSchedule()

    val curDayweek = MutableLiveData<Int>().default(
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 1
        }
    )

}
