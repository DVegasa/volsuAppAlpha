package io.github.dvegasa.volsuapplicationalpha.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.layout_menu_item.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class MenuViewBuilder(private val context: Context, private val container: ViewGroup) {

    fun setupDefaultMenuView() {
        val items = listOf(
            R.drawable.ic_rating_accent to "Рейтинг",
            R.drawable.ic_schedule_accent to "Расписание",
            R.drawable.ic_map_accent to "Навигация в вузе",
            R.drawable.ic_email_accent to "Почта",
            R.drawable.ic_notification_accent to "Уведомления"
        )

        val inflater = LayoutInflater.from(context)
        for (item in items) {
            val view = inflater.inflate(R.layout.layout_menu_item, container, false)
            view.ivIcon.setImageResource(item.first)
            view.tvTitle.text = item.second
            container.addView(view)
        }
    }
}