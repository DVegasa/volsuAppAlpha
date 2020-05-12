package io.github.dvegasa.volsuapplicationalpha.feature.notification.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.feature.notification.NotificationViewModel
import kotlinx.android.synthetic.main.notification_fragment.*
import kotlinx.android.synthetic.main.notification_toolbar.*

class NotificationFragment : Fragment() {
    companion object {
        fun newInstance() =
            NotificationFragment()
    }

    private lateinit var vm: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.notification_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(NotificationViewModel::class.java)
        initToolbar()
        initRvNotifications()
    }

    private fun initToolbar() {
        toolbarNotification.title = "Уведомления"
        toolbarNotification.inflateMenu(R.menu.notification_toolbar_menu)
    }

    private fun initRvNotifications() {
        rvNotifications.layoutManager = LinearLayoutManager(context)
        val adapter = RvNotificationsAdapter(viewLifecycleOwner, vm.shortNotifications)
        rvNotifications.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notification_toolbar_menu, menu)
    }
}
