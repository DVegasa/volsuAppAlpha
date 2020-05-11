package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.*

/**
 * Created by Ed Khalturin @DVegasa
 */

const val TIMER_DELAY_RATE = 5 * 1000L

class TimeStatusCalculator(
    private val weekSchedule: LiveData<ScheduleWeek>,
    private val dayweek: Dayweek
) {
    private val handler = Handler()
    private val scheduleRepo = ScheduleRepo()

    private val ldExport = MutableLiveData<ScheduleDay>(weekSchedule.value?.schedule(dayweek))

    init {
        weekSchedule.observeForever {
            ldExport.value = weekSchedule.value?.schedule(dayweek)
            initStartTimes()
            startTimeStatusesTimer()
        }
    }

    fun initTimeValues(): LiveData<ScheduleDay> {
        return ldExport
    }

    private fun initStartTimes() {
        ldExport.value?.let { day ->
            if (day.chis.isNotEmpty()) {
                Log.d("ed__", day.chis.toString())
                val slot = day.chis.sorted()[0].slot
                day.nonTrivialStartTimeChis =
                    if (slot != 0) ScheduleTimetable.subjStart[slot] else null
            }

            if (day.znam?.isNotEmpty() == true) {
                val slot2 = day.znam.sorted()[0].slot
                day.nonTrivialStartTimeZnam =
                    if (slot2 != 0) ScheduleTimetable.subjStart[slot2] else null
            }
        }
        ldExport.value = ldExport.value
    }

    private fun startTimeStatusesTimer() {
        handler.post(timeStatusRunnable)
    }

    private fun stopTimeStatusesTimer() {
        handler.removeCallbacks(timeStatusRunnable)
    }

    private val timeStatusRunnable = object : Runnable {
        override fun run() {
            for (subj in ldExport.value!!.chis) { // Назначаем для числ предметов
                defineSubjTimeStatus(subj, isSubjZnam = false)
            }
            if (ldExport.value!!.znam != null) { // Назначаем для знам предметов
                for (subj in ldExport.value!!.znam!!) {
                    defineSubjTimeStatus(subj, isSubjZnam = true)
                }
            }
            ldExport.value = ldExport.value
            handler.postDelayed(this, TIMER_DELAY_RATE)
        }
    }

    private fun defineSubjTimeStatus(subj: ScheduleSubject, isSubjZnam: Boolean) {
        val curTime = Time.current
        val curDayweek = Dayweek.current
        if (subj.dayweek != curDayweek) { // Не сегодняшний день недели
            subj.timeStatus = TimeStatus.FUTURE
            return
        }

        if (isSubjZnam != scheduleRepo.isThisWeekZnam()) { // Сегодня знаментаельная неделя
            subj.timeStatus = TimeStatus.FUTURE
            return
        }
        // На данном этапе работаем с сегодняшними уроками
        val startTime = ScheduleTimetable.subjStart[subj.slot]
        val endTime = ScheduleTimetable.subjEnd[subj.slot]

        // Для первого урока статус COMING будет поставлен за 10 минут до его начала
        val prevSubjEndTime =
            if (subj.slot == 0) Time.fromMins(startTime.mins - 10)
            else ScheduleTimetable.subjEnd[subj.slot - 1]

        Log.d("ed__", "TimeIssue:\nprevSubjEndTime:$prevSubjEndTime\nstartTime:$startTime")
        if (curTime.isBetween(prevSubjEndTime, startTime)) {
            subj.timeStatus = TimeStatus.COMING
            subj.timeStatusMsg = getComingText(startTime.delta(curTime))
            return
        }

        if (curTime.isBefore(startTime)) {
            subj.timeStatus = TimeStatus.FUTURE
            return
        }

        if (curTime.isBetween(startTime, endTime)) {
            subj.timeStatus = TimeStatus.ONGOING
            return
        }

        if (curTime.isAfter(endTime)) {
            subj.timeStatus = TimeStatus.PAST
            return
        }
    }

    private fun getComingText(m: Int): String {
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

}