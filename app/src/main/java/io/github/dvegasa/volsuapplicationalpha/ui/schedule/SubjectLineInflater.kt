package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectTimeStatus
import io.github.dvegasa.volsuapplicationalpha.pojos.TimeStatus
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.repos.Timetable
import kotlinx.android.synthetic.main.layout_starttime_line.view.*
import kotlinx.android.synthetic.main.layout_subject_line.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class SubjectLineInflater(private val context: Context, private val vm: ScheduleViewModel) {

    /**
     * Принимает в себя параметр data, в котором внешний лист представляет собой день недели, а
     * внутренний лист предметы в этот день. Внешних листов должно быть ровно шесть
     * (с понедельника по субботу включительно). Во внутренних предметы должны быть отсортированы
     * по порядку их проведения (slot по возрастанию). В случае пропуска предметов по времени (окон)
     * вставляется объект с title = "-", что обрабатывается как отсутствие: "окно".
     * Окон в начале и в конце списка быть НЕ должно.
     */
    @SuppressLint("SetTextI18n")
    fun publish(viewgroups: List<ViewGroup>, data: List<List<SubjectSchedule>>) {

        for (day in data.indices) {
            val ll = viewgroups[day]
            ll.removeAllViews()

            // Показывает вверху плашку "Начало пар в HH:mm", если первый урок отсутсвует
            if (data[day].isNotEmpty() && data[day][0].slot != 1) {
                ll.addView(getStartTimeView(data[day][0].slot, ll))
            }

            for (i in data[day].indices) {
                val subj = data[day][i]
                val view =
                    LayoutInflater.from(context).inflate(R.layout.layout_subject_line, ll, false)

                fillContent(subj, view)
                val timeStatus = vm.subjStatuses.value!![i]
                showTimeStatus(subj, view, timeStatus)
                ll.addView(view)
            }
        }
    }

    private fun fillContent(subj: SubjectSchedule, view: View) {
        view.apply {
            if (subj.title == "-") {
                // Окно
                tvTitle.text = "Окно"
                tvSubtitle.text =
                    "${Timetable.subjStart[subj.slot]} — ${Timetable.subjStart[subj.slot + 1]}"
                tvAudi.text = ""

            } else {
                // Предмет
                tvTitle.text = subj.title
                tvSubtitle.text = subj.teacher
                tvAudi.text = subj.audi
            }
        }
    }

    private fun showTimeStatus(subj: SubjectSchedule, view: View, timeStatus: TimeStatus) {
        view.apply {
            if (subj.dayweek == TimeCalculator()
                    .getCurrentDayweek()) {
                flOngoing.visibility = View.INVISIBLE
                when (timeStatus.status) {
                    SubjectTimeStatus.SKIPPED -> {
                        val weakColor =
                            ResourcesCompat.getColor(resources, R.color.colorSubjSkipped, null)
                        tvTitle.setTextColor(weakColor)
                        tvSubtitle.setTextColor(weakColor)
                        tvAudi.setTextColor(weakColor)
                    }
                    SubjectTimeStatus.UPCOMING -> {
                        val accentColor =
                            ResourcesCompat.getColor(resources, R.color.colorAccent, null)
                        tvSubtitle.setTextColor(accentColor)
                        tvSubtitle.text = timeStatus.msg
                    }
                    SubjectTimeStatus.ONGOING -> {
                        flOngoing.visibility = View.VISIBLE
                    }
                    SubjectTimeStatus.FUTURE -> {
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getStartTimeView(slot: Int, container: ViewGroup): View? {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_starttime_line, container, false)
        view.tvTime.text = "Начало пар в ${Timetable.subjStart[slot]}"
        return view
    }

}