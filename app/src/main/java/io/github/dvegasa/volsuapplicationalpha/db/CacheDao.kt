package io.github.dvegasa.volsuapplicationalpha.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.SCHEDULE_WEEK_TABLE_NAME
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleWeek

/**
 * Created by Ed Khalturin @DVegasa
 */

const val GLOBAL_CACHE_VALUE_ID = 1L

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateScheduleWeek(scheduleWeek: ScheduleWeek)

    @Query("SELECT * FROM $SCHEDULE_WEEK_TABLE_NAME WHERE id = $GLOBAL_CACHE_VALUE_ID")
    fun getScheduleWeek(): LiveData<ScheduleWeek>
}