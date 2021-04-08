package com.application.beautify.ui.admin.fragment

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.LinearLayout
import com.application.beautify.R
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window.FEATURE_NO_TITLE
import kotlinx.android.synthetic.main.fragment_add_beautify.*
import android.app.TimePickerDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import com.application.beautify.beautifyPresenter
import com.application.beautify.common.onClick
import com.application.beautify.model.Beautify
import com.application.beautify.model.Services
import com.application.beautify.model.User
import com.application.beautify.model.WorkingTime
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by GuGolf on 19/4/2019 AD.
 */

class AddBeautifyFragment: DialogFragment(), View.OnClickListener, BeautifyView {

    private val presenter by lazy { beautifyPresenter() }

    private lateinit var mDialog: Dialog

    private var image1: String? = null
    private var image2: String? = null
    private var image3: String? = null
    private var image4: String? = null

    private var beautifyWorking: ArrayList<WorkingTime> = arrayListOf()
    private var beautifyServices: ArrayList<Services> = arrayListOf()
    private var beautifyImages: ArrayList<String> = arrayListOf()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_add_beautify, LinearLayout(activity), false)
        val builder = Dialog(activity!!)
        builder.requestWindowFeature(FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        builder.setContentView(view)

        presenter.setView(this)

        mDialog = builder
        initUI()

        return builder
    }

    private fun initUI() {
        addBtnWorkingAction()
        checkCheckbox()
//        addSeekerAction()
        checkImage()
        addImagePickAction()
        mDialog.btnAddBeautifySave.onClick {
            saveAction()
        }
    }

    private fun addBtnWorkingAction() {
        mDialog.btnWorkingMonOpen.setOnClickListener(this)
        mDialog.btnWorkingMonClose.setOnClickListener(this)
        mDialog.btnWorkingTueOpen.setOnClickListener(this)
        mDialog.btnWorkingTueClose.setOnClickListener(this)
        mDialog.btnWorkingWedOpen.setOnClickListener(this)
        mDialog.btnWorkingWedClose.setOnClickListener(this)
        mDialog.btnWorkingThuOpen.setOnClickListener(this)
        mDialog.btnWorkingThuClose.setOnClickListener(this)
        mDialog.btnWorkingFriOpen.setOnClickListener(this)
        mDialog.btnWorkingFriClose.setOnClickListener(this)
        mDialog.btnWorkingSatOpen.setOnClickListener(this)
        mDialog.btnWorkingSatClose.setOnClickListener(this)
        mDialog.btnWorkingSunOpen.setOnClickListener(this)
        mDialog.btnWorkingSunClose.setOnClickListener(this)
    }

    override fun onClick(btn: View) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(context,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val clickedButton = btn as Button
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
                clickedButton.text = times
            }, hour, minute, true
        )
        mTimePicker.setTitle("เลือกเวลา")
        mTimePicker.show()
    }

    private fun checkCheckbox() {
        mDialog.checkBoxBeautifyWorkingMon.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingMonOpen.isEnabled = true
                mDialog.btnWorkingMonClose.isEnabled = true
            } else {
                mDialog.btnWorkingMonOpen.isEnabled = false
                mDialog.btnWorkingMonClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingTue.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingTueOpen.isEnabled = true
                mDialog.btnWorkingTueClose.isEnabled = true
            } else {
                mDialog.btnWorkingTueOpen.isEnabled = false
                mDialog.btnWorkingTueClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingWed.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingWedOpen.isEnabled = true
                mDialog.btnWorkingWedClose.isEnabled = true
            } else {
                mDialog.btnWorkingWedOpen.isEnabled = false
                mDialog.btnWorkingWedClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingThu.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingThuOpen.isEnabled = true
                mDialog.btnWorkingThuClose.isEnabled = true
            } else {
                mDialog.btnWorkingThuOpen.isEnabled = false
                mDialog.btnWorkingThuClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingFri.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingFriOpen.isEnabled = true
                mDialog.btnWorkingFriClose.isEnabled = true
            } else {
                mDialog.btnWorkingFriOpen.isEnabled = false
                mDialog.btnWorkingFriClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingSat.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingSatOpen.isEnabled = true
                mDialog.btnWorkingSatClose.isEnabled = true
            } else {
                mDialog.btnWorkingSatOpen.isEnabled = false
                mDialog.btnWorkingSatClose.isEnabled = false
            }
        }
        mDialog.checkBoxBeautifyWorkingSun.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                mDialog.btnWorkingSunOpen.isEnabled = true
                mDialog.btnWorkingSunClose.isEnabled = true
            } else {
                mDialog.btnWorkingSunOpen.isEnabled = false
                mDialog.btnWorkingSunClose.isEnabled = false
            }
        }
    }

