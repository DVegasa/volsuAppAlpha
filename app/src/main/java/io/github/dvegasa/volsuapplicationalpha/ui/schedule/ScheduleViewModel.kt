package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dvegasa.volsuapplicationalpha.default

class ScheduleViewModel : ViewModel() {
    val mainContent = MutableLiveData<List<Subject>>()
    val curDayweek = MutableLiveData<Int>().default(1).apply {
        // Произошла смена выбранного dayweek. Нужно отобразить соответствущее расписание
        updateMainContent(value!!)
    }


    private fun updateMainContent(selectedDayWeek: Int) {
        // Тестовые данные:
        val subj1 = Subject("3-01 A", "Алгебра и теория чисел", "Косенко Д.А.", SubjectStyle.INACTIVE)
        val subj2 = Subject(
            "4-01 B",
            "Физическая культура и спорт",
            "Начнётся через 3 минуты",
            SubjectStyle.ACCENT
        )
        val subj3 = Subject("", "Окно", "10:30 — 12:00", SubjectStyle.NORMAL)
        val subj4 = Subject(
            "1-11 М",
            "Информатика и программирование",
            "Пара отменена",
            SubjectStyle.CANCELLED
        )

        mainContent.value = listOf(subj1, subj2, subj3, subj4)
    }
}
