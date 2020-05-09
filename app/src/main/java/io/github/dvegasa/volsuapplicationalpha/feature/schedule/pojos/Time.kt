package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

/**
 * Created by Ed Khalturin @DVegasa
 */
data class Time(
    val h: Int,
    val m: Int
) {
    companion object {

        val hh = SimpleDateFormat("HH", Locale.getDefault())
        val mm = SimpleDateFormat("mm", Locale.getDefault())

        val current: Time
            get() {
                // /* Fake */ return Time(11, 44)

                val t = Calendar.getInstance().time

                val h = hh.format(t).toInt()
                val m = mm.format(t).toInt()
                return Time(
                    h,
                    m
                )
                /**
                 * TODO[!]: Потенциально опасное место переполнения памяти (создание нового объекта Time() при каждом обращении к Time.current)
                 * Вместо выдачи одного объекта с текущим временем, при каждом вызове данного метода
                 * происходит постоянное создание нового объекта. И хотя, при анонимных созданиях
                 * сборщик мусора Java справляется с их очисткой, стоит иметь ввиду что данный код
                 * может (но не обязательно) ухудшать производительность.
                 */
            }

        fun fromMins(mins: Int): Time {
            val h: Int = mins / 60
            val m = mins % 60
            return Time(
                h,
                m
            )
        }
    }

    val mins: Int = (h * 60) + m

    fun delta(t: Time) = abs(this.mins - t.mins)
    fun isBefore(t: Time) = this.mins < t.mins
    fun isAfter(t: Time) = this.mins > t.mins
    fun isBetween(t1: Time, t2: Time) =
        (this.isAfter(t1) && this.isBefore(t2)) || this.mins == t1.mins || this.mins == t2.mins

    override fun toString(): String {
        val mm = if (m < 10) "0$m" else "$m"
        return "$h:$mm"
    }
}
