package io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui

import android.annotation.SuppressLint
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
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.ScheduleSubject
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.TimeStatus
import io.github.dvegasa.volsuapplicationalpha.utils.color
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex
import kotlinx.android.synthetic.main.layout_starttime_line.*
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_day_fragment.*
import kotlin.collections.ArrayList

const val DAYWEEK_KEY = "dayweek_key"

@SuppressLint("SetTextI18n")
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

    private lateinit var scheduleDay: LiveData<ScheduleDay>


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
        scheduleDay = (vm.scheduleByDayweek[dayweek]).apply {
            this!!.observe(viewLifecycleOwner, Observer {
                if (it != null) updateUI()
            })
        }!!

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
        scheduleDay.value?.let {
            val isZnam = vm.isZnamPicked.value == true
            val toShow = (if (isZnam) it.znam ?: it.chis else it.chis)
            val nonTrivialStartTime =
                if (isZnam) it.nonTrivialStartTimeZnam
                else it.nonTrivialStartTimeChis

            defineChisZnamSwitcherVisibility()
            llSubjectLines.removeAllViews()

            if (nonTrivialStartTime != null) {
                llStartTime.visibility = View.VISIBLE
                tvTime.text = "Начало пар в $nonTrivialStartTime"
            } else {
                llStartTime.visibility = View.GONE
            }

            if (toShow.isEmpty()) {
                llSubjectLines.addView(LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false).apply {
                        tvTitle.text = "В этот день пар нет"
                    })
            } else {
                addSubjects(toShow)
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


    private fun addSubjects(toShow: ArrayList<ScheduleSubject>) {
        for (s in toShow) {
            val v = LayoutInflater.from(context)
                .inflate(R.layout.layout_subject_line, llSubjectLines, false)

            if (s.isOkno()) {
                v.tvTitle.text = "Окно"
                v.tvSubtitle.text =
                    "${ScheduleTimetable.subjStart[s.slot]} — ${ScheduleTimetable.subjEnd[s.slot]}"
                v.tvAudi.text = ""
            } else {
                v.tvAudi.text = s.audi
                v.tvTitle.text = s.title
                v.tvSubtitle.text = s.teacher
            }

//            displayTimeStatus(s, v)
            llSubjectLines.addView(v)
        }
    }

//    private fun displayTimeStatus(s: ScheduleSubject, v: View) {
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
//                TimeStatus.FUTURE -> {
//                    val c = context.color(android.R.color.black)
//                    tvTitle.setTextColor(c)
//                    tvSubtitle.setTextColor(c)
//                    tvAudi.setTextColor(c)
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
