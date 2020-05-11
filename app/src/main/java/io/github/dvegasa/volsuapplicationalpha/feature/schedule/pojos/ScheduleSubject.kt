package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ed Khalturin @DVegasa
 */
@Parcelize
data class ScheduleSubject(
    val title: String,
    val teacher: String,
    val audi: String,
    val dayweek: Dayweek,
    val slot: Int,
    var status: SubjectStatus = SubjectStatus.OK,
    var timeStatus: TimeStatus = TimeStatus.FUTURE,
    var timeStatusMsg: String? = null
) : Parcelable, Comparable<ScheduleSubject> {
    companion object {
        fun okno(dayweek: Dayweek, slot: Int) = ScheduleSubject(
            "-",
            "",
            "",
            dayweek, slot
        )
    }

    fun isOkno() = title == "-"

    override fun compareTo(other: ScheduleSubject): Int {
        if (this.slot < other.slot) return -1
        if (this.slot > other.slot) return 1
        return 0
    }
}

enum class TimeStatus {
    PAST, COMING, ONGOING, FUTURE
}

enum class SubjectStatus {
    OK, CANCELLED
}

