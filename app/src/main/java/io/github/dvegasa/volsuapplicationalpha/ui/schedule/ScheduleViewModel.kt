package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default

class ScheduleViewModel : ViewModel() {
    val curDayweek = MutableLiveData<Int>().default(1)
}
