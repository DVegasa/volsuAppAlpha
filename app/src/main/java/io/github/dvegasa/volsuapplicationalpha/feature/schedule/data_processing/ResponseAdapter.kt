package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

import android.util.Log
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class ResponseAdapter(private val timetableResponse: TimetableResponse) {
    fun toScheduleWeek(): ScheduleWeek {
        val request = timetableResponse
        val cells = request.timetable[0].cells

        val dayweekMapChis = mutableMapOf<Dayweek, ArrayList<ScheduleSubject>>(
            Dayweek.MONDAY to arrayListOf(),
            Dayweek.TUESDAY to arrayListOf(),
            Dayweek.WEDNESDAY to arrayListOf(),
            Dayweek.THURSDAY to arrayListOf(),
            Dayweek.FRIDAY to arrayListOf(),
            Dayweek.SATURDAY to arrayListOf()
        )
        val dayweekMapZnam = mutableMapOf<Dayweek, ArrayList<ScheduleSubject>>(
            Dayweek.MONDAY to arrayListOf(),
            Dayweek.TUESDAY to arrayListOf(),
            Dayweek.WEDNESDAY to arrayListOf(),
            Dayweek.THURSDAY to arrayListOf(),
            Dayweek.FRIDAY to arrayListOf(),
            Dayweek.SATURDAY to arrayListOf()
        )

        for (cell in cells) {
            // Добавляем в числитель
            val dayweekIndex = cell.time[0].day.toInt()
            val pairSlot = cell.time[0].pair.toInt()
            val subjectTitle = cell.lesson[0].discipline[0].name
            val teacherName = cell.lesson[0].teacher[0].name
            val audi = cell.lesson[0].room[0].name
            val subj = ScheduleSubject(
                subjectTitle,
                teacherName,
                audi,
                Dayweek.byValue(dayweekIndex + 1),
                pairSlot
            )
            dayweekMapChis[subj.dayweek]!!.add(subj)

            // Добавляем в знаменатель
            val secondIndex = if (cell.lesson.size == 2) 1 else 0
            val dayweekIndex2 = cell.time[0].day.toInt()
            val pairSlot2 = cell.time[0].pair.toInt()
            val subjectTitle2 = cell.lesson[secondIndex].discipline[0].name
            val teacherName2 = cell.lesson[secondIndex].teacher[0].name
            val audi2 = cell.lesson[secondIndex].room[0].name
            val subj2 = ScheduleSubject(
                subjectTitle2,
                teacherName2,
                audi2,
                Dayweek.byValue(dayweekIndex2 + 1),
                pairSlot2
            )
            dayweekMapZnam[subj2.dayweek]!!.add(subj2)
        }

        val isChisSubjectEqualZnam = Array(6) { true }
        for (iDayweek in 0..5) {
            if (dayweekMapChis[Dayweek.byValue(iDayweek + 1)]
                != dayweekMapZnam[Dayweek.byValue(iDayweek + 1)]
            ) {
                isChisSubjectEqualZnam[iDayweek] = false
            }
        }

        val scheduleWeekList = arrayListOf<ScheduleDay>()
        for (dayweekValue in 1..6) {
            val scheduleDay: ScheduleDay
            Log.d("ed__", "ResponseAdapter:\n${dayweekMapChis}")
            if (isChisSubjectEqualZnam[dayweekValue - 1]) {
                scheduleDay =
                    ScheduleDay(
                        chis = ArrayList(dayweekMapChis[Dayweek.byValue(dayweekValue)]!!.toList()),
                        znam = null
                    )
            } else {
                scheduleDay =
                    ScheduleDay(
                        chis = ArrayList(dayweekMapChis[Dayweek.byValue(dayweekValue)]!!.toList()),
                        znam = ArrayList(dayweekMapZnam[Dayweek.byValue(dayweekValue)]!!.toList())
                    )
            }
            scheduleWeekList.add(scheduleDay)
        }

        Log.d("ed__", "ResponseAdapter:\n${scheduleWeekList[2]}")
        return ScheduleWeek(
            scheduleWeekList[0],
            scheduleWeekList[1],
            scheduleWeekList[2],
            scheduleWeekList[3],
            scheduleWeekList[4],
            scheduleWeekList[5]
        )
    }

//    val cells = request[0].timetable[0].cells
//
//    val dayweekMapChis = mutableMapOf<Int, Array<SubjectSchedule>>()
//    val dayweekMapZnam = mutableMapOf<Int, Array<SubjectSchedule>>()
//
//    for (dayweekIndex in 0..5) {
//        dayweekMapChis[dayweekIndex] = Array(7) { SubjectSchedule.okno }
//        dayweekMapZnam[dayweekIndex] = Array(7) { SubjectSchedule.okno }
//    }
//
//    for (cell in cells) {
//        val dayweekIndex = cell.time[0].day.toInt()
//        val pairSlot = cell.time[0].pair.toInt()
//
//        val subjectTitle = cell.lesson[0].discipline[0].name
//        val teacherName = cell.lesson[0].teacher[0].name
//        val audi = cell.lesson[0].room[0].name
//
//        val subjScheduleChis =
//            SubjectSchedule(
//                subjectTitle,
//                teacherName,
//                audi
//            )
//        dayweekMapChis[dayweekIndex]?.set(pairSlot, subjScheduleChis)
//
//        val secondIndex = if (cell.lesson.size == 1) 0 else 1
//
//        val subjectTitleZnam = cell.lesson[secondIndex].discipline[0].name
//        val teacherNameZnam = cell.lesson[secondIndex].teacher[0].name
//        val audiZnam = cell.lesson[secondIndex].room[0].name
//
//        val subjScheduleZnam =
//            SubjectSchedule(
//                subjectTitleZnam,
//                teacherNameZnam,
//                audiZnam
//            )
//        dayweekMapZnam[dayweekIndex]?.set(pairSlot, subjScheduleZnam)
//
//    }
//
//
//    val isChisSubjectEqualZnam = Array(6) { true }
//
//    outside@ for (dayweekIndex in 0..5) {
//        for (pairSlot in 0..6) {
//            if (dayweekMapZnam[dayweekIndex]?.get(pairSlot)!!
//                != dayweekMapChis[dayweekIndex]?.get(pairSlot)
//            ) {
//                isChisSubjectEqualZnam[dayweekIndex] = false
//                continue@outside
//            }
//        }
//    }
//
//    val scheduleWeekList = arrayListOf<ScheduleDay>()
//    for (dayweekIndex in 0..5) {
//        val scheduleDay: ScheduleDay
//        if (isChisSubjectEqualZnam[dayweekIndex]) {
//            scheduleDay =
//                ScheduleDay(
//                    chis = ArrayList(dayweekMapChis[dayweekIndex]!!.toList())
//                )
//        } else {
//            scheduleDay =
//                ScheduleDay(
//                    chis = ArrayList(dayweekMapChis[dayweekIndex]!!.toList()),
//                    znam = ArrayList(dayweekMapZnam[dayweekIndex]!!.toList())
//                )
//        }
//        scheduleWeekList.add(scheduleDay)
//    }
//
//    return ScheduleWeek(
//    scheduleWeekList[0],
//    scheduleWeekList[1],
//    scheduleWeekList[2],
//    scheduleWeekList[3],
//    scheduleWeekList[4],
//    scheduleWeekList[5]
//    )
//}
}