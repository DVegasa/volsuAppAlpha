package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

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
        // Добавляем окна
        for (iDayweek in 1..6) { // Числ
            val max = dayweekMapChis[Dayweek.byValue(iDayweek)]!!.max()?.slot ?: continue
            val min = dayweekMapChis[Dayweek.byValue(iDayweek)]!!.min()?.slot ?: continue
            for (i in min..max) {
                val inThisSlotSubj = dayweekMapChis[Dayweek.byValue(iDayweek)]!!.filter {
                    it.slot == i
                }
                if (inThisSlotSubj.isEmpty()) dayweekMapChis[Dayweek.byValue(iDayweek)]!!.add(
                    ScheduleSubject.okno(dayweek = Dayweek.byValue(iDayweek), slot = i)
                )
            }
        }
        for (iDayweek in 1..6) { // Знам
            val max = dayweekMapZnam[Dayweek.byValue(iDayweek)]!!.max()?.slot ?: continue
            val min = dayweekMapZnam[Dayweek.byValue(iDayweek)]!!.min()?.slot ?: continue
            for (i in min..max) {
                val inThisSlotSubj = dayweekMapZnam[Dayweek.byValue(iDayweek)]!!.filter {
                    it.slot == i
                }
                if (inThisSlotSubj.isEmpty()) dayweekMapZnam[Dayweek.byValue(iDayweek)]!!.add(
                    ScheduleSubject.okno(dayweek = Dayweek.byValue(iDayweek), slot = i)
                )
            }
        }

        // Проверяем на совпадение числителя и знаменателя
        val isChisSubjectEqualZnam = Array(6) { true }
        for (iDayweek in 0..5) {
            if (dayweekMapChis[Dayweek.byValue(iDayweek + 1)]
                != dayweekMapZnam[Dayweek.byValue(iDayweek + 1)]
            ) {
                isChisSubjectEqualZnam[iDayweek] = false
            }
        }

        // Отсекаем лишние знаментальные уроки, если (уроки числ == уроки знам)
        val scheduleWeekList = arrayListOf<ScheduleDay>()
        for (dayweekValue in 1..6) {
            val scheduleDay: ScheduleDay
            scheduleDay = if (isChisSubjectEqualZnam[dayweekValue - 1]) {
                ScheduleDay(
                    chis = ArrayList(dayweekMapChis[Dayweek.byValue(dayweekValue)]!!.toList().sorted()),
                    znam = null
                )
            } else {
                ScheduleDay(
                    chis = ArrayList(dayweekMapChis[Dayweek.byValue(dayweekValue)]!!.toList().sorted()),
                    znam = ArrayList(dayweekMapZnam[Dayweek.byValue(dayweekValue)]!!.toList().sorted())
                )
            }
            scheduleWeekList.add(scheduleDay)
        }

        return ScheduleWeek(
            scheduleWeekList[0],
            scheduleWeekList[1],
            scheduleWeekList[2],
            scheduleWeekList[3],
            scheduleWeekList[4],
            scheduleWeekList[5]
        )
    }
}