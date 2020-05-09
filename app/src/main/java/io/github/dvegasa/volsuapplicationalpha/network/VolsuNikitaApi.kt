package io.github.dvegasa.volsuapplicationalpha.network

import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.TimetableResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by Ed Khalturin @DVegasa
 */
interface VolsuNikitaApi {
    companion object {
        fun create(): VolsuNikitaApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://62.109.28.67:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(VolsuNikitaApi::class.java)
        }
    }

    @GET("/timetable/getTimetable/?id=3")
    fun getFakeTimetable(): Call<List<TimetableResponse>>
}