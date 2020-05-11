package io.github.dvegasa.volsuapplicationalpha.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel : ViewModel() {
    val randomNumber = MutableLiveData(Random.nextInt(10, 99).toString())
}
