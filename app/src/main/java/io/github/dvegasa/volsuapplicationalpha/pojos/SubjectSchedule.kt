package io.github.dvegasa.volsuapplicationalpha.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ed Khalturin @DVegasa
 */
@Parcelize
data class SubjectSchedule(
    val title: String,
    val teacher: String,
    val audi: String,
    val status: SubjectStatus = SubjectStatus.OK,
    var timeStatus: TimeStatus = TimeStatus.FUTURE,
    var timeStatusMsg: String = ""
) : Parcelable {
    companion object {
        val none: SubjectSchedule
            get() = SubjectSchedule("-", "", "")
    }

    fun isOkno() = title == "-"
}

enum class TimeStatus {
    PAST, COMING, ONGOING, FUTURE
}

enum class SubjectStatus {
    OK, CANCELLED
}

