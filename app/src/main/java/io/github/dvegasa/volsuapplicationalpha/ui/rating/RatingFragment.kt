package io.github.dvegasa.volsuapplicationalpha.ui.rating

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.ui.schedule.ScheduleViewModel
import kotlinx.android.synthetic.main.rating_fragment.*
import kotlinx.android.synthetic.main.rating_toolbar.*
import kotlinx.android.synthetic.main.rating_toolbar.view.*

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
        initSpintb()
        vm.chosenSemestr.observe(viewLifecycleOwner, Observer {
            if (it != spintb.selectedItemPosition) {
                spintb.setSelection(it - 1)
            }
        })
    }

    private fun initSpintb() {
        val semesters = arrayOf(
            "1 семестр",
            "2 семестр",
            "3 семестр",
            "4 семестр",
            "5 семестр",
            "6 семестр",
            "7 семестр",
            "8 семестр"
        )
        val adapter = ArrayAdapter<CharSequence>(
            toolbarRating.context,
            R.layout.spintb_item, // Выбранный элемент (заменяет toolbar.title)
            semesters
        )
        adapter.setDropDownViewResource(R.layout.spintb_dropdown_item)
        toolbarRating.spintb.adapter = adapter
        llSpintbArea.setOnClickListener {
            spintb.performClick()
        }
        spintb.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                val semestr = pos + 1
                vm.chosenSemestr.value = semestr
            }
        }
    }

    private fun initContent() {
        // vm.subjectRiches наблюдается из адаптера
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
