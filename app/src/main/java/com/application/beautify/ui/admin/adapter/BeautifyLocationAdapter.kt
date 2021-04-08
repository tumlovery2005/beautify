package com.application.beautify.ui.admin.adapter

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.beautify.R
import com.application.beautify.model.Beautify
import kotlinx.android.synthetic.main.item_beautify.view.*
import kotlinx.android.synthetic.main.item_beautify_location.view.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class BeautifyLocationAdapter(var list: ArrayList<Beautify>): RecyclerView.Adapter<BeautifyLocationHolder>() {

    private lateinit var mParent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeautifyLocationHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent

        val view = inflater.inflate(R.layout.item_beautify_location, parent, false)
        return BeautifyLocationHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BeautifyLocationHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addBeautify(beautify: Beautify) {
        list.add(beautify)
        notifyDataSetChanged()
    }
}

class BeautifyLocationHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(beautify: Beautify) {
        itemView.textViewLacationName.text = beautify.name
        itemView.textViewBeautifyLat.text = beautify.latitude
        itemView.textViewBeautifyLng.text = beautify.longitude
    }
}
