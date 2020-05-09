package io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.data_processing.ScheduleTimetable
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.ScheduleViewModel
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.utils.color
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex
import kotlinx.android.synthetic.main.layout_starttime_line.*
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_day_fragment.*
import kotlin.collections.ArrayList

const val DAYWEEK_KEY = "dayweek_key"

class ScheduleDayFragment : Fragment() {

    companion object {
        fun newInstance(dayweek: Dayweek): ScheduleDayFragment {
            val frag =
                ScheduleDayFragment()
            val bundle = Bundle()
            bundle.putParcelable(DAYWEEK_KEY, dayweek)
            frag.arguments = bundle
            return frag
        }
    }

    private lateinit var dayweek: Dayweek
    private lateinit var vm: ScheduleViewModel

    private lateinit var scheduleDay: LiveData<ScheduleDay?>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dayweek = arguments?.getParcelable(DAYWEEK_KEY)!!
        return inflater.inflate(R.layout.schedule_day_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(ScheduleViewModel::class.java)
        scheduleDay = (vm.scheduleByDayweek[dayweek]
            ?: error("Ошибка получения расписания для дня $dayweek")).apply {
            this.observe(viewLifecycleOwner, Observer {
                updateUI()
            })
        }

        flChis.setOnClickListener { vm.isZnamPicked.value = false }
        flZnam.setOnClickListener { vm.isZnamPicked.value = true }

        vm.isZnamPicked.observe(viewLifecycleOwner, Observer {
            if (it == false) {// Числитель
                activateChisUISwitcher()
            } else { // Знаменатель
                activateZnamUISwitcher()
            }
            if (scheduleDay.value != null) updateUI()
        })
    }

    private fun updateUI() {
        if (scheduleDay.value != null) {
            defineChisZnamSwitcherVisibility()
            val toShow =
                (if (vm.isZnamPicked.value == true) scheduleDay.value!!.znam
                else scheduleDay.value!!.chis)

            llSubjectLines.removeAllViews()
            if (toShow.firstNonOknoIndex() < 0) {
                llSubjectLines.addView(LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false).apply {
                        tvTitle.text = "В этот день пар нет"
                    })
            } else {
                addSubjects(toShow)
            }

            if (toShow.firstNonOknoIndex() >= 1) {
                llStartTime.visibility = View.VISIBLE
                tvTime.text =
                    "Начало пар в ${ScheduleTimetable.subjStart[toShow.firstNonOknoIndex()]}"
            } else {
                llStartTime.visibility = View.GONE
            }
        }
    }

    private fun defineChisZnamSwitcherVisibility() {
        val subjes = scheduleDay.value!!
        if (subjes.isChisZnamIdentical) {
            llSwitcher.visibility = View.GONE
        } else {
            llSwitcher.visibility = View.VISIBLE
        }
    }

    private fun addSubjects(toShow: ArrayList<SubjectSchedule>) {
        for (i in toShow.firstNonOknoIndex()..toShow.lastNonOknoIndex()) {
            if (i < 0) continue
            val s = toShow[i]
            val v =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false)

            if (s.isOkno()) {
                v.tvTitle.text = "Окно"
                v.tvSubtitle.text =
                    "${ScheduleTimetable.subjStart[i]} — ${ScheduleTimetable.subjEnd[i]}"
                v.tvAudi.text = ""
            } else {
                v.tvAudi.text = s.audi
                v.tvTitle.text = s.title
                v.tvSubtitle.text = s.teacher
            }

            // displayTimeStatus(s, v)
            llSubjectLines.addView(v)
        }
    }

//    private fun displayTimeStatus(s: SubjectSchedule, v: View) {
//        v.flOngoing.visibility = View.INVISIBLE
//        with(v) {
//            when (s.timeStatus) {
//                TimeStatus.PAST -> {
//                    val c = context.color(R.color.colorSubjSkipped)
//                    tvTitle.setTextColor(c)
//                    tvSubtitle.setTextColor(c)
//                    tvAudi.setTextColor(c)
//                }
//                TimeStatus.ONGOING -> {
//                    flOngoing.visibility = View.VISIBLE
//                }
//                TimeStatus.COMING -> {
//                    tvSubtitle.setTextColor(context.color(R.color.colorAccent))
//                    tvSubtitle.text = s.timeStatusMsg
//                }
//            }
//        }
//    }


    private fun activateChisUISwitcher() {
        tvChis.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(context.color(R.color.colorAccent))
        }
        tvZnam.apply {
            setTextColor(context.color(android.R.color.white))
            setBackgroundColor(context.color(android.R.color.transparent))
        }
    }

    private fun activateZnamUISwitcher() {
        tvZnam.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(context.color(R.color.colorAccent))
        }
        tvChis.apply {
            setTextColor(context.color(android.R.color.white))
            setBackgroundColor(context.color(android.R.color.transparent))
        }
    }
}
