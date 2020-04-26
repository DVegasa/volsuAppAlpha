package io.github.dvegasa.volsuapplicationalpha.pojos

import java.time.DayOfWeek

/**
 * Created by Ed Khalturin @DVegasa
 */
data class SubjectSchedule(
    val title: String,
    val teacher: String,
    val audi: String,
    val dayweek: Dayweek,
    val slot: Int,
    val period: SubjectPeriod,
    val status: SubjectStatus
)

enum class SubjectStatus {
    OK, CANCELLED
}

enum class SubjectPeriod {
    CHISLZNAM, CHISL, ZNAM
}

enum class Dayweek(val value: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7)
}