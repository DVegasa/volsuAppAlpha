package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */
data class TimeStatus (
    val status: SubjectTimeStatus,
    val msg: String
) {
    constructor(status: SubjectTimeStatus) : this(status, "")
}

enum class SubjectTimeStatus {
    SKIPPED, UPCOMING, ONGOING, FUTURE
}