package io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.dvegasa.volsuapplicationalpha.application.MyApplication
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.TimetableResponse
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleWeek
import io.github.dvegasa.volsuapplicationalpha.network.DataRequest
import io.github.dvegasa.volsuapplicationalpha.network.RequestStatus
import io.github.dvegasa.volsuapplicationalpha.network.VolsuNikitaApi
import retrofit2.Call

/**
 * Created by Ed Khalturin @DVegasa
 */
class ScheduleRepo {

    fun isThisWeekZnam(): Boolean = false

    fun getScheduleWeek(requestStatus: MutableLiveData<RequestStatus>): LiveData<ScheduleWeek> {
        return object : DataRequest<List<TimetableResponse>, ScheduleWeek>(requestStatus) {
            override fun shouldFetch() = true

            override fun getCachedData(): LiveData<ScheduleWeek> {
                return MyApplication.instance.appDatabase.cacheDao().getScheduleWeek()
            }

            override fun getRetrofitCall(): Call<List<TimetableResponse>> {
                return VolsuNikitaApi.create().getFakeTimetable()
            }

            override fun cacheResult(data: ScheduleWeek) {
                MyApplication.instance.appDatabase.cacheDao().updateScheduleWeek(data)
            }

            override fun requestToResult(request: List<TimetableResponse>): ScheduleWeek {
                return ResponseAdapter(request[0]).toScheduleWeek()
            }
        }.asLiveData()
    }
}