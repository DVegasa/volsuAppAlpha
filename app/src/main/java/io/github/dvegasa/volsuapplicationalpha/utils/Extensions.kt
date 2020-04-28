package io.github.dvegasa.volsuapplicationalpha.utils

import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule

/**
 * Created by Ed Khalturin @DVegasa
 */

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
fun <T : Any?> MutableLiveData<T>.defaultAsync(initialValue: T) = apply { postValue(initialValue) }

fun Array<SubjectSchedule>.firstSlot(): Int {
    for (i in this.indices) {
        if (!this[i].isOkno()) return i
    }
    return -1
}

fun Array<SubjectSchedule>.lastSlot(): Int {
    for (i in (this.size-1) downTo 0) {
        if (!this[i].isOkno()) return i
    }
    return -1
}