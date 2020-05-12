package io.github.dvegasa.volsuapplicationalpha.activities

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.feature.menu.MenuFragment
import io.github.dvegasa.volsuapplicationalpha.feature.notification.ui.NotificationFragment
import io.github.dvegasa.volsuapplicationalpha.feature.rating.RatingFragment
import io.github.dvegasa.volsuapplicationalpha.feature.schedule.ui.ScheduleFragment
import io.github.dvegasa.volsuapplicationalpha.utils.color
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    private lateinit var vm: ActivityViewModel

    private val fragmentLists = listOf(
        ScheduleFragment.newInstance(),
        RatingFragment.newInstance(),
        NotificationFragment.newInstance()
    )

    private val menuFragment = MenuFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        vm = ViewModelProvider(this).get(ActivityViewModel::class.java)
        vm.currentScreen.value = 0 // Начальный экран. 0 - расписание

        initNavBar()
        initVmObservers()
    }


    private fun initNavBar() {
        // Content
        val itemSchedule = AHBottomNavigationItem("Расписание",
            R.drawable.ic_schedule
        )
        val itemRating = AHBottomNavigationItem("Рейтинг",
            R.drawable.ic_rating
        )
        val itemNotification = AHBottomNavigationItem("Уведомления",
            R.drawable.ic_notification
        )
        val itemMenu = AHBottomNavigationItem("Меню",
            R.drawable.ic_menu
        )

        bottomnav.addItem(itemSchedule)
        bottomnav.addItem(itemRating)
        bottomnav.addItem(itemNotification)
        bottomnav.addItem(itemMenu)

        // Notification
        val notification = AHNotification.Builder()
            .setText("2")
            .setBackgroundColor(this.color(R.color.colorPrimary))
            .setTextColor(this.color(android.R.color.white))
            .build()
        bottomnav.setNotification(notification, 2);

        // Behavior
        bottomnav.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        // Styling
        bottomnav.accentColor = Color.parseColor("#2260AE")
        bottomnav.inactiveColor = Color.parseColor("#000000")
        bottomnav.setTitleTextSize(24f, 24f)

        // Callbacks
        bottomnav.setOnTabSelectedListener { position, _ ->
            if (position == 3) {
                vm.isMenuShown.value = true
                false
            } else {
                vm.currentScreen.value = position
                true
            }
        }
    }

    private fun initVmObservers() {
        vm.currentScreen.observe(this, Observer {
            showScreen(it)
        })

        vm.isMenuShown.observe(this, Observer {
            if (it == true) showMenu()
            else hideMenu()
        })
    }

    private fun showScreen(position: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_root, fragmentLists[position])
            .commitNow()
        bottomnav.setCurrentItem(position, false)
    }

    private fun showMenu() {
        supportFragmentManager.beginTransaction()
            .add(android.R.id.content, menuFragment)
            .commitNow()
    }

    private fun hideMenu() {
        supportFragmentManager.beginTransaction()
            .remove(menuFragment)
            .commitNow()
    }

}
