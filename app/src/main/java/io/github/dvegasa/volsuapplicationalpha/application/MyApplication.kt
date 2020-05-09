package io.github.dvegasa.volsuapplicationalpha.application

import android.app.Application
import androidx.room.Room
import io.github.dvegasa.volsuapplicationalpha.db.AppDatabase

/**
 * Created by Ed Khalturin @DVegasa
 */
class MyApplication : Application() {
    val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "database").build()
    }

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}