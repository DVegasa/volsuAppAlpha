package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.icu.text.TimeZoneFormat
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
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectSchedule
import io.github.dvegasa.volsuapplicationalpha.pojos.TimeStatus
import io.github.dvegasa.volsuapplicationalpha.repos.ScheduleTimetable
import io.github.dvegasa.volsuapplicationalpha.utils.default
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex
import kotlinx.android.synthetic.main.layout_starttime_line.*
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_day_fragment.*
import java.util.*
import kotlin.collections.ArrayList

const val DAYWEEK_KEY = "dayweek_key"
const val UPCOMING_APPROACH_TIME = 10 * 60 // min * sec

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
    private val isShownZnam = MutableLiveData<Boolean>().default(false)

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
        val subjes = vm.weekSchedule.value!!.schedule(dayweek)
        val l = if (isShownZnam.value!!) subjes.znam else subjes.chis
        val firstSubjIndex = l.firstNonOknoIndex()
        val lastSubjIndex = l.lastNonOknoIndex()

        var toShow = ArrayList(l.toList())
        if (firstSubjIndex < 0) {
            toShow.clear()
        } else {
            toShow = ArrayList(toShow.subList(firstSubjIndex, lastSubjIndex + 1))
        }


        if (firstSubjIndex >= 1) {
            llStartTime.visibility = View.VISIBLE
            tvTime.text = "Начало пар в ${ScheduleTimetable.subjStart[firstSubjIndex]}"
        } else {
            llStartTime.visibility = View.GONE
        }

        llSubjectLines.removeAllViews()
        if (firstSubjIndex < 0) {
            val v =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false)
            v.tvTitle.text = "В этот день пар нет"

            llSubjectLines.addView(v)
        }

        val curSubjIndex = ScheduleTimetable.getSubjectIndexByTime(Time.current)
        // Плашки уроков
        for (i in toShow.indices) {
            val s = toShow[i]
            val v =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false)

            if (s.isOkno()) {
                v.tvTitle.text = "Окно"
                v.tvSubtitle.text =
                    "${ScheduleTimetable.subjStart[i]} — ${ScheduleTimetable.subjStart[i + 1]}"
                v.tvAudi.text = ""
            } else {
                v.tvAudi.text = s.audi
                v.tvTitle.text = s.title
                v.tvSubtitle.text = s.teacher
            }

            if (curSubjIndex == i && TimeCalculator.currentDayweek == dayweek) {
                v.flOngoing.visibility = View.VISIBLE
            } else {
                v.flOngoing.visibility = View.INVISIBLE
            }

            llSubjectLines.addView(v)

            // Обработка TimeStatus
            var timeStatus: TimeStatus = TimeStatus.FUTURE

            if (TimeCalculator.currentDayweek == dayweek) {
                if (Time.current.isBefore(ScheduleTimetable.subjStart[i])) timeStatus =
                    TimeStatus.FUTURE
                else if (Time.current.isBetween(
                        ScheduleTimetable.subjStart[i],
                        ScheduleTimetable.subjEnd[i]
                    )
                ) timeStatus = TimeStatus.ONGOING
                else if (Time.current.isAfter(ScheduleTimetable.subjEnd[i])) timeStatus =
                    TimeStatus.PAST
            }

            when (timeStatus) {
                TimeStatus.PAST -> {
                    val c =
                        ResourcesCompat.getColor(
                            context!!.resources,
                            R.color.colorSubjSkipped,
                            null
                        )
                    v.tvTitle.setTextColor(c)
                    v.tvSubtitle.setTextColor(c)
                    v.tvAudi.setTextColor(c)
                }
                else -> {
                }
            }
        }
    }


    private fun activateChisUISwitcher() {
        tvChis.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.colorAccent,
                    null
                )
            )
        }
        tvZnam.apply {
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    android.R.color.white,
                    null
                )
            )
            setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    android.R.color.transparent,
                    null
                )
            )
        }
    }

    private fun activateZnamUISwitcher() {
        tvZnam.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.colorAccent,
                    null
                )
            )
        }
        tvChis.apply {
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    android.R.color.white,
                    null
                )
            )
            setBackgroundColor(
                ResourcesCompat.getColor(
                    context.resources,
                    android.R.color.transparent,
                    null
                )
            )
        }
    }

}
