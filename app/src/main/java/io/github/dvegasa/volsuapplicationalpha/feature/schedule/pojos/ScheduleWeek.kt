package io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import io.github.dvegasa.volsuapplicationalpha.db.DataTypeConverters
import io.github.dvegasa.volsuapplicationalpha.db.GLOBAL_CACHE_VALUE_ID

/**
 * Created by Ed Khalturin @DVegasa
 */

const val SCHEDULE_WEEK_TABLE_NAME = "schedule_week_cache"

@Entity(tableName = SCHEDULE_WEEK_TABLE_NAME)
data class ScheduleWeek(
    @TypeConverters(DataTypeConverters::class) val monday: ScheduleDay,
    @TypeConverters(DataTypeConverters::class) val tuesday: ScheduleDay,
    @TypeConverters(DataTypeConverters::class) val wednesday: ScheduleDay,
    @TypeConverters(DataTypeConverters::class) val thursday: ScheduleDay,
    @TypeConverters(DataTypeConverters::class) val friday: ScheduleDay,
    @TypeConverters(DataTypeConverters::class) val saturday: ScheduleDay
) {
    @PrimaryKey
    var id: Long = GLOBAL_CACHE_VALUE_ID

    fun schedule(dayweek: Dayweek) = when (dayweek) {
        Dayweek.MONDAY -> monday
        Dayweek.TUESDAY -> tuesday
        Dayweek.WEDNESDAY -> wednesday
        Dayweek.THURSDAY -> thursday
        Dayweek.FRIDAY -> friday
        Dayweek.SATURDAY -> saturday
        Dayweek.SUNDAY -> throw Exception("Attempt to get schedule for Dayweek.SUNDAY")
    }
}