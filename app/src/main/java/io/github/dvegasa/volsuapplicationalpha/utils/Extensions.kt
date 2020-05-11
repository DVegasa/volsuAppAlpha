package io.github.dvegasa.volsuapplicationalpha.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleSubject

fun Context.color(id: Int) = ResourcesCompat.getColor(resources, id, null)
