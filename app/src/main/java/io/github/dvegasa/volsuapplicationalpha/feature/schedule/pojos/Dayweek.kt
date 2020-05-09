package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Dayweek(val value: Int) : Parcelable {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7)
}