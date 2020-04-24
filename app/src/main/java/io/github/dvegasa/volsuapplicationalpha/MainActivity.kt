package io.github.dvegasa.volsuapplicationalpha

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import io.github.dvegasa.volsuapplicationalpha.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_root, MainFragment.newInstance())
            .commitNow()

        initNovBar()
    }

    private fun initNovBar() {
        // Content
        val itemSchedule = AHBottomNavigationItem("Расписание", R.drawable.ic_schedule)
        val itemRating = AHBottomNavigationItem("Рейтинг", R.drawable.ic_rating)
        val itemNotification = AHBottomNavigationItem("Уведомления", R.drawable.ic_notification)
        val itemMenu = AHBottomNavigationItem("Меню", R.drawable.ic_menu)

        bottomnav.addItem(itemSchedule)
        bottomnav.addItem(itemRating)
        bottomnav.addItem(itemNotification)
        bottomnav.addItem(itemMenu)

        // Behavior
        bottomnav.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        // Styling
        bottomnav.accentColor = Color.parseColor("#2260AE")
        bottomnav.inactiveColor = Color.parseColor("#000000")
        bottomnav.setTitleTextSize(24f, 24f)


    }


}
