package io.github.dvegasa.volsuapplicationalpha.pojos

/**
 * Created by Ed Khalturin @DVegasa
 */


data class ScheduleWeek (
    val monday: ScheduleDay,
    val tuesday: ScheduleDay,
    val wednesday: ScheduleDay,
    val thursday: ScheduleDay,
    val friday: ScheduleDay,
    val saturday: ScheduleDay
) {
    fun schedule(dayweek: Dayweek) = when (dayweek) {
        Dayweek.MONDAY -> monday
        Dayweek.TUESDAY -> tuesday
        Dayweek.WEDNESDAY -> wednesday
        Dayweek.THURSDAY -> thursday
        Dayweek.FRIDAY -> friday
        Dayweek.SATURDAY -> saturday
        Dayweek.SUNDAY -> throw Exception("Attempt to get schedule for Dayweek.SUNDAY")
    }
}