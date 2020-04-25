package io.github.dvegasa.volsuapplicationalpha.ui.rating

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.ui.schedule.ScheduleViewModel
import kotlinx.android.synthetic.main.rating_fragment.*
import kotlinx.android.synthetic.main.rating_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.*
import kotlinx.android.synthetic.main.schedule_toolbar.view.*

class RatingFragment : Fragment() {

    companion object {
        fun newInstance() = RatingFragment()
    }

    private lateinit var vm: RatingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rating_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vm = ViewModelProvider(activity!!).get(RatingViewModel::class.java)
        initToolbar()
        initContent()
        initTopPanel()
    }

    private fun initToolbar() {
        toolbarRating.title = ""
        toolbarRating.tvTitle.text = "2 семестр"
    }

    private fun initContent() {
        val adapter = NewAdapter(viewLifecycleOwner, vm.subjectRiches)
        rvSubjects.adapter = adapter
        val ll = LinearLayoutManager(context)
        rvSubjects.layoutManager = ll
        rvSubjects.addItemDecoration(
            DividerItemDecoration(rvSubjects.context, ll.orientation)
        )

        swipeRvSubjects.setOnRefreshListener {
            swipeRvSubjects.isRefreshing = false
        }
    }

    private fun initTopPanel() {
        vm.userRating.observe(viewLifecycleOwner, Observer {
            tvRating.text = it.toString()
        })

        vm.userSum.observe(viewLifecycleOwner, Observer {
            tvScore.text = it.toString()
        })
    }

}