//    private fun addSeekerAction() {
//        mDialog.seekBarServiceOne.max = 500
//        mDialog.seekBarServiceTwo.max = 3000
//        mDialog.seekBarServiceThree.max = 3000
//
//        mDialog.seekBarServiceOne.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                mDialog.textViewServiceOnePrice.text = price
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//
//            }
//
//        })
//        mDialog.seekBarServiceTwo.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                mDialog.textViewServiceTwoPrice.text = price
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//
//            }
//
//        })
//        mDialog.seekBarServiceThree.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                mDialog.textViewServiceThreePrice.text = price
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//
//            }
//
//        })
//    }

    private fun checkImage() {
        if (image1 == null) {
            mDialog.imageViewBeautifyTwo.visibility = View.GONE
        } else {
            mDialog.imageViewBeautifyTwo.visibility = View.VISIBLE
        }
        if (image2 == null) {
            mDialog.imageViewBeautifyThree.visibility = View.GONE
        } else {
            mDialog.imageViewBeautifyThree.visibility = View.VISIBLE
        }
        if (image3 == null) {
            mDialog.imageViewBeautifyFour.visibility = View.GONE
        } else {
            mDialog.imageViewBeautifyFour.visibility = View.VISIBLE
        }
    }

    private fun addImagePickAction() {
        mDialog.imageViewBeautifyOne.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 11)
        }
        mDialog.imageViewBeautifyTwo.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 12)
        }
        mDialog.imageViewBeautifyThree.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 13)
        }
        mDialog.imageViewBeautifyFour.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 14)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 11) {
            val selectedImage = data!!.data
            try {
                val imageFile = File(getRealPathFromUri(selectedImage!!))
                val bitmap = BitmapFactory.decodeFile(imageFile.path)
                mDialog.imageViewBeautifyOne.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                image1 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                checkImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 12) {
            val selectedImage = data!!.data
            try {
                val imageFile = File(getRealPathFromUri(selectedImage!!))
                val bitmap = BitmapFactory.decodeFile(imageFile.path)
                mDialog.imageViewBeautifyTwo.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                image2 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                checkImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 13) {
            val selectedImage = data!!.data
            try {
                val imageFile = File(getRealPathFromUri(selectedImage!!))
                val bitmap = BitmapFactory.decodeFile(imageFile.path)
                mDialog.imageViewBeautifyThree.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                image3 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                checkImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (resultCode == Activity.RESULT_OK && requestCode == 14) {
            val selectedImage = data!!.data
            try {
                val imageFile = File(getRealPathFromUri(selectedImage!!))
                val bitmap = BitmapFactory.decodeFile(imageFile.path)
                mDialog.imageViewBeautifyFour.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                image4 = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                checkImage()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getRealPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = activity!!.contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    private fun saveAction() {
        val name = mDialog.editTextBeautifyAddName.text.toString()
        val lat = mDialog.editTextBeautifyAddLat.text.toString()
        val lng = mDialog.editTextBeautifyAddLng.text.toString()
        val tel = mDialog.editTextBeautifyAddTel.text.toString()
        val address = mDialog.editTextBeautifyAddAddress.text.toString()

        beautifyWorking = arrayListOf()
        if (mDialog.checkBoxBeautifyWorkingMon.isChecked) {
            beautifyWorking.add(WorkingTime("จ", mDialog.btnWorkingMonOpen.text.toString(), mDialog.btnWorkingMonClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingTue.isChecked) {
            beautifyWorking.add(WorkingTime("อ", mDialog.btnWorkingTueOpen.text.toString(), mDialog.btnWorkingTueClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingWed.isChecked) {
            beautifyWorking.add(WorkingTime("พ", mDialog.btnWorkingWedOpen.text.toString(), mDialog.btnWorkingWedClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingThu.isChecked) {
            beautifyWorking.add(WorkingTime("พฤ", mDialog.btnWorkingThuOpen.text.toString(), mDialog.btnWorkingThuClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingFri.isChecked) {
            beautifyWorking.add(WorkingTime("ศ", mDialog.btnWorkingFriOpen.text.toString(), mDialog.btnWorkingFriClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingSat.isChecked) {
            beautifyWorking.add(WorkingTime("ส", mDialog.btnWorkingSatOpen.text.toString(), mDialog.btnWorkingSatClose.text.toString()))
        }
        if (mDialog.checkBoxBeautifyWorkingSun.isChecked) {
            beautifyWorking.add(WorkingTime("อา", mDialog.btnWorkingSunOpen.text.toString(), mDialog.btnWorkingSunClose.text.toString()))
        }

        beautifyServices = arrayListOf()
        val servicePrice1 = mDialog.textViewServiceOnePrice.text.toString().toInt()
        val servicePrice2 = mDialog.textViewServiceTwoPrice.text.toString().toInt()
        val servicePrice3 = mDialog.textViewServiceThreePrice.text.toString().toInt()
        beautifyServices.add(Services(mDialog.textViewServiceOne.text.toString(), servicePrice1))
        beautifyServices.add(Services(mDialog.textViewServiceTwo.text.toString(), servicePrice2))
        beautifyServices.add(Services(mDialog.textViewServiceThree.text.toString(), servicePrice3))

        val detail = mDialog.editTextBeautifyAddDetail.text.toString()

        beautifyImages = arrayListOf()
        if (image1 != null) {
            beautifyImages.add(image1!!)
        }
        if (image2 != null) {
            beautifyImages.add(image2!!)
        }
        if (image3 != null) {
            beautifyImages.add(image3!!)
        }
        if (image4 != null) {
            beautifyImages.add(image4!!)
        }

        if (name.isBlank() || lat.isBlank() || lng.isBlank() || tel.isBlank() || address.isBlank()) {
            Toast.makeText(context, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
        } else if (beautifyWorking.size < 1) {
            Toast.makeText(context, "กรุณากรอกเลือกเวลาเปิด-ปิดร้าน", Toast.LENGTH_SHORT).show()
        } else if (servicePrice1 < 1 || servicePrice2 < 1 || servicePrice3 < 1) {
            Toast.makeText(context, "กรุณากรอกเพิ่มราคาของการบริการ", Toast.LENGTH_SHORT).show()
        } else if (detail.isBlank()) {
            Toast.makeText(context, "กรุณากรอกรายละเอียด", Toast.LENGTH_SHORT).show()
        } else if (beautifyImages.size < 1) {
            Toast.makeText(context, "กรุณากรอกเพิ่มรูปภาพของร้านอย่างน้อย 1 ภาพ", Toast.LENGTH_SHORT).show()
        } else {
            val beautify = Beautify("", name, lat, lng, tel, address, beautifyWorking, beautifyServices, detail, beautifyImages, "0")
            presenter.onAddBeautify(beautify)
        }
    }

    override fun getBeautifySuccess(beautify: Beautify) {

    }

    override fun addBeautifySuccess() {
        Toast.makeText(context, "สำเร็จ", Toast.LENGTH_SHORT).show()
        mDialog.dismiss()
    }

    override fun getUserSuccess(user: User) {

    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}