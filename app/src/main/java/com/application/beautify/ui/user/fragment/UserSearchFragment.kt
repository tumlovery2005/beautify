package com.application.beautify.ui.user.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.model.Direction
import com.application.beautify.R
import com.application.beautify.common.GPSTracker
import com.application.beautify.common.onClick
import com.application.beautify.common.onTextChanged
import com.application.beautify.model.Beautify
import com.application.beautify.ui.user.fragment.adapter.UserSearchAdapter
import com.application.beautify.userSearchPresenter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_user_search.view.*
import java.util.*

class UserSearchFragment : Fragment(), UserSearchView {

    private val presenter by lazy { userSearchPresenter() }

    private lateinit var mView: View
    private lateinit var adapter: UserSearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_user_search, container, false)
        mView = view
        presenter.setView(this)

        initUI()

        return view
    }

    private fun initUI() {
        adapter = UserSearchAdapter(arrayListOf())
        mView.recyclerViewSearch.layoutManager = LinearLayoutManager(context)
        mView.recyclerViewSearch.itemAnimator = DefaultItemAnimator()
        mView.recyclerViewSearch.adapter = adapter

        mView.editTextSearch.onTextChanged { adapter.filter.filter(it) }

        mView.btnSetFilter.onClick {
            mView.cardViewFilter.visibility = View.VISIBLE
        }

        mView.btnSetFilterNearby.setOnClickListener {
            adapter.sortDistance()
        }

        mView.btnSetFilterPrice.setOnClickListener {
            adapter.sortPrice()
        }

        // Filter
        mView.editTextFilterDate.keyListener = null
        mView.editTextFilterTime.keyListener = null
        mView.editTextFilterDate.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            val picker = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> mView.editTextFilterDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()
        }
        mView.editTextFilterTime.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(context!!,
                TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    val hours = if (selectedHour < 10) {
                        "0$selectedHour"
                    } else {
                        "$selectedHour"
                    }
                    val mins = if (selectedMinute < 10) {
                        "0$selectedMinute"
                    } else {
                        "$selectedMinute"
                    }
                    val times = "$hours:$mins"

                    mView.editTextFilterTime.setText(times)

                }, hour, minute, true
            )
            mTimePicker.setTitle("เลือกเวลา")
            mTimePicker.show()
        }
        mView.btnCancelFilter.onClick {
            mView.cardViewFilter.visibility = View.GONE
        }
        mView.btnSetMoreFilter.onClick {
            mView.cardViewFilter.visibility = View.GONE
        }

        presenter.onGetBeautify()
    }

    override fun getBeautifySuccess(beautify: Beautify) {
        setDistance(beautify)
    }

    override fun onGetDataError() {
        Toast.makeText(context, "มีการทำงานบางอย่างผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun setDistance(beautify: Beautify) {
        val gps = GPSTracker(context!!)

        val serverKey = "AIzaSyDt9AWzEDiO2xGYafB6dZZ_e97uFylq_CQ"
        val curLat = gps.getLatitude()
        val curLng = gps.getLongitude()
        val origin = LatLng(curLat, curLng)
        val destination = LatLng(beautify.latitude.toDouble(), beautify.longitude.toDouble())
        GoogleDirection.withServerKey(serverKey)
            .from(origin)
            .to(destination)
            .execute(object : DirectionCallback {
                override fun onDirectionSuccess(direction: Direction, rawBody: String) {
                    val route = direction.routeList[0]
                    val leg = route.legList[0]
                    val durationInfo = leg.duration

                    val meters = durationInfo.value.toDouble()
                    val kilo: Double = meters/1000.00

                    val fullBeautify = beautify.mapToFull()

                    fullBeautify.distance = kilo.toString()

                    adapter.addBeautify(fullBeautify)
                }

                override fun onDirectionFailure(t: Throwable) {

                }
            })
    }
}
