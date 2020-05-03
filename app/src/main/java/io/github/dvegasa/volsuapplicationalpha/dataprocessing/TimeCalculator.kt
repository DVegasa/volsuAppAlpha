package io.github.dvegasa.volsuapplicationalpha.dataprocessing

import android.util.Log
import io.github.dvegasa.volsuapplicationalpha.pojos.*
import io.github.dvegasa.volsuapplicationalpha.repos.IN_BREAK
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleTimetable
import java.util.*

/**
 * Created by Ed Khalturin @DVegasa
 */

class TimeCalculator {

    companion object {
        val currentDayweek: Dayweek
            get() {
                // /* Fake */ return Dayweek.SUNDAY
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

//        fun defineTimeStatuses(
//            subjes: ArrayList<SubjectSchedule>,
//            dayweek: Dayweek,
//            isListZnam: Boolean,
//            isWeekRealZnam: Boolean
//        ) {
//            for (i in 0 until MAX_SUBJES_IN_DAY) {
//                val startTime = ScheduleTimetable.subjStart[i]
//                val endTime = ScheduleTimetable.subjEnd[i]
//                val s = subjes[i]
//                val prevSubjEndTime =
//                    if (i == 0) Time.fromMins(startTime.mins - 10) // Для первого урока статус ONGOING будет поставлен за десять минут до его начала
//                    else ScheduleTimetable.subjEnd[i - 1]
//
//                val isPeriodCorrect = isListZnam == isWeekRealZnam
//
//                when {
//                    currentDayweek != dayweek || !isPeriodCorrect -> {
//                        s.timeStatus = TimeStatus.FUTURE
//                    }
//
//                    Time.current.isBetween(startTime, endTime) -> {
//                        s.timeStatus = TimeStatus.ONGOING
//                    }
//
//                    Time.current.isBetween(prevSubjEndTime, startTime) -> {
//                        s.timeStatus = TimeStatus.COMING
//                        s.timeStatusMsg = stringFromMins(startTime.mins - Time.current.mins)
//                    }
//
//                    Time.current.isAfter(startTime) -> {
//                        s.timeStatus = TimeStatus.PAST
//                    }
//
//                }
//            }
//        }

        private fun stringFromMins(m: Int): String {
            return when {
                m <= 0 -> "ноль"
                m <= 1 -> "Начнётся через 1 минуту"
                m <= 2 -> "Начнётся через 2 минуты"
                m <= 3 -> "Начнётся через 3 минуты"
                m <= 4 -> "Начнётся через 4 минуты"
                m <= 5 -> "Начнётся через 5 минут"
                m <= 6 -> "Начнётся через 6 минут"
                m <= 7 -> "Начнётся через 7 минут"
                m <= 8 -> "Начнётся через 8 минут"
                m <= 9 -> "Начнётся через 9 минут"
                m <= 10 -> "Начнётся через 10 минут"
                m <= 11 -> "Начнётся через 11 минут"
                m <= 12 -> "Начнётся через 12 минут"
                m <= 13 -> "Начнётся через 13 минут"
                m <= 14 -> "Начнётся через 14 минут"
                m <= 15 -> "Начнётся через 15 минут"
                m <= 16 -> "Начнётся через 16 минут"
                m <= 17 -> "Начнётся через 17 минут"
                m <= 18 -> "Начнётся через 18 минут"
                m <= 19 -> "Начнётся через 19 минут"
                m <= 20 -> "Начнётся через 20 минут"
                m > 20 -> "До начала более 20 минут"
                else -> "Ошибка замера"
            }
        }

        fun getTimerValue(): Int {
            val curIndex = ScheduleTimetable.getSubjectIndexByTime(Time.current)
            if (curIndex in 0 until MAX_SUBJES_IN_DAY) {
                val subjEnd = ScheduleTimetable.subjEnd[curIndex]
                return subjEnd.mins - Time.current.mins
            }
            return -1
        }

        fun stringMin(n: Int): String {
            val textForms = arrayOf("минута", "минуты", "минут")
            val n1 = n % 10
            val f: String
            f = when {
                n in 11..19 -> textForms[2]
                n1 in 2..4 -> textForms[1]
                n1 == 1 -> textForms[0]
                else -> textForms[2]
            }
            return "$n $f"
        }
    }
}
