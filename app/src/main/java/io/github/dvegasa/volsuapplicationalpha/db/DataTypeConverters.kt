package io.github.dvegasa.volsuapplicationalpha.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleDay


/**
 * Created by Ed Khalturin @DVegasa
 */
class DataTypeConverters {
    companion object {

        private val gson = Gson()

        @TypeConverter
        @JvmStatic
        fun scheduleDayToJson(scheduleDay: ScheduleDay): String {
            return gson.toJson(scheduleDay)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToScheduleDay(json: String): ScheduleDay {
            return gson.fromJson(json, ScheduleDay::class.java)
        }

//        @TypeConverter
//        fun listToString(list: ArrayList<SubjectSchedule>): String {
//            return gson.toJson(list)
//        }
//
//        @TypeConverter
//        fun stringToSubjectScheduleList(json: String): ArrayList<SubjectSchedule> {
//            if (json.isEmpty()) return arrayListOf()
//            val listType: Type =
//                object : TypeToken<ArrayList<SubjectSchedule>>() {}.type
//            return gson.fromJson(json, listType)
//        }
    }
}