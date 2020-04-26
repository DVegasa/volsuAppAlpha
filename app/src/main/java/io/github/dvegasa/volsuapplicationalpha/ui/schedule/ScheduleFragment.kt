package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.layout_subject_line.view.*
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var vm: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.schedule_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(ScheduleViewModel::class.java)
        initToolbar()
        initDayweekButtons()
        initVpProperties()
        initVpContent()
    }

    private fun initToolbar() {
        toolbarSchedule.title = ""
        toolbarSchedule.inflateMenu(R.menu.shedule_toolbar_menu)
        initSpintb()
        vm.chosenTitle.observe(viewLifecycleOwner, Observer {
            if (it != spintb.selectedItemPosition) {
                spintb.setSelection(it)
            }
        })
    }

    private fun initSpintb() {
        val titles = arrayOf(
            "Учебные пары",
            "Доп. занятия",
            "Кафедры"
        )
        val adapter = ArrayAdapter<CharSequence>(
            toolbarSchedule.context,
            R.layout.spintb_item,
            titles
        )
        adapter.setDropDownViewResource(R.layout.spintb_dropdown_item)
        toolbarSchedule.spintb.adapter = adapter
        llSpintbArea.setOnClickListener {
            spintb.performClick()
        }
        spintb.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                vm.chosenTitle.value = pos
                tvToolbarTitle.text = titles[pos]
                spintb.forceLayout()
                ivArrowDown.forceLayout()
                llSpintbArea.requestLayout()
            }
        }
    }

    private fun initDayweekButtons() {
        vm.curDayweek.observe(viewLifecycleOwner, Observer { value ->
            updateCurDayweekUi(value)
            vpContent.currentItem = value - 1
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

    private fun initVpProperties() {
        val adapter = VpContentPagerAdapter(activity!!)
        vpContent.adapter = adapter
        vpContent.offscreenPageLimit = 6

        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                vm.curDayweek.value = position + 1
            }
        })
    }

    private fun initVpContent() {
        val lls = listOf(llContent1, llContent2, llContent3, llContent4, llContent5, llContent6)
        vm.weekSchedule.observe(viewLifecycleOwner, Observer{ data ->
            SubjectLineInflater(context!!).publish(lls, data)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shedule_toolbar_menu, menu)
    }

}
