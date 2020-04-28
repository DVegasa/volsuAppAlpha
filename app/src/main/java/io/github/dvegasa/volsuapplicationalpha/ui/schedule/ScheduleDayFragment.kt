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
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.utils.default
import io.github.dvegasa.volsuapplicationalpha.utils.firstNonOknoIndex
import io.github.dvegasa.volsuapplicationalpha.utils.lastNonOknoIndex
import kotlinx.android.synthetic.main.layout_starttime_line.*
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_day_fragment.*

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

    private fun updateUI() {
        val subjes = vm.weekSchedule.value!!.schedule(dayweek)
        if (subjes.isChisZnamIdentical) {
            llSwitcher.visibility = View.GONE
        } else {
            llSwitcher.visibility = View.VISIBLE
        }

        val l = if (isShownZnam.value!!) subjes.znam else subjes.chis
        val firstSlot = l.firstNonOknoIndex()
        val lastSlot = l.lastNonOknoIndex()

        var toShow = ArrayList(l.toList())
        if (firstSlot < 0) {
            toShow.clear()
        } else {
            toShow = ArrayList(toShow.subList(firstSlot, lastSlot+1))
        }

        if (firstSlot >= 1) {
            llStartTime.visibility = View.VISIBLE
            // TODO Указать правильное время
            tvTime.text = "Начало пар в 20:00"
        } else {
            llStartTime.visibility = View.GONE
        }

        llSubjectLines.removeAllViews()
        if (firstSlot < 0) {
            val v =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false)
            v.tvTitle.text = "В этот день пар нет"

            llSubjectLines.addView(v)
        }

        for (s in toShow) {
            val v =
                LayoutInflater.from(context)
                    .inflate(R.layout.layout_subject_line, llSubjectLines, false)
            v.tvAudi.text = s.audi
            v.tvTitle.text = s.title
            v.tvSubtitle.text = s.teacher

            llSubjectLines.addView(v)
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
