package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */
data class TimeStatus (
    val status: SubjectTimeStatuses,
    val msg: String
) {
    constructor(status: SubjectTimeStatuses) : this(status, "")
}

enum class SubjectTimeStatuses {
    SKIPPED, UPCOMING, ONGOING, FUTURE
}