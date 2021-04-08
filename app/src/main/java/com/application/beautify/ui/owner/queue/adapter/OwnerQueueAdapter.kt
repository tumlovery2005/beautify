package com.application.beautify.ui.owner.queue.adapter

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.beautify.R
import com.application.beautify.common.onClick
import com.application.beautify.model.Booking
import kotlinx.android.synthetic.main.item_queue.view.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class OwnerQueueAdapter(var list: ArrayList<Booking>): RecyclerView.Adapter<QueueHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent

        val view = inflater.inflate(R.layout.item_queue, parent, false)
        return QueueHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: QueueHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addQueue(booking: Booking) {
        list.add(booking)
        notifyDataSetChanged()
    }

    fun getBookingList(position: Int): Booking {
        return list[position]
    }
}

class QueueHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(booking: Booking) {
        itemView.textViewQueueName.text = booking.name
        itemView.textViewQueueDate.text = booking.date
        itemView.textViewQueueTime.text = booking.time
        itemView.btnQueueCallback.onClick {
            val tel = booking.tel
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$tel")
            itemView.context.startActivity(intent)
        }
    }
}
