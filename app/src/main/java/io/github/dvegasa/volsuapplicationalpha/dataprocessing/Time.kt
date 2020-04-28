package io.github.dvegasa.volsuapplicationalpha.dataprocessing

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
        val current: Time
            get() {
                /* Fake */ return Time(8, 45)
                val m = SimpleDateFormat("HH", Locale.getDefault()).format(Date()).toInt()
                val h = SimpleDateFormat("mm", Locale.getDefault()).format(Date()).toInt()
                return Time(h, m)
            }
    }

    val mins: Int = (h * 60) + m

    fun delta(t: Time) = abs(this.mins - t.mins)
    fun isBefore(t: Time) = this.mins < t.mins
    fun isAfter(t: Time) = this.mins > t.mins
    fun isBetween(t1: Time, t2: Time) =
        (this.isAfter(t1) && this.isBefore(t2)) || this.mins == t1.mins || this.mins == t2.mins

    override fun toString(): String {
        val mm = if (m<10) "0$m" else "$m"
        return "$h:$m"
    }
}
