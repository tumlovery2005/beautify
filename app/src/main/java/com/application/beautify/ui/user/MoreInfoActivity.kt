package com.application.beautify.ui.user

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.model.FullBeautify
import com.application.beautify.model.WorkingTime
import kotlinx.android.synthetic.main.activity_more_info.*

class MoreInfoActivity : AppCompatActivity() {

    private lateinit var beautify: FullBeautify

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        title = "More Info"

        beautify = StoredValue(applicationContext).getObject(Constant().PREFS_ITEM_KEY) as FullBeautify

        initUI()
    }

    private fun initUI() {
        val byteArray = Base64.decode(beautify.images!![0], Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        imageViewInfo.setImageBitmap(bitmap)

        textViewInfoAddress.text = beautify.address

        textViewSearchOpenTime.text = beautify.working[0].open
        textViewSearchCloseTime.text = beautify.working[0].close
        textViewSearchNearby.text = beautify.distance
        textViewSearchTel.text = beautify.tel

        setWorkingTime(beautify.working)
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
}
