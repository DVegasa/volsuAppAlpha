package io.github.dvegasa.volsuapplicationalpha.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.default
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek.*
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectPeriod.*
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectStatus.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class ScheduleRepo {

    /* Метод для демонстрации поведения приложения */
    fun getFakeWeekSchedule(): MutableLiveData<List<List<SubjectSchedule>>> {
        val algl = "Алгебра и теория чисел (л)"
        val algp = "Алгебра и теория чисел (пр)"
        val infl = "Информатика и программирование"
        val infp = "Информатика и программирование"
        val matl = "Математический анализ (л)"
        val matp = "Математический анализ (пр)"
        val inya = "Иностранный язык"
        val okno = "-"
        val day1 = listOf(
            SubjectSchedule(algl, "Косенко Д.А.", "3-01 А", MONDAY, 1, CHISLZNAM, OK),
            SubjectSchedule(algp, "Пенкорь Е.В.", "4-02 А", MONDAY, 2, CHISLZNAM, OK),
            SubjectSchedule(infl, "Романвский Е.Е.", "1-01 В", MONDAY, 3, CHISLZNAM, OK),
            SubjectSchedule(infl, "Романвский Е.Е.", "1-01 В", MONDAY, 4, CHISLZNAM, OK)
        )
        val day2 = listOf(
            SubjectSchedule(
                "Правоведение",
                "Юристиченко Д.О.",
                "4-07 А",
                TUESDAY,
                3,
                CHISLZNAM,
                OK
            ),
            SubjectSchedule(
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
            SubjectSchedule(infp, "Романвский Е.Е.", "3-01 А", WEDNESDAY, 1, CHISLZNAM, OK),
            SubjectSchedule(okno, "", "", WEDNESDAY, 2, CHISLZNAM, OK),
            SubjectSchedule(matl, "Косенко Д.А.", "4-02 А", WEDNESDAY, 3, CHISLZNAM, OK),
            SubjectSchedule(algl, "Пенкорь Е.В.", "4-02 А", WEDNESDAY, 4, CHISLZNAM, OK)
        )
        val day4 = listOf(
            SubjectSchedule(inya, "Татьянова Т.В.", "3-07 Б", THURSDAY, 1, CHISLZNAM, OK),
            SubjectSchedule(inya, "Татьянова Т.В.", "3-07 Б", THURSDAY, 2, CHISLZNAM, OK),
            SubjectSchedule(matp, "Пенкорь Е.В.", "1-01 В", THURSDAY, 3, CHISLZNAM, OK)
        )
        val day5 = listOf<SubjectSchedule>()
        val day6 = listOf(
            SubjectSchedule(matl, "Косенко Д.А.", "3-07 Б", SATURDAY, 2, CHISLZNAM, OK),
            SubjectSchedule(okno, "", "", SATURDAY, 3, CHISLZNAM, OK),
            SubjectSchedule(matp, "Пенкорь Е.В.", "3-07 Б", SATURDAY, 4, CHISLZNAM, OK)
        )
        return MutableLiveData<List<List<SubjectSchedule>>>().default(
            listOf(day1, day2, day3, day4, day5, day6)
        )
    }
}