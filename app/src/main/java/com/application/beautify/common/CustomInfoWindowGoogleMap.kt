package com.application.beautify.common

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.View
import com.application.beautify.R
import com.application.beautify.model.Beautify
import com.application.beautify.ui.user.DetailActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.item_beautify_map.view.*

class CustomInfoWindowGoogleMap(private val activity: Activity): GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(marker: Marker?): View {
        val view = activity.layoutInflater.inflate(R.layout.item_beautify_map, null)

        if (marker!!.tag != null) {
            val infoWindowData = marker.tag as Beautify
            view.textViewItemBeautify.text = infoWindowData.name
            val byteArray = Base64.decode(infoWindowData.images[0], Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            view.imageViewItemBeautify.setImageBitmap(bitmap)
            view.btnItemBeautifyNav.onClick {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=${infoWindowData.latitude},${infoWindowData.longitude}"))
                activity.startActivity(intent)
            }
            view.btnItemBeautifyDetail.onClick {
                val intent = Intent(activity, DetailActivity::class.java)
                val fullBeautify = infoWindowData.mapToFull()
                StoredValue(activity).saveObject(Constant().PREFS_ITEM_KEY, fullBeautify)
                activity.startActivity(intent)
            }
        }
        return view
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}