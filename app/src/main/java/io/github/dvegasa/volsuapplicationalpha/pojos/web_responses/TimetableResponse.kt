package io.github.dvegasa.volsuapplicationalpha.pojos.web_responses


import com.google.gson.annotations.SerializedName
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleWeek
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule

data class TimetableResponse(
    @SerializedName("direction")
    val direction: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("Timetable")
    val timetable: List<Timetable>
) {
    data class Timetable(
        @SerializedName("Cells")
        val cells: List<Cell>,
        @SerializedName("id")
        val id: Int,
        @SerializedName("year")
        val year: List<Year>
    ) {
        data class Cell(
            @SerializedName("id")
            val id: Int,
            @SerializedName("lesson")
            val lesson: List<Lesson>,
            @SerializedName("time")
            val time: List<Time>
        ) {
            data class Lesson(
                @SerializedName("discipline")
                val discipline: List<Discipline>,
                @SerializedName("id")
                val id: Int,
                @SerializedName("room")
                val room: List<Room>,
                @SerializedName("teacher")
                val teacher: List<Teacher>
            ) {
                data class Discipline(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("name")
                    val name: String
                )

                data class Room(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("name")
                    val name: String
                )

                data class Teacher(
                    @SerializedName("id")
                    val id: Int,
                    @SerializedName("name")
                    val name: String
                )
            }

            data class Time(
                @SerializedName("day")
                val day: String,
                @SerializedName("id")
                val id: Int,
                @SerializedName("pair")
                val pair: String
            )
        }

        data class Year(
            @SerializedName("id")
            val id: Int,
            @SerializedName("name")
            val name: String
        )
    }

    /* Fake */
    companion object {
        fun getFake(): ScheduleWeek {
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
            return ScheduleWeek(monday, tuesday, wednesday, thursday, friday, saturday)
        }
    }
}