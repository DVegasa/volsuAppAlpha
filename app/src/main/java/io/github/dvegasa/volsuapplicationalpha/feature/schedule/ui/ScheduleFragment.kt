package io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.pojos.Dayweek
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.ScheduleViewModel
import io.github.dvegasa.volsuapplicationalpha.utils.color
import kotlinx.android.synthetic.main.schedule_fragment.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() =
            ScheduleFragment()
    }

    private lateinit var vm: ScheduleViewModel

    private val errorLoadingSnackbar by lazy {
        Snackbar.make(
            vpContent,
            "Не удалось подключиться",
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            view.setBackgroundColor(context.color(R.color.colorTextRed))
            setActionTextColor(context.color(android.R.color.white))
            val layoutParams = this.view.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.anchorId = R.id.bottomnav
            layoutParams.anchorGravity = Gravity.TOP
            layoutParams.gravity = Gravity.TOP
            this.view.layoutParams = layoutParams
            setAction("Повторить") { vm.requestUpdateScheduleWeek() }
            view.elevation = 2f
        }
    }


    private val dayFragments = arrayOf(
        ScheduleDayFragment.newInstance(Dayweek.MONDAY),
        ScheduleDayFragment.newInstance(Dayweek.TUESDAY),
        ScheduleDayFragment.newInstance(Dayweek.WEDNESDAY),
        ScheduleDayFragment.newInstance(Dayweek.THURSDAY),
        ScheduleDayFragment.newInstance(Dayweek.FRIDAY),
        ScheduleDayFragment.newInstance(Dayweek.SATURDAY)
    )

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
        initBottomTimer()
        handleErrors()
        handleLoading()
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
            R.layout.global_spintb_item,
            titles
        )
        adapter.setDropDownViewResource(R.layout.global_spintb_dropdown_item)
        toolbarSchedule.spintb.adapter = adapter
        llSpintbArea.setOnClickListener {
            spintb.performClick()
        }
        spintb.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                vm.chosenTitle.value = pos
                tvToolbarTitle.text = titles[pos]
            }
        }
    }

    private fun initDayweekButtons() {
        vm.pickedDayweekTab.observe(viewLifecycleOwner, Observer { value ->
            val v = if (value == Dayweek.SUNDAY.value) Dayweek.MONDAY.value else value

            updateCurDayweekUi(v)
            vpContent.currentItem = v - 1
        })

        val clickListener = View.OnClickListener { view ->
            vm.pickedDayweekTab.value = when (view.id) {
                flDayweek1.id -> Dayweek.MONDAY.value
                flDayweek2.id -> Dayweek.TUESDAY.value
                flDayweek3.id -> Dayweek.WEDNESDAY.value
                flDayweek4.id -> Dayweek.THURSDAY.value
                flDayweek5.id -> Dayweek.FRIDAY.value
                flDayweek6.id -> Dayweek.SATURDAY.value
                else -> Dayweek.SATURDAY.value
            }
        }

        flDayweek1.setOnClickListener(clickListener)
        flDayweek2.setOnClickListener(clickListener)
        flDayweek3.setOnClickListener(clickListener)
        flDayweek4.setOnClickListener(clickListener)
        flDayweek5.setOnClickListener(clickListener)
        flDayweek6.setOnClickListener(clickListener)
    }

    private fun initBottomTimer() {
        vm.bottomTimerText.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                tvTimerContent.text = ""
                tvTimerCaption.text = ""
            } else {
                tvTimerCaption.text = "До конца пары"
                tvTimerContent.text = it
            }
        })
    }

    private fun handleErrors() {
        vm.errorMessage.observe(viewLifecycleOwner, Observer {
            Log.d("ed__", "UI layer. errorMessage: $it")
            if (it != null) {
                errorLoadingSnackbar.show()
                errorLoadingSnackbar.view.visibility = View.VISIBLE
            } else {
                errorLoadingSnackbar.view.visibility = View.GONE
            }
        })
    }

    private fun handleLoading() {
        vm.isDataLoading.observe(viewLifecycleOwner, Observer {
            toolbarProgressBar.visibility = if (it == true) View.VISIBLE else View.GONE
        })
    }

    private fun updateCurDayweekUi(selected: Int) {
        val fls = listOf(flDayweek1, flDayweek2, flDayweek3, flDayweek4, flDayweek5, flDayweek6)
        val tvs = listOf(tvDayweek1, tvDayweek2, tvDayweek3, tvDayweek4, tvDayweek5, tvDayweek6)

        for (i in fls.indices) {
            fls[i].setBackgroundColor(0)
            tvs[i].setTextColor(Color.WHITE)
        }
        val bgRes =
            if (vm.scheduleByDayweek[Dayweek.byValue(selected)]?.value?.isChisZnamIdentical == false)
                R.drawable.bg_dayweek_selected_bottomsmooth
            else R.drawable.bg_dayweek_selected_bottomsharp

        fls[selected - 1].setBackgroundResource(bgRes)
        tvs[selected - 1].setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    }

    private fun initVpProperties() {
        val adapter = VpContentPagerAdapter(childFragmentManager, dayFragments)
        vpContent.adapter = adapter

        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                vm.pickedDayweekTab.value = position + 1
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shedule_toolbar_menu, menu)
    }

}
