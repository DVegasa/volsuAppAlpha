package io.github.dvegasa.volsuapplicationalpha.repos

import io.github.dvegasa.volsuapplicationalpha.dataprocessing.Time

/**
 * Created by Ed Khalturin @DVegasa
 */
const val BEFORE_SUBJECTS = -1
const val AFTER_SUBJECTS = -2
const val IN_BREAK = -10

object ScheduleTimetable {
    val subjStart = arrayOf(
        Time(8, 30),
        Time(10, 10),
        Time(12, 0),
        Time(13, 40),
        Time(15, 20),
        Time(17, 0),
        Time(18, 40)
    )

    val subjEnd = arrayOf(
        Time(10, 0),
        Time(11, 40),
        Time(13, 30),
        Time(15, 10),
        Time(16, 50),
        Time(18, 30),
        Time(20, 10)
    )

    fun getSubjectIndexByTime(t: Time): Int {
        for (i in subjStart.indices) {
            if (t.isBetween(subjStart[i], subjEnd[i])) return i
        }
        if (t.isBefore(subjStart[0])) return BEFORE_SUBJECTS
        if (t.isAfter(subjEnd[subjEnd.lastIndex])) return AFTER_SUBJECTS
        return IN_BREAK
    }

}