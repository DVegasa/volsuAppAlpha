package io.github.dvegasa.volsuapplicationalpha.utils

import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleSubject

/**
 * Created by Ed Khalturin @DVegasa
 */

@Deprecated("") fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
@Deprecated("") fun <T : Any?> MutableLiveData<T>.defaultAsync(initialValue: T) = apply { postValue(initialValue) }

fun ArrayList<ScheduleSubject>.firstNonOknoIndex(): Int {
    for (i in this.indices) {
        if (!this[i].isOkno()) return i
    }
    return -1
}

fun ArrayList<ScheduleSubject>.lastNonOknoIndex(): Int {
    for (i in (this.size-1) downTo 0) {
        if (!this[i].isOkno()) return i
    }
    return -1
}

fun Context.color(id: Int) = ResourcesCompat.getColor(resources, id, null)
