package io.github.dvegasa.volsuapplicationalpha.dataprocessing

import io.github.dvegasa.volsuapplicationalpha.pojos.*
import io.github.dvegasa.volsuapplicationalpha.repos.Timetable
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ed Khalturin @DVegasa
 */

const val UPCOMING_APPROACH_TIME = 10 * 60 // min * sec

@Suppress("UNREACHABLE_CODE")
class TimeCalculator {

    fun getCurrentDayweek(): Dayweek {
        return Dayweek.MONDAY
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

//    fun getTodaySubjStatuses(subjes: List<SubjectSchedule>): List<TimeStatus> {
//        val list = arrayListOf<TimeStatus>()
//        for (i in subjes.indices) {
//
//            // Пропущенная пара
//            if (isSubjSkipped(subjes[i])) {
//                list.add(TimeStatus(SubjectTimeStatus.SKIPPED))
//            }
//            // Предстоящая пара (скоро будет)
//            else if (isSubjUpcoming(subjes[i]).isNotEmpty()) {
//                list.add(TimeStatus(SubjectTimeStatus.UPCOMING, isSubjUpcoming(subjes[i])))
//            }
//            // Пара будет
//            else if (isSubjFuture(subjes[i])) {
//                list.add(TimeStatus(SubjectTimeStatus.FUTURE))
//            }
//            // Пара идёт сейчас
//            else {
//                list.add(TimeStatus(SubjectTimeStatus.ONGOING))
//            }
//        }
//        return list
//    }

    fun getTimeLeft(): Long {
        for (et in Timetable.subjEnd) {
            if (et == "xxx") continue
            // endTime == "10:00"
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val curTime = sdf.parse(getCurrentTime())!!
            val endTime = sdf.parse(et)

            val delta = (endTime.time - curTime.time) / (1000 * 60) // в минутах
            if (delta in 0..90) {
                return delta
            }
        }
        return -1
    }

//    private fun isSubjUpcoming(subj: SubjectSchedule): String {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val curTime = sdf.parse(getCurrentTime())!!
//        val subjStart = sdf.parse(Timetable.subjStart[subj.slot])
//
//        val delta = (subjStart.time - curTime.time) / 1000 // in seconds
//        return if (delta <= UPCOMING_APPROACH_TIME) {
//            stringFromSeconds(delta)
//        } else {
//            ""
//        }
//    }

    private fun getCurrentTime(): String {
        val s = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        return s
    }

//    private fun isSubjSkipped(subj: SubjectSchedule): Boolean {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val curTime = sdf.parse(getCurrentTime())!!
//        val subjEnd = sdf.parse(Timetable.subjEnd[subj.slot])
//
//        return curTime.after(subjEnd)
//    }

//    private fun isSubjFuture(subj: SubjectSchedule): Boolean {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val curTime = sdf.parse(getCurrentTime())!!
//        val subjStart = sdf.parse(Timetable.subjStart[subj.slot])
//
//        return curTime.before(subjStart)
//    }

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
