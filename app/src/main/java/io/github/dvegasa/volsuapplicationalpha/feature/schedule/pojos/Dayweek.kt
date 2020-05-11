package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
enum class Dayweek(val value: Int) : Parcelable {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    companion object {
        val current: Dayweek
            get() {
                // /* Fake */ return Dayweek.THURSDAY
                return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                    Calendar.MONDAY -> Dayweek.MONDAY
                    Calendar.TUESDAY -> Dayweek.TUESDAY
                    Calendar.WEDNESDAY -> Dayweek.WEDNESDAY
                    Calendar.THURSDAY -> Dayweek.THURSDAY
                    Calendar.FRIDAY -> Dayweek.FRIDAY
                    Calendar.SATURDAY -> Dayweek.SATURDAY
                    else -> Dayweek.SUNDAY
                }
            }

        fun byValue(v: Int): Dayweek = when (v) {
            1 -> MONDAY
            2 -> TUESDAY
            3 -> WEDNESDAY
            4 -> THURSDAY
            5 -> FRIDAY
            6 -> SATURDAY
            else -> SUNDAY
        }
    }


}