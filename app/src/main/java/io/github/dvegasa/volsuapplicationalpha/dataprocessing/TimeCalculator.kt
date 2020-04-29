package io.github.dvegasa.volsuapplicationalpha.dataprocessing

import io.github.dvegasa.volsuapplicationalpha.pojos.*
import java.util.*

/**
 * Created by Ed Khalturin @DVegasa
 */
const val UPCOMING_APPROACH_TIME = 10 * 60 // min * sec

class TimeCalculator {

    companion object {
        val currentDayweek: Dayweek
        get () {
            /* Fake */ return Dayweek.MONDAY
            return when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> Dayweek.MONDAY
                Calendar.TUESDAY -> Dayweek.TUESDAY
                Calendar.WEDNESDAY -> Dayweek.WEDNESDAY
                Calendar.THURSDAY -> Dayweek.THURSDAY
                Calendar.FRIDAY -> Dayweek.FRIDAY
                Calendar.SATURDAY -> Dayweek.SATURDAY
                Calendar.SUNDAY -> Dayweek.SUNDAY
            }
        }

        fun defineTimeStatuses(subjes: ArrayList<SubjectSchedule>) {
            for (i in 0 until MAX_SUBJES_IN_DAY) {

            }
        }
    }



    fun stringMin(n: Long): String {
        val textForms = arrayOf("минута", "минуты", "минут")
        val n1 = n % 10
        val f: String
        f = when {
            n in 11..19 -> textForms[2]
            n1 in 2..4 -> textForms[1]
            n1 == 1L -> textForms[0]
            else -> textForms[2]
        }
        return "$n $f"
    }

    private fun stringFromSeconds(sec: Long): String {
        val m = 60
        return when {
            sec <= 0 -> ""
            sec <= 1 * m -> "Начнётся через 1 минуту"
            sec <= 2 * m -> "Начнётся через 2 минуты"
            sec <= 3 * m -> "Начнётся через 3 минуты"
            sec <= 4 * m -> "Начнётся через 4 минуты"
            sec <= 5 * m -> "Начнётся через 5 минут"
            sec <= 6 * m -> "Начнётся через 6 минут"
            sec <= 7 * m -> "Начнётся через 7 минут"
            sec <= 8 * m -> "Начнётся через 8 минут"
            sec <= 9 * m -> "Начнётся через 9 минут"
            sec <= 10 * m -> "Начнётся через 10 минут"
            sec <= 11 * m -> "Начнётся через 11 минут"
            sec <= 12 * m -> "Начнётся через 12 минут"
            sec <= 13 * m -> "Начнётся через 13 минут"
            sec <= 14 * m -> "Начнётся через 14 минут"
            sec <= 15 * m -> "Начнётся через 15 минут"
            else -> ""
        }
    }
}
