package io.github.dvegasa.volsuapplicationalpha.feature.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.layout_menu_item.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class MenuBuilder(private val context: Context) {

    fun getDefaultMenuViews(container: ViewGroup): List<View> {
        val items = listOf(
            R.drawable.ic_rating_accent to "Рейтинг",
            R.drawable.ic_schedule_accent to "Расписание",
            R.drawable.ic_map_accent to "Навигация в вузе",
            R.drawable.ic_email_accent to "Почта",
            R.drawable.ic_notification_accent to "Уведомления"
        )

        val inflater = LayoutInflater.from(context)
        val list = arrayListOf<View>()
        for (item in items) {
            val view = inflater.inflate(R.layout.layout_menu_item, container, false)
            view.ivIcon.setImageResource(item.first)
            view.tvTitle.text = item.second
            list.add(view)
        }
        list[0].id = R.id.miRating
        list[1].id = R.id.miSchedule
        list[2].id = R.id.miNavigation
        list[3].id = R.id.miEmail
        list[4].id = R.id.miNotifications
        return list
    }

    fun setupBottomMenuView(container: ViewGroup): List<View> {
        val items = listOf(
            R.drawable.ic_help_accent to "Помощь",
            R.drawable.ic_settings_accent to "Настройки",
            R.drawable.ic_logout_accent to "Сменить пользователя"
        )

        val inflater = LayoutInflater.from(context)
        val list = arrayListOf<View>()
        for (item in items) {
            val view = inflater.inflate(R.layout.layout_menu_item, container, false)
            view.ivIcon.setImageResource(item.first)
            view.tvTitle.text = item.second
            list.add(view)
        }
        list[0].id = R.id.miHelp
        list[1].id = R.id.miSettings
        list[2].id = R.id.miLogout
        return list
    }
}