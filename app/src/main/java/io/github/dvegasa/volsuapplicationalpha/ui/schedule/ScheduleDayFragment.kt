package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.pojos.ScheduleDay

const val DAYWEEK_KEY = "dayweek_key"

class ScheduleDayFragment : Fragment() {

    companion object {
        fun newInstance(dayweek: Dayweek): ScheduleDayFragment{
            val frag = ScheduleDayFragment()
            val bundle = Bundle()
            bundle.putParcelable(DAYWEEK_KEY, dayweek)
            frag.arguments = bundle
            return frag
        }
    }

    private lateinit var dayweek: Dayweek
    private lateinit var vm: ScheduleViewModel

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
        vm.weekSchedule.observe(viewLifecycleOwner, Observer {
            updateUI()
        })
    }

    private fun updateUI() {
        val subjes = vm.weekSchedule.value!!.schedule(dayweek)
        // TODO Update UI here
    }

}
