package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ed Khalturin @DVegasa
 */
const val MAX_SUBJES_IN_DAY = 7

@Parcelize
data class ScheduleDay(
    val chis: ArrayList<SubjectSchedule>,
    val znam: ArrayList<SubjectSchedule> = chis
) : Parcelable {
    val isChisZnamIdentical: Boolean
        get() = znam == chis

    init {
        if (chis.size != MAX_SUBJES_IN_DAY || znam.size != MAX_SUBJES_IN_DAY)
            throw Exception("Массивы в ScheduleDay обязаны быть размера $MAX_SUBJES_IN_DAY")
    }
}