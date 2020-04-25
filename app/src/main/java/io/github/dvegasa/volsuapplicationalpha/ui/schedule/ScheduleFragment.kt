package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.tvTitle
import kotlin.Exception

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var vm: ScheduleViewModel

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
        toolbarSchedule.inflateMenu(R.menu.shedule_toolbar_menu)
        // (activity as AppCompatActivity?)!!.setSupportActionBar(toolbarSchedule)

    }

    private fun initDayweekButtons() {
        vm.curDayweek.observe(viewLifecycleOwner, Observer { value ->
            updateCurDayweekUi(value)
        })

        val clickListener = View.OnClickListener { view ->
            vm.curDayweek.value = when (view.id) {
                flDayweek1.id -> 1
                flDayweek2.id -> 2
                flDayweek3.id -> 3
                flDayweek4.id -> 4
                flDayweek5.id -> 5
                flDayweek6.id -> 6
                else -> -1
            }
        }

        flDayweek1.setOnClickListener(clickListener)
        flDayweek2.setOnClickListener(clickListener)
        flDayweek3.setOnClickListener(clickListener)
        flDayweek4.setOnClickListener(clickListener)
        flDayweek5.setOnClickListener(clickListener)
        flDayweek6.setOnClickListener(clickListener)
    }

    private fun updateCurDayweekUi(selected: Int) {
        val fls = listOf(flDayweek1, flDayweek2, flDayweek3, flDayweek4, flDayweek5, flDayweek6)
        val tvs = listOf(tvDayweek1, tvDayweek2, tvDayweek3, tvDayweek4, tvDayweek5, tvDayweek6)

        for (i in fls.indices) {
            fls[i].setBackgroundColor(0)
            tvs[i].setTextColor(Color.WHITE)
        }
        fls[selected-1].setBackgroundResource(R.drawable.bg_dayweek_selected)
        tvs[selected-1].setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }

    private fun initLlContent() {
        vm.mainContent.observe(viewLifecycleOwner, Observer { list ->
            for (subj in list) {
                val view = LayoutInflater.from(context).inflate(R.layout.layout_subject_line, null)
                view.tvTitle.text = subj.title
                view.tvSubtitle.text = subj.subtitle
                view.tvAudi.text = subj.audi

                when (subj.style) {
                    SubjectStyle.ACCENT -> {
                        view.tvSubtitle.setTextColor(ContextCompat.getColor(context!!, R.color.colorTextAccent))
                    }

                    SubjectStyle.CANCELLED -> {
                        view.tvSubtitle.setTextColor(ContextCompat.getColor(context!!, R.color.colorTextRed))
                    }

                    SubjectStyle.INACTIVE -> {
                        view.tvTitle.setTextColor(ContextCompat.getColor(context!!, R.color.colorTextWeak))
                        view.tvAudi.setTextColor(ContextCompat.getColor(context!!, R.color.colorTextWeak))
                        view.tvSubtitle.setTextColor(ContextCompat.getColor(context!!, R.color.colorTextWeak))
                    }

                    SubjectStyle.NORMAL -> {/*ничего не требуется*/}

                }
                llContent.addView(view)
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(ScheduleViewModel::class.java)
        initToolbar()
        initDayweekButtons()
        initLlContent()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shedule_toolbar_menu, menu)
    }

}
