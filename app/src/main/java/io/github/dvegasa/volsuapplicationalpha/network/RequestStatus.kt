package io.github.dvegasa.volsuapplicationalpha.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Created by Ed Khalturin @DVegasa
 */
data class RequestStatus(
    val status: Status,
    val msg: String? = null
)

enum class Status {
    LOADING, ERROR, OK
}

