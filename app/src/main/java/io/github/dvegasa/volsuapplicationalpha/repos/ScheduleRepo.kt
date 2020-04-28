package io.github.dvegasa.volsuapplicationalpha.repos

import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleWeek
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus.*
import io.github.dvegasa.volsuapplicationalpha.utils.default

/**
 * Created by Ed Khalturin @DVegasa
 */
class ScheduleRepo {

    /* Fake */
    fun getFakeScheduleWeek(): MutableLiveData<ScheduleWeek> {
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
            arrayOf(
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
            arrayOf(
                SubjectSchedule.none,
                SubjectSchedule(inya, t5, "1-01 М"),
                SubjectSchedule(inya, t5, "1-01 М"),
                SubjectSchedule(matl, t7, "4-01 Г"),
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            )
        )
        val wednesday = ScheduleDay(
            arrayOf(
                SubjectSchedule(matp, t1, "3-01 А"),
                SubjectSchedule(infp, t1, "2-10 А"), // ТОЛЬКО ЧИСЛ
                SubjectSchedule(infl, t2, "4-01 М"), // ТОЛЬКО ЧИСЛ
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none,
                SubjectSchedule.none
            ),
            arrayOf(
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
            arrayOf(
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
            arrayOf(
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
            arrayOf(
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