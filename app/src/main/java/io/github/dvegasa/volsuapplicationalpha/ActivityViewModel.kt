package io.github.dvegasa.volsuapplicationalpha

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Ed Khalturin @DVegasa
 */
class ActivityViewModel : ViewModel() {
    val currentScreen = MutableLiveData<Int>().default(1)

    val isMenuShown = MutableLiveData<Boolean>().default(false)
}