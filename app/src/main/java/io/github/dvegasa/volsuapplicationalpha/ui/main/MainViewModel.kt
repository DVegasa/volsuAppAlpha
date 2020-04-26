package io.github.dvegasa.volsuapplicationalpha.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default
import kotlin.random.Random

class MainViewModel : ViewModel() {
    val randomNumber = MutableLiveData<String>().default(Random.nextInt(10, 99).toString())
}
