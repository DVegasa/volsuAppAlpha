package io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui.ScheduleDayFragment


/**
 * Created by Ed Khalturin @DVegasa
 */
class VpContentPagerAdapter(fm: FragmentManager, private val fragments: Array<ScheduleDayFragment>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}