package io.github.dvegasa.volsuapplicationalpha.network

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

