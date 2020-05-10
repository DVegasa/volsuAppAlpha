package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos


import com.google.gson.annotations.SerializedName

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
}