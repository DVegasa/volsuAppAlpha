package io.github.dvegasa.volsuapplicationalpha.ui.menu

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.dvegasa.volsuapplicationalpha.ActivityViewModel

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
        val menuBuilder = MenuViewBuilder(context!!, llMenuRoot).setupDefaultMenuView()
    }

}
