package com.application.beautify.ui.admin.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.beautify.R
import com.application.beautify.model.Beautify
import kotlinx.android.synthetic.main.item_beautify.view.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class BeautifyAdapter(var list: ArrayList<Beautify>): RecyclerView.Adapter<BeautifyHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeautifyHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent

        val view = inflater.inflate(R.layout.item_beautify, parent, false)
        return BeautifyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BeautifyHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addBeautify(beautify: Beautify) {
        Log.d("Beautifyadapter", "Beautify -> ${beautify.name}")
        list.add(beautify)
        notifyDataSetChanged()
    }
}

class BeautifyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(beautify: Beautify) {
        if (beautify.images.size > 0) {
            val byteArray = Base64.decode(beautify.images[0], Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            itemView.imageViewItemBeautify.setImageBitmap(bitmap)
        } else {
            itemView.imageViewItemBeautify.setImageResource(R.drawable.ic_online_store)
        }
        itemView.textViewItemBeautify.text = beautify.name
    }
}
