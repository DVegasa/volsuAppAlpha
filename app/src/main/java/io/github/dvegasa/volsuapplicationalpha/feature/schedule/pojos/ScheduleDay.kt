package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import android.os.Parcelable
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ed Khalturin @DVegasa
 */

@Parcelize
data class ScheduleDay(
    val chis: ArrayList<ScheduleSubject>,
    val znam: ArrayList<ScheduleSubject>?,
    @Ignore var nonTrivialStartTimeChis: Time? = null,
    @Ignore var nonTrivialStartTimeZnam: Time? = null
) : Parcelable {

    val isChisZnamIdentical: Boolean
        get() = znam == null

}