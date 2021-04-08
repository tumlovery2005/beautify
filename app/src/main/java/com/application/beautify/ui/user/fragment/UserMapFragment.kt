package com.application.beautify.ui.user.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.CustomInfoWindowGoogleMap
import com.application.beautify.common.GPSTracker
import com.application.beautify.common.StoredValue
import com.application.beautify.model.Beautify
import com.application.beautify.ui.user.DetailActivity
import com.application.beautify.userSearchPresenter
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserMapFragment : Fragment(), OnMapReadyCallback, UserSearchView {

    private val presenter by lazy { userSearchPresenter() }

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_map, container, false)
        presenter.setView(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        presenter.onGetBeautify()

        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val mapSettings = mMap.uiSettings
        mapSettings.isMyLocationButtonEnabled = true
        mapSettings.isZoomControlsEnabled = true

        val gps = GPSTracker(context!!)
        val curLat = gps.getLatitude()
        val curLng = gps.getLongitude()
        val sydney = LatLng(curLat, curLng)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))
        mMap.setOnInfoWindowClickListener { marker ->
            val beautify = marker.tag as Beautify
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=${beautify.latitude},${beautify.longitude}"))
//            activity!!.startActivity(intent)

            val intent = Intent(context, DetailActivity::class.java)
            StoredValue(context!!).saveObject(Constant().PREFS_ITEM_KEY, beautify)
            startActivity(intent)
        }
    }

    override fun getBeautifySuccess(beautify: Beautify) {
        val customInfoWindow = CustomInfoWindowGoogleMap(activity as Activity)
        mMap.setInfoWindowAdapter(customInfoWindow)

        val beautifyLocation = LatLng(beautify.latitude.toDouble(), beautify.longitude.toDouble())
        val m = mMap.addMarker(MarkerOptions().position(beautifyLocation).title(beautify.name))
        m.tag = beautify
        m.showInfoWindow()
    }

    override fun onGetDataError() {
        Toast.makeText(context, "เกิดข้อผิดพลาดกรุณาลองใหม่ภายหลัง", Toast.LENGTH_SHORT).show()
    }
}
