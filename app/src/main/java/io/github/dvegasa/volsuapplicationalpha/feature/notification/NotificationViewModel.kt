package io.github.dvegasa.volsuapplicationalpha.feature.notification

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class NotificationViewModel : ViewModel() {
    val randomNumber = MutableLiveData(Random.nextInt(10, 99).toString())
}
