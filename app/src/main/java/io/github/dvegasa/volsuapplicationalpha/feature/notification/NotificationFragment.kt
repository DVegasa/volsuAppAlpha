package io.github.dvegasa.volsuapplicationalpha.feature.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import io.github.dvegasa.volsuapplicationalpha.R
import kotlinx.android.synthetic.main.notification_fragment.*

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
        tvNum.text = vm.randomNumber.value.toString()
    }

}
