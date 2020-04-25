package io.github.dvegasa.volsuapplicationalpha.ui.rating

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.ui.schedule.ScheduleViewModel
import kotlinx.android.synthetic.main.rating_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*

class RatingFragment : Fragment() {

    companion object {
        fun newInstance() = RatingFragment()
    }

    private lateinit var viewModel: RatingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rating_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(RatingViewModel::class.java)
        initToolbar()
    }

    private fun initToolbar() {
        toolbarRating.title = ""
        toolbarRating.tvTitle.text = "2 семестр"
    }

}
