package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.Time
import io.github.dvegasa.volsuapplicationalpha.dataprocessing.TimeCalculator
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleDay
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.TimeStatus
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleTimetable
import io.github.dvegasa.volsuapplicationalpha.utils.color
import io.github.dvegasa.volsuapplicationalpha.utils.default
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
            val frag = ScheduleDayFragment()
            val bundle = Bundle()
            bundle.putParcelable(DAYWEEK_KEY, dayweek)
            frag.arguments = bundle
            return frag
        }
    }

    private lateinit var dayweek: Dayweek
    private lateinit var vm: ScheduleViewModel
    private val isShownZnam = MutableLiveData<Boolean>().default(
        // TODO: Показывать по умолчанию то, какая неделя по факту (сейчас числ или сейчас знам)
        false
    )
    private val scheduleDay: ScheduleDay
        get() = vm.weekSchedule.value!!.schedule(dayweek)

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

        flChis.setOnClickListener { isShownZnam.value = false }
        flZnam.setOnClickListener { isShownZnam.value = true }

        isShownZnam.observeForever {
            if (it == false) {// Числитель
                activateChisUISwitcher()
            } else { // Знаменатель
                activateZnamUISwitcher()
            }
            updateUI()
        }

        vm.weekSchedule.observe(viewLifecycleOwner, Observer {
            updateUI()
        })
    }

    private fun defineChisZnamSwitcherVisibility() {
        val subjes = vm.weekSchedule.value!!.schedule(dayweek)
        if (subjes.isChisZnamIdentical) {
            llSwitcher.visibility = View.GONE
        } else {
            llSwitcher.visibility = View.VISIBLE
        }
    }

    private fun updateUI() {
        defineChisZnamSwitcherVisibility()
        val toShow = (if (isShownZnam.value!!) scheduleDay.znam else scheduleDay.chis).apply {
            TimeCalculator.defineTimeStatuses(this, dayweek)
        }

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
            tvTime.text = "Начало пар в ${ScheduleTimetable.subjStart[toShow.firstNonOknoIndex()]}"
        } else {
            llStartTime.visibility = View.GONE
        }
    }

    private fun addSubjects(toShow: java.util.ArrayList<SubjectSchedule>) {
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

            displayTimeStatus(s, v)
            llSubjectLines.addView(v)
        }
    }

    private fun displayTimeStatus(s: SubjectSchedule, v: View) {
        v.flOngoing.visibility = View.INVISIBLE
        with(v) {
            when (s.timeStatus) {
                TimeStatus.PAST -> {
                    val c = context.color(R.color.colorSubjSkipped)
                    tvTitle.setTextColor(c)
                    tvSubtitle.setTextColor(c)
                    tvAudi.setTextColor(c)
                }
                TimeStatus.ONGOING -> {
                    flOngoing.visibility = View.VISIBLE
                }
                TimeStatus.COMING -> {
                    tvSubtitle.setTextColor(context.color(R.color.colorAccent))
                    tvSubtitle.text = s.timeStatusMsg
                }
            }
        }
    }


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
