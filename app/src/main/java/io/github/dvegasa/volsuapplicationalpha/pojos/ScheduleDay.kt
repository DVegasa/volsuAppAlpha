package io.github.dvegasa.volsuapplicationalpha.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ed Khalturin @DVegasa
 */
const val MAX_SLOT = 7

@Parcelize
data class ScheduleDay(
    val chis: Array<SubjectSchedule>,
    val znam: Array<SubjectSchedule> = chis
) : Parcelable {
    val isChisZnamIdentical: Boolean
        get() = znam.contentEquals(chis)

    init {
        if (chis.size != MAX_SLOT || znam.size != MAX_SLOT)
            throw Exception("Массивы в ScheduleDay обязаны быть размера $MAX_SLOT")
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScheduleDay

        if (!chis.contentEquals(other.chis)) return false
        if (!znam.contentEquals(other.znam)) return false

        return true
    }
    override fun hashCode(): Int {
        var result = chis.contentHashCode()
        result = 31 * result + znam.contentHashCode()
        return result
    }
}