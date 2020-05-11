package io.github.dvegasa.volsuapplicationalpha.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Ed Khalturin @DVegasa
 */
class ActivityViewModel : ViewModel() {
    val currentScreen = MutableLiveData(1)

    val isMenuShown = MutableLiveData(false)
}