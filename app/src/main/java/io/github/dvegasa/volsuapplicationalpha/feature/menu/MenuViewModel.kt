package io.github.dvegasa.volsuapplicationalpha.feature.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.utils.default

class MenuViewModel : ViewModel() {
    val username = MutableLiveData<String>().default("Александр Михайловский")
    val userSubtitle = MutableLiveData<String>().default("МОСб-192, №962941")
}
