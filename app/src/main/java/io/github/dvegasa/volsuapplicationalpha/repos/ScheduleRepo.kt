package io.github.dvegasa.volsuapplicationalpha.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectScheduleNew
import java.time.DayOfWeek
import java.util.*
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek.*
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectPeriod.*
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class ScheduleRepo {

    /* Метод для демонстрации поведения приложения */
    fun getFakeWeekSchedule(): LiveData<List<List<SubjectScheduleNew>>> {
        val algl = "Алгебра и теория чисел (л)"
        val algp = "Алгебра и теория чисел (пр)"
        val infl = "Информатика и программирование (л)"
        val infp = "Информатика и программирование (пр)"
        val matl = "Математический анализ (л)"
        val matp = "Математический анализ (пр)"
        val inya = "Иностранный язык"
        val okno = "-"
        val day1 = listOf(
            SubjectScheduleNew(algl, "Косенко Д.А.", "3-01 А", MONDAY, 1, CHISLZNAM, OK),
            SubjectScheduleNew(algp, "Пенкорь Е.В.", "4-02 А", MONDAY, 2, CHISLZNAM, OK),
            SubjectScheduleNew(infl, "Романвский Е.Е.", "1-01 В", MONDAY, 3, CHISLZNAM, OK),
            SubjectScheduleNew(infl, "Романвский Е.Е.", "1-01 В", MONDAY, 4, CHISLZNAM, OK)
        )
        val day2 = listOf(
            SubjectScheduleNew(
                "Правоведение",
                "Юристиченко Д.О.",
                "4-07 А",
                TUESDAY,
                3,
                CHISLZNAM,
                OK
            ),
            SubjectScheduleNew(
                "Прикладная физическая культура",
                "Пенкорь Е.В.",
                "4-02 А",
                TUESDAY,
                4,
                CHISLZNAM,
                OK
            )
        )
        val day3 = listOf(
            SubjectScheduleNew(infp, "Романвский Е.Е.", "3-01 А", WEDNESDAY, 1, CHISLZNAM, OK),
            SubjectScheduleNew(okno, "", "", WEDNESDAY, 2, CHISLZNAM, OK),
            SubjectScheduleNew(matl, "Косенко Д.А.", "4-02 А", WEDNESDAY, 3, CHISLZNAM, OK),
            SubjectScheduleNew(algl, "Пенкорь Е.В.", "4-02 А", WEDNESDAY, 4, CHISLZNAM, OK)
        )
        val day4 = listOf(
            SubjectScheduleNew(inya, "Татьянова Т.В.", "3-07 Б", THURSDAY, 1, CHISLZNAM, OK),
            SubjectScheduleNew(inya, "Татьянова Т.В.", "3-07 Б", THURSDAY, 2, CHISLZNAM, OK),
            SubjectScheduleNew(matp, "Пенкорь Е.В.", "1-01 В", THURSDAY, 3, CHISLZNAM, OK)
        )
        val day5 = listOf<SubjectScheduleNew>()
        val day6 = listOf(
            SubjectScheduleNew(matl, "Косенко Д.А.", "3-07 Б", SATURDAY, 2, CHISLZNAM, OK),
            SubjectScheduleNew(okno, "", "", SATURDAY, 3, CHISLZNAM, OK),
            SubjectScheduleNew(matp, "Пенкорь Е.В.", "3-07 Б", SATURDAY, 4, CHISLZNAM, OK)
        )
        return MutableLiveData<List<List<SubjectScheduleNew>>>().default(
            listOf(day1, day2, day3, day4, day5, day6)
        )
    }
}