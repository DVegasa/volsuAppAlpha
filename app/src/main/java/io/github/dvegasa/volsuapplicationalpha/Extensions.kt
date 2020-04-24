package io.github.dvegasa.volsuapplicationalpha

import androidx.lifecycle.MutableLiveData

/**
 * Created by Ed Khalturin @DVegasa
 */

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
fun <T : Any?> MutableLiveData<T>.defaultAsync(initialValue: T) = apply { postValue(initialValue) }
