package com.application.beautify.ui.user

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.application.beautify.bookingPresenter
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.model.Booking
import com.application.beautify.model.User
import kotlinx.android.synthetic.main.activity_booking.*
import java.util.*


class BookingActivity : AppCompatActivity(), BookingView {

    private val presenter by lazy { bookingPresenter() }

    private lateinit var user: User

    private lateinit var store: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.application.beautify.R.layout.activity_booking)
        presenter.setView(this)

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        store = intent.getStringExtra("store")

        initUI()
    }

    private fun initUI() {
        editTextBookingDate.keyListener = null
        editTextBookingTime.keyListener = null
        editTextBookingDate.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            val picker = DatePickerDialog(this@BookingActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> editTextBookingDate.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()
        }
        editTextBookingTime.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
            val minute = mcurrentTime.get(Calendar.MINUTE)
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this@BookingActivity,
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

                    editTextBookingTime.setText(times)

                }, hour, minute, true
            )
            mTimePicker.setTitle("เลือกเวลา")
            mTimePicker.show()
        }
        btnBooking.onClick {
            if (editTextBookingDate.text.toString().isNotBlank() && editTextBookingTime.text.toString().isNotBlank()) {
                val booking = Booking(
                    "",
                    store,
                    user.username,
                    editTextBookingTel.text.toString(),
                    editTextBookingDate.text.toString(),
                    editTextBookingTime.text.toString()
                )
                presenter.onAddBooking(booking)
            } else {
                Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ครบถ่้วน", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun addBookingSuccess() {
        Toast.makeText(applicationContext, "จองสำเร็จ", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onError() {
        Toast.makeText(applicationContext, "การข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}
