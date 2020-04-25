package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default

class ScheduleViewModel : ViewModel() {
    val mainContent = MutableLiveData<List<Subject>>().default(getTestData()[0])

    val weekSchedule = MutableLiveData<List<List<Subject>>>().default(
        getTestData()
    )

    val curDayweek = MutableLiveData<Int>().default(1).apply {
        // Произошла смена выбранного dayweek. Нужно отобразить соответствущее расписание
        observeForever {
            mainContent.value = weekSchedule.value!![value!! -1]
            Log.d("ed__", "curDayweek: $value")
        }
    }


    private fun getTestData(): List<List<Subject>> {
        val subj1 = Subject("3-01 A", "Алгебра и теория чисел", "Косенко Д.А.", SubjectStyle.INACTIVE)
        val subj2 = Subject("4-01 B", "Физическая культура и спорт", "Начнётся через 3 минуты", SubjectStyle.ACCENT)
        val subj3 = Subject("", "Окно", "10:30 — 12:00", SubjectStyle.NORMAL)
        val subj4 = Subject("1-11 М", "Информатика и программирование", "Пара отменена", SubjectStyle.CANCELLED)

        val list1 = listOf(subj1, subj2, subj4, subj4)
        val list2 = listOf(subj4, subj1, subj3, subj2)
        val list3 = listOf(subj2, subj2, subj3, subj4)
        val list4 = listOf(subj1, subj2, subj3)
        val list5 = listOf(subj1, subj4, subj2, subj4, subj4)
        val list6 = listOf(subj2, subj2, subj4, subj4)

        return listOf(list1, list2, list3, list4, list5, list6)
    }

}
