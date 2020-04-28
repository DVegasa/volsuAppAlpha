package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import io.github.dvegasa.volsuapplicationalpha.R


/**
 * Created by Ed Khalturin @DVegasa
 */
class VpContentPagerAdapter(fm: FragmentManager, private val fragments: Array<ScheduleDayFragment>) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}