package io.github.dvegasa.volsuapplicationalpha.feature.notification.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import io.github.dvegasa.volsuapplicationalpha.R
import io.github.dvegasa.volsuapplicationalpha.feature.notification.pojos.ShortNotification
import io.github.dvegasa.volsuapplicationalpha.feature.notification.pojos.SmallCircle
import kotlinx.android.synthetic.main.notification_item_short.view.*

/**
 * Created by Ed Khalturin @DVegasa
 */
class RvNotificationsAdapter(
    lifecycleOwner: LifecycleOwner,
    val list: LiveData<List<ShortNotification>>
) :
    RecyclerView.Adapter<RvNotificationsAdapter.VH>() {

    init {
        list.observe(lifecycleOwner, Observer {
            this.notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_item_short, parent, false)
        return VH(v)
    }

    override fun getItemCount() = list.value?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }

    inner class VH(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(pos: Int) {
            view.apply {
                val shortNotif = list.value!![pos]

                tvAuthor.text = shortNotif.from
                tvShortNotif.text = shortNotif.briefContent
                flReadCircle.visibility = if (shortNotif.isMarked) View.VISIBLE else View.GONE
                tvTime.text = shortNotif.time

                ivAvatar.setImageResource(
                    when (shortNotif.imgId) {
                        0L -> R.drawable.ava_0
                        1L -> R.drawable.ava_1
                        2L -> R.drawable.ava_2
                        else -> R.drawable.ava_3
                    }
                )

                ivSmallCircle.setImageResource(
                    when (shortNotif.smallCircleId) {
                        SmallCircle.MONEY -> R.drawable.ic_small_circle_money
                        SmallCircle.REPLY -> R.drawable.ic_small_circle_reply
                        SmallCircle.NONE -> android.R.color.transparent
                    }
                )
            }
        }
    }
}