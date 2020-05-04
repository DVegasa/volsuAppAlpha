package io.github.dvegasa.volsuapplicationalpha.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.MyApplication
import io.github.dvegasa.volsuapplicationalpha.network.DataRequest
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.VolsuNikitaApi
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleWeek
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus.*
import io.github.dvegasa.volsuapplicationalpha.pojos.web_responses.TimetableResponse
import io.github.dvegasa.volsuapplicationalpha.utils.default
import retrofit2.Call

/**
 * Created by Ed Khalturin @DVegasa
 */
class ScheduleRepo {

    fun getScheduleWeek(requestStatus: MutableLiveData<RequestStatus>): LiveData<ScheduleWeek> {

        return object : DataRequest<List<TimetableResponse>, ScheduleWeek>(requestStatus) {
            override fun shouldFetch() = true

            override fun getCachedData(): LiveData<ScheduleWeek> {
                return MyApplication.instance.appDatabase.cacheDao().getScheduleWeek()
            }

            override fun getRetrofitCall(): Call<List<TimetableResponse>> {
                return VolsuNikitaApi.create().getFakeTimetable()
            }

            override fun cacheResult(data: ScheduleWeek) {
                MyApplication.instance.appDatabase.cacheDao().updateScheduleWeek(data)
            }

            override fun requestToResult(request: List<TimetableResponse>): ScheduleWeek {
                val cells = request[0].timetable[0].cells

                val dayweekMapChis = mutableMapOf<Int, Array<SubjectSchedule>>()
                val dayweekMapZnam = mutableMapOf<Int, Array<SubjectSchedule>>()

                for (dayweekIndex in 0..5) {
                    dayweekMapChis[dayweekIndex] = Array(7) { SubjectSchedule.none }
                    dayweekMapZnam[dayweekIndex] = Array(7) { SubjectSchedule.none }
                }

                for (cell in cells) {
                    val dayweekIndex = cell.time[0].day.toInt()
                    val pairSlot = cell.time[0].pair.toInt()

                    val subjectTitle = cell.lesson[0].discipline[0].name
                    val teacherName = cell.lesson[0].teacher[0].name
                    val audi = cell.lesson[0].room[0].name

                    val subjScheduleChis = SubjectSchedule(subjectTitle, teacherName, audi)
                    dayweekMapChis[dayweekIndex]?.set(pairSlot, subjScheduleChis)

                    val secondIndex = if (cell.lesson.size == 1) 0 else 1

                    val subjectTitleZnam = cell.lesson[secondIndex].discipline[0].name
                    val teacherNameZnam = cell.lesson[secondIndex].teacher[0].name
                    val audiZnam = cell.lesson[secondIndex].room[0].name

                    val subjScheduleZnam =
                        SubjectSchedule(subjectTitleZnam, teacherNameZnam, audiZnam)
                    dayweekMapZnam[dayweekIndex]?.set(pairSlot, subjScheduleZnam)

                }

                val isChisSubjectEqualZnam = Array(6) { true }

                outside@ for (dayweekIndex in 0..5) {
                    for (pairSlot in 0..6) {
                        if (dayweekMapZnam[dayweekIndex]?.get(pairSlot)!!
                            != dayweekMapChis[dayweekIndex]?.get(pairSlot)
                        ) {
                            isChisSubjectEqualZnam[dayweekIndex] = false
                            continue@outside
                        }
                    }
                }

                val scheduleWeekList = arrayListOf<ScheduleDay>()
                for (dayweekIndex in 0..5) {
                    val scheduleDay: ScheduleDay
                    if (isChisSubjectEqualZnam[dayweekIndex]) {
                        scheduleDay = ScheduleDay(
                            chis = ArrayList(dayweekMapChis[dayweekIndex]!!.toList())
                        )
                    } else {
                        scheduleDay = ScheduleDay(
                            chis = ArrayList(dayweekMapChis[dayweekIndex]!!.toList()),
                            znam = ArrayList(dayweekMapZnam[dayweekIndex]!!.toList())
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

        }.asLiveData()
    }

    /* Fake */
    fun getFakeScheduleWeek(): LiveData<ScheduleWeek> {
        val algl = "Алгебра и теория чисел (л)"
        val algp = "Алгебра и теория чисел (пр)"
        val infl = "Информатика и программирование"
        val infp = "Информатика и программирование"
        val matl = "Математический анализ (л)"
        val matp = "Математический анализ (пр)"
        val inya = "Иностранный язык"
        val fizl = "Физкультура (л)"
        val fizp = "Физкультура (пр)"

        val t1 = "Пеньшин. В.А."
        val t2 = "Кулебяка О.У."
        val t3 = "Косенков У.В."
        val t4 = "Юристиченко У.А."
        val t5 = "Маринкина К.К."
        val t6 = "Шол О.В,"
        val t7 = "Грац И.К."
        val t8 = "Леньков Л.Л."
        val t9 = "Иванова Е.Л"

        val monday = ScheduleDay(
            arrayListOf(
                SubjectSchedule(algl, t1, "3-01 А"),
                SubjectSchedule.none,
                SubjectSchedule(algp, t1, "3-01 В"),
                SubjectSchedule(algl, t2, "4-01 Г"),
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val tuesday = ScheduleDay(
            arrayListOf(
                SubjectSchedule.none,
                SubjectSchedule(inya, t5, "1-01 М"),
                SubjectSchedule.none,
                SubjectSchedule(matl, t7, "4-01 Г"),
                SubjectSchedule(inya, t5, "1-01 М"),
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val wednesday = ScheduleDay(
            arrayListOf(
                SubjectSchedule(matp, t1, "3-01 А"),
                SubjectSchedule(infp, t1, "2-10 А"), // ТОЛЬКО ЧИСЛ
                SubjectSchedule(infl, t2, "4-01 М"), // ТОЛЬКО ЧИСЛ
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            ),
            arrayListOf(
                SubjectSchedule.none,
                SubjectSchedule(matp, t1, "2-11 В"), // ТОЛЬКО ЗНАМ
                SubjectSchedule(matp, t1, "3-01 А"), // ТОЛЬКО ЗНАМ
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val thursday = ScheduleDay(
            arrayListOf(
                SubjectSchedule(algl, t1, "3-01 А"),
                SubjectSchedule.none,
                SubjectSchedule(algp, t1, "3-01 В"),
                SubjectSchedule(algl, t2, "4-01 Г"),
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val friday = ScheduleDay(
            arrayListOf(
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val saturday = ScheduleDay(
            arrayListOf(
                SubjectSchedule(algl, t1, "1-01 Б"),
                SubjectSchedule(matl, t1, "1-01 В"),
                SubjectSchedule(fizp, t3, "3-07 Б"),
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        return MutableLiveData<ScheduleWeek>().default(
            ScheduleWeek(monday, tuesday, wednesday, thursday, friday, saturday)
        )
    }
}