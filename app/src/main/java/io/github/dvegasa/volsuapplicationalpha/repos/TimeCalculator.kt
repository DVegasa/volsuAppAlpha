package io.github.dvegasa.volsuapplicationalpha.repos

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ed Khalturin @DVegasa
 *
 * lifecycleOwner нужен для того, чтобы не производить вычисления, пока скрыт тот экран,
 * для которого эти вычисления нужны (ScheduleFragment)
 */
@Suppress("UNREACHABLE_CODE")
class TimeCalculator {

    private fun getCurrentTime(): String {
        return "11:00"
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

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

    private fun isNowAfterSubj(subj: SubjectSchedule): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val curTime = sdf.parse(getCurrentTime())!!
        val subjEnd = sdf.parse(Timetable.subjEnd[subj.slot])

        return curTime.after(subjEnd)
    }

    private fun isNowBeforeSubj(subj: SubjectSchedule): Boolean {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val curTime = sdf.parse(getCurrentTime())!!
        val subjStart = sdf.parse(Timetable.subjStart[subj.slot])

        return curTime.before(subjStart)
    }

    /* Тестовые данные */
    fun getTodaySubjStatuses(subjes: List<SubjectSchedule>): List<SubjectStatus> {
        val list = arrayListOf<SubjectStatus>()
        for (i in subjes.indices) {
            list.add(SubjectStatus.OK)
        }
        return list
    }
}