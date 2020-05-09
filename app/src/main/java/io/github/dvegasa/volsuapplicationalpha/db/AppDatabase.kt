package io.github.dvegasa.volsuapplicationalpha.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleWeek

/**
 * Created by Ed Khalturin @DVegasa
 */

@Database(entities = [ScheduleWeek::class], version = 1)
@TypeConverters(DataTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cacheDao(): CacheDao
}