package com.application.beautify.ui.user

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_beautify_detail.*
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.application.beautify.detailPresenter
import com.application.beautify.model.*
import com.google.firebase.auth.FirebaseAuth


class DetailActivity : AppCompatActivity(), OnMapReadyCallback, DetailView {

    private val presenter by lazy { detailPresenter() }

    private lateinit var mMap: GoogleMap
    private lateinit var beautify: FullBeautify
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_beautify_detail)
        presenter.setView(this)

        beautify = StoredValue(applicationContext).getObject(Constant().PREFS_ITEM_KEY) as FullBeautify
        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapViewDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(beautify.latitude.toDouble(), beautify.longitude.toDouble())
        mMap.addMarker(MarkerOptions().position(sydney).title(beautify.name))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))

        initUI()
    }

    private fun initUI() {
        val byteArray = Base64.decode(beautify.images!![0], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imageViewDetail.setImageBitmap(bitmap)
        textViewDetailName.text = beautify.name
        textViewSearchOpenTime.text = beautify.working[0].open
        textViewSearchCloseTime.text = beautify.working[0].close
        textViewSearchNearby.text = beautify.distance
        textViewSearchTel.text = beautify.tel

        setWorkingTime(beautify.working)

        btnDetailContact.onClick {
            AlertDialog.Builder(this)
                .setTitle("ยืนยันการโทร")
                .setMessage("โทรหา ${beautify.tel}")
                .setPositiveButton("ตกลง") { _, _ ->
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:${beautify.tel}")
                    startActivity(intent)
                }
                .setNegativeButton("ยกเลิก") { dialog, _ -> dialog.dismiss() }
                .show()
        }
        btnDetailMap.onClick {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=${beautify.latitude},${beautify.longitude}"))
            startActivity(intent)
        }
        if (FirebaseAuth.getInstance().currentUser != null) {
            btnDetailFav.isEnabled = true
            btnDetailBooking.isEnabled = true
        } else {
            btnDetailFav.isEnabled = false
            btnDetailFav.isEnabled = false
        }

        btnDetailFav.onClick {
            presenter.onAddFav(user.uid, beautify.mapToBeautify())
        }
        btnDetailBooking.onClick {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("store", beautify.uid)
            startActivity(intent)
        }
        btnMoreInfo.onClick {
            startActivity(Intent(this, MoreInfoActivity::class.java))
        }
    }

    private fun setWorkingTime(workingTime: ArrayList<WorkingTime>) {
        for (working in workingTime) {
            when {
                working.day == "จ" -> textViewSearchMon.visibility = View.VISIBLE
                working.day == "อ" -> textViewSearchTue.visibility = View.VISIBLE
                working.day == "พ" -> textViewSearchWed.visibility = View.VISIBLE
                working.day == "พฤ" -> textViewSearchThu.visibility = View.VISIBLE
                working.day == "ศ" -> textViewSearchFri.visibility = View.VISIBLE
                working.day == "ส" -> textViewSearchSat.visibility = View.VISIBLE
                working.day == "อา" -> textViewSearchSun.visibility = View.VISIBLE
            }
        }
    }

    override fun addFavoriteSuccess() {
        Toast.makeText(applicationContext, "เพิ่มรายการโปรดเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
    }

    override fun onGetFavorite(favorite: Favorite) {

    }

    override fun onError() {
        Toast.makeText(applicationContext, "เกิดข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}
