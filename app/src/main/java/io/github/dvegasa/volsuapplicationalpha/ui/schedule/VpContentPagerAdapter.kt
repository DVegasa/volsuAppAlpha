package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import io.github.dvegasa.volsuapplicationalpha.R


/**
 * Created by Ed Khalturin @DVegasa
 */
class VpContentPagerAdapter(val activity: Activity) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var resId = 0
        when (position) {
            0 -> resId = R.id.llContent1
            1 -> resId = R.id.llContent2
            2 -> resId = R.id.llContent3
            3 -> resId = R.id.llContent4
            4 -> resId = R.id.llContent5
            5 -> resId = R.id.llContent6
        }
        return activity.findViewById(resId)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = 6
}