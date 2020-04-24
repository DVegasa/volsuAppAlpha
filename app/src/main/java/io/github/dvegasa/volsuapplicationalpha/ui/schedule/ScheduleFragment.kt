package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.schedule_fragment, container, false)
        return view
    }

    private fun initToolbar() {
        toolbarSchedule.title = ""
        toolbarSchedule.tvTitle.text = "Учебные пары"
        val listener = View.OnClickListener {

        }
        toolbarSchedule.tvTitle.setOnClickListener(listener)
        toolbarSchedule.ivArrowDown.setOnClickListener(listener)
        toolbarSchedule.inflateMenu(R.menu.shedule_toolbar_menu)
        // (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarSchedule)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleViewModel::class.java)
        initToolbar()
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shedule_toolbar_menu, menu)
    }

}
