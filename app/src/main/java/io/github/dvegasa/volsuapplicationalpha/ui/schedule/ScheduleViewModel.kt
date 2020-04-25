package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo

class ScheduleViewModel : ViewModel() {
    private val scheduleRepo = ScheduleRepo()

    val weekSchedule = scheduleRepo.getTestWeekSchedule()

    val curDayweek = MutableLiveData<Int>().default(1)

}
