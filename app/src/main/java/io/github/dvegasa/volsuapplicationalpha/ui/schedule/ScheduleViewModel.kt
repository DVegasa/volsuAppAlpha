package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleRepo
import io.github.dvegasa.volsuapplicationalpha.utils.default


class ScheduleViewModel : ViewModel() {
    //////////// Объекты
    private val scheduleRepo = ScheduleRepo()

    //////////// LiveData
    val chosenTitle = MutableLiveData<Int>().default(0)

    val weekSchedule = scheduleRepo.getFakeScheduleWeek()

    val pickedDayweekTab = MutableLiveData<Int>().default(
        TimeCalculator.currentDayweek.value
    )


}
