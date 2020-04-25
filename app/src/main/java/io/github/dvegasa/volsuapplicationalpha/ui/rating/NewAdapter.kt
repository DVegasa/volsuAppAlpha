package io.github.dvegasa.volsuapplicationalpha.ui.rating

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.pojos.SubjectRich
import kotlinx.android.synthetic.main.item_subject.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class NewAdapter(val lifecycleOwner: LifecycleOwner, val list: MutableLiveData<ArrayList<SubjectRich>>) :
    RecyclerView.Adapter<NewAdapter.VH>() {

    private val values = list.value!!

    private var visibleSubjects = ArrayList<SubjectRich>()
    private var hiddenSubjects = ArrayList<SubjectRich>()
    private var expandedIndicies = Array(values.size) { false }

    // ok
    init {
        list.observe(lifecycleOwner, Observer {
            val tempList = arrayListOf<SubjectRich>()
            val tempHidden = arrayListOf<SubjectRich>()
            for (i in values.indices) {
                Log.d("ed__", "userRate: ${values[i].userRate}")
                if (values[i].userRate == 0) {
                    tempHidden.add(values[i])
                } else {
                    tempList.add(values[i])
                }
            }
            this.visibleSubjects = ArrayList(tempList)
            this.hiddenSubjects = ArrayList(tempHidden)
            this.expandedIndicies = Array(values.size) { false }
            notifyDataSetChanged()
            Log.d("ed__", "NewAdapter.init. visible: ${this.visibleSubjects.size}, hidden: ${this.hiddenSubjects.size}")
        })
    }

    // ok
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return VH(v)
    }

    // ok
    override fun getItemCount() = visibleSubjects.size + if (hiddenSubjects.isEmpty()) 0 else 1

    // ok
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }

    inner class VH(val v: View) : RecyclerView.ViewHolder(v) {
        fun bind(pos: Int) {
            if (pos == visibleSubjects.size) {
                initHiddenTitle(v, pos)
            } else {
                initSubjectTitle(v, pos)
            }
        }

        private fun initHiddenTitle(v: View, pos: Int) {
            if (hiddenSubjects.isEmpty()) return

            v.apply {
                tvSubjectName.setText("Предметы без рейтинга: ${hiddenSubjects.size}")
                tvRating.setText("")
                tvSubjectName.setTextColor(
                    ResourcesCompat.getColor(
                        v.resources,
                        R.color.colorPrimaryDark,
                        null
                    )
                )

                setOnClickListener {
                    visibleSubjects.addAll(hiddenSubjects)
                    hiddenSubjects.clear()
                    notifyDataSetChanged()
                }
            }
        }

        private fun initSubjectTitle(v: View, pos: Int) {
            val subj = visibleSubjects[pos]

            v.apply {
                tvSubjectName.setText(subj.name)
                tvEmoji.setText(Statistics.getEmojiForSubject(subj))
                tvRating.setText(subj.userRate.toString())
                tvSubjectEkzamen.setText(subj.ekzamen)
                tvSubjectName.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        android.R.color.black,
                        null
                    )
                )
                initPlot(v, pos)
                initSubjStats(v, pos)
                setOnClickListener {
                    expandedIndicies[pos] = !expandedIndicies[pos]
                    notifyItemChanged(pos)
                }
                updatedExpandedState(v, pos)
            }
        }

        private fun initPlot(v: View, pos: Int) {
            val values = Statistics.getChartData(visibleSubjects[pos])
            v.apply {
                val weakColor = ColorTemplate.rgb("#E3E3E3")
                val data = BarData(BarDataSet(values, "").apply {
                    setColor(ColorTemplate.rgb("#1460F5"))
                    valueTextColor = ColorTemplate.COLOR_NONE
                    isHighlightEnabled = true
                })
                chart.data = data
                chart.description.isEnabled = false

                chart.setDrawGridBackground(false)

                chart.setTouchEnabled(false)
                chart.axisLeft.setDrawLabels(false)
                chart.axisRight.setDrawLabels(false)
                chart.axisLeft.setDrawGridLines(false)
                chart.axisRight.setDrawGridLines(false)
                chart.xAxis.position = XAxis.XAxisPosition.TOP
                chart.xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return "${(value * 5).toInt()}+"
                    }
                }
                chart.xAxis.gridColor = weakColor
                chart.xAxis.axisLineColor = weakColor
                chart.axisLeft.axisLineColor = weakColor
                chart.axisLeft.gridColor = weakColor
                chart.axisRight.axisLineColor = weakColor
                chart.axisRight.gridColor = weakColor

                chart.getAxis(YAxis.AxisDependency.LEFT).setDrawZeroLine(true)
                chart.getAxis(YAxis.AxisDependency.LEFT).zeroLineColor =
                    ColorTemplate.rgb("#FF000000")

                chart.highlightValue(
                    Statistics.getColumnByRate(visibleSubjects[pos].userRate).toFloat(),
                    0,
                    false
                )

                chart.legend.isEnabled = false
                invalidate()
            }
        }

        private fun initSubjStats(v: View, pos: Int) {
            v.tvSubjRating.setText("${Statistics.getSubjRating(visibleSubjects[pos])} из ${visibleSubjects[pos].rates.size}")
            v.tvMedian.setText(Statistics.getMedian(visibleSubjects[pos]).toString())
        }

        private fun updatedExpandedState(v: View, pos: Int) {
            val expanded = expandedIndicies[pos]
            if (expanded) {
                v.flExpandablePart.visibility = View.VISIBLE
                v.tvSubjectEkzamen.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.root.elevation = 32.0f
                }
            } else {
                v.flExpandablePart.visibility = View.GONE
                v.tvSubjectEkzamen.visibility = View.GONE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.root.elevation = 0.0f
                }
            }
        }
    }
}