package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */
data class SubjectSchedule(
    val title: String,
    val teacher: String,
    val audi: String,
    val status: SubjectStatus = SubjectStatus.OK,
    val timeStatus: TimeStatus = TimeStatus.FUTURE,
    val timeStatusMsg: String = ""
) {
    companion object {
        val none: SubjectSchedule
            get() = SubjectSchedule("-", "", "")
    }
}

enum class TimeStatus {
    PAST, COMING, ONGOING, FUTURE
}

enum class SubjectStatus {
    OK, CANCELLED
}

