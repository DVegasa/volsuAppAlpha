package io.github.dvegasa.volsuapplicationalpha.utils

import android.app.Application
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule

/**
 * Created by Ed Khalturin @DVegasa
 */

@Deprecated("") fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
@Deprecated("") fun <T : Any?> MutableLiveData<T>.defaultAsync(initialValue: T) = apply { postValue(initialValue) }

fun ArrayList<SubjectSchedule>.firstNonOknoIndex(): Int {
    for (i in this.indices) {
        if (!this[i].isOkno()) return i
    }
    return -1
}

fun ArrayList<SubjectSchedule>.lastNonOknoIndex(): Int {
    for (i in (this.size-1) downTo 0) {
        if (!this[i].isOkno()) return i
    }
    return -1
}

fun Context.color(id: Int) = ResourcesCompat.getColor(resources, id, null)
