package io.github.dvegasa.volsuapplicationalpha.feature.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.dvegasa.volsuapplicationalpha.activities.ActivityViewModel

import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.menu_fragment.*

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    private lateinit var vm: MenuViewModel
    private lateinit var activityVm: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.menu_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(MenuViewModel::class.java)
        activityVm = ViewModelProvider(activity!!).get(ActivityViewModel::class.java)

        initViews()
        initMenu()
        initVmObservers()
    }

    private fun initViews() {
        llTransparent.setOnClickListener {
            activityVm.isMenuShown.value = false
        }
    }

    private fun initVmObservers() {
        vm.username.observe(viewLifecycleOwner, Observer {
            tvUserName.text = it
        })

        vm.userSubtitle.observe(viewLifecycleOwner, Observer {
            tvUserSubtitle.text = it
        })
    }

    private fun initMenu() {
        val menubuilder = MenuBuilder(context!!)
        val menuList = menubuilder.getDefaultMenuViews(llMenuRoot)
        val bottomMenuList = menubuilder.setupBottomMenuView(llMenuBottom)
        val listener = View.OnClickListener { view ->
            when (view.id) {
                R.id.miSchedule -> activityVm.currentScreen.value = 0
                R.id.miRating -> activityVm.currentScreen.value = 1
                R.id.miNotifications -> activityVm.currentScreen.value = 2

                R.id.miNavigation -> {
                    // TODO: Тут открывать экран с навигацией
                }
                R.id.miEmail -> {
                    // TODO: Тут открывать экран с почтой
                }
                R.id.miHelp -> {
                }
                R.id.miSettings -> {
                }
                R.id.miLogout -> {
                }
            }
            activityVm.isMenuShown.value = false
        }

        for (view in menuList) {
            view.setOnClickListener(listener)
            llMenuRoot.addView(view)
        }
        for (view in bottomMenuList) {
            view.setOnClickListener(listener)
            llMenuBottom.addView(view)
        }
    }

}
