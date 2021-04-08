package com.application.beautify.ui.user.fragment.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.model.Direction
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.GPSTracker
import com.application.beautify.common.StoredValue
import com.application.beautify.model.Beautify
import com.application.beautify.model.FullBeautify
import com.application.beautify.model.WorkingTime
import com.application.beautify.ui.user.DetailActivity
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.item_search_beautify.view.*
import java.util.*

/**
 * Created by GuGolf on 3/4/2019 AD.
 */
class UserSearchAdapter(var list: ArrayList<FullBeautify>): RecyclerView.Adapter<UserBeautifyHolder>(), Filterable {

    private lateinit var mParent: ViewGroup

    private var beautifyList: ArrayList<FullBeautify>? = null
    private var mBeautifyList: ArrayList<FullBeautify>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserBeautifyHolder {
        val inflater = LayoutInflater.from(parent.context)

        mParent = parent
        mBeautifyList = list

        val view = inflater.inflate(R.layout.item_search_beautify, parent, false)
        return UserBeautifyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserBeautifyHolder, position: Int) {
        holder.bind(list[position])
    }

    fun addBeautify(fullBeautify: FullBeautify) {
        list.add(fullBeautify)
        mBeautifyList = list
        notifyDataSetChanged()
    }

    fun getBeautify(position: Int): FullBeautify {
        return list[position]
    }

    fun sortDistance() {
        Collections.sort(list, DistanceComparator())
        notifyDataSetChanged()
    }

    fun sortPrice() {
        Collections.sort(list, PriceComparator())
        notifyDataSetChanged()
    }

    inner class DistanceComparator : Comparator<FullBeautify> {
        override fun compare(left: FullBeautify, right: FullBeautify): Int {
            val value1 = left.distance.toDouble()
            val value2 = right.distance.toDouble()
            return value1.compareTo(value2)
        }
    }

    inner class PriceComparator : Comparator<FullBeautify> {
        override fun compare(left: FullBeautify, right: FullBeautify): Int {
            val value1 = left.services[0].price.toDouble()
            val value2 = right.services[0].price.toDouble()
            return value1.compareTo(value2)
        }
    }

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                beautifyList = if (charString.isEmpty()) {
                    mBeautifyList
                } else {
                    val filteredList = arrayListOf<FullBeautify>()
                    for (row in mBeautifyList!!) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.detail.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = beautifyList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResult: FilterResults) {
                list = filterResult.values as ArrayList<FullBeautify>
                notifyDataSetChanged()
            }
        }
    }
}

class UserBeautifyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(beautify: FullBeautify) {
        val byteArray = Base64.decode(beautify.images!![0], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        itemView.imageViewItemBeautify.setImageBitmap(bitmap)
        itemView.textViewSearchStoreName.text = beautify.name
        val openTime = beautify.working[0].open
        val closeTime = beautify.working[0].close
        itemView.textViewSearchOpenTime.text = openTime
        itemView.textViewSearchCloseTime.text = closeTime

        setWorkingTime(beautify.working)
        setWorkingStatus(beautify)
//        setDistance(beautify)

        itemView.textViewSearchNearby.text = beautify.distance

        itemView.setOnClickListener {
//            val distance = itemView.textViewSearchNearby.text.toString()
//            val fullBeautify = beautify.mapToFull()
//            fullBeautify.distance = distance
            val intent = Intent(itemView.context, DetailActivity::class.java)
            StoredValue(itemView.context).saveObject(Constant().PREFS_ITEM_KEY, beautify)
            itemView.context.startActivity(intent)
        }
    }

    private fun setWorkingStatus(beautify: FullBeautify) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_WEEK)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        var curDay = ""
        when (day) {
            Calendar.MONDAY -> {
                curDay = "จ"
            }
            Calendar.TUESDAY -> {
                curDay = "อ"
            }
            Calendar.WEDNESDAY -> {
                curDay = "พ"
            }
            Calendar.THURSDAY -> {
                curDay = "พฤ"
            }
            Calendar.FRIDAY -> {
                curDay = "ศ"
            }
            Calendar.SATURDAY -> {
                curDay = "ส"
            }
            Calendar.SUNDAY -> {
                curDay = "อา"
            }
        }
        val workingTimes = beautify.working
        itemView.textViewSearchStoreStatus.text = "ปิดให้บริการ"
        itemView.textViewSearchStoreStatus.setTextColor(Color.parseColor("#464646"))
        for (working in workingTimes) {
            if (working.day == curDay) {
                val openTimes = working.open.split(":")
                val openHour = openTimes[0].toInt()
                val openMin = openTimes[1].toInt()
                val closeTimes = working.close.split(":")
                val closeHour = closeTimes[0].toInt()
                val closeMin = closeTimes[1].toInt()

                if (hour in (openHour + 1)..(closeHour - 1)) {
                    itemView.textViewSearchStoreStatus.text = "เปิดให้บริการ"
                    itemView.textViewSearchStoreStatus.setTextColor(Color.parseColor("#4CAF50"))
                } else if (hour == openHour || hour == closeHour) {
                    if (min in openMin..closeMin) {
                        itemView.textViewSearchStoreStatus.text = "เปิดให้บริการ"
                        itemView.textViewSearchStoreStatus.setTextColor(Color.parseColor("#4CAF50"))
                    }
                }
            }
        }
    }

    private fun setWorkingTime(workingTime: ArrayList<WorkingTime>) {
        for (working in workingTime) {
            when {
                working.day == "จ" -> itemView.textViewSearchMon.visibility = View.VISIBLE
                working.day == "อ" -> itemView.textViewSearchTue.visibility = View.VISIBLE
                working.day == "พ" -> itemView.textViewSearchWed.visibility = View.VISIBLE
                working.day == "พฤ" -> itemView.textViewSearchThu.visibility = View.VISIBLE
                working.day == "ศ" -> itemView.textViewSearchFri.visibility = View.VISIBLE
                working.day == "ส" -> itemView.textViewSearchSat.visibility = View.VISIBLE
                working.day == "อา" -> itemView.textViewSearchSun.visibility = View.VISIBLE
            }
        }
    }
}
