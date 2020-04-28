package io.github.dvegasa.volsuapplicationalpha.ui.schedule

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.layout_schedule_chis_znam_switcher.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class ChisZnamPickerView(val context: Context, val lifecycleOwner: LifecycleOwner) {
    /**
     * 0 -- chis
     * 1 -- znam
     */

    fun getView(container: ViewGroup, status: MutableLiveData<Int>): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_schedule_chis_znam_switcher, container, false)

        status.observe(lifecycleOwner, Observer {
            if (it == 0) activateChis(view)
            else activateZnam(view)
        })

        view.tvChis.setOnClickListener { status.value = 0 }
        view.tvZnam.setOnClickListener { status.value = 1 }

        return view
    }

    private fun activateChis(view: View) {
        view.tvChis.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.colorAccent,
                    null
                )
            )
        }
        view.tvZnam.apply {
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

    private fun activateZnam(view: View) {
        view.tvZnam.apply {
            setBackgroundResource(R.drawable.bg_chisznam_picker)
            setTextColor(
                ResourcesCompat.getColor(
                    context.resources,
                    R.color.colorAccent,
                    null
                )
            )
        }
        view.tvChis.apply {
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