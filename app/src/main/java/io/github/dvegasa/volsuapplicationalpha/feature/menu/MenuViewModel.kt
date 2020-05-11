package io.github.dvegasa.volsuapplicationalpha.feature.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {
    val username = MutableLiveData("Александр Михайловский")
    val userSubtitle = MutableLiveData("МОСб-192, №962941")
}
