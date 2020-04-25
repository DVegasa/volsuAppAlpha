package io.github.dvegasa.volsuapplicationalpha.ui.menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default

class MenuViewModel : ViewModel() {
    val username = MutableLiveData<String>().default("Александр Михайловский")
    val userSubtitle = MutableLiveData<String>().default("МОСб-192, №962941")
}
