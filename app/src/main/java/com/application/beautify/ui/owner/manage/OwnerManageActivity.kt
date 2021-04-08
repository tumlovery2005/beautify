package com.application.beautify.ui.owner.manage

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.model.Beautify
import com.application.beautify.model.Services
import com.application.beautify.model.User
import com.application.beautify.model.WorkingTime
import com.application.beautify.ownerManagePresenter
import kotlinx.android.synthetic.main.fragment_add_beautify.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*

class OwnerManageActivity : AppCompatActivity(), View.OnClickListener, OwnerManageView {

    private val presenter by lazy { ownerManagePresenter() }

    private lateinit var user: User

    private var image1: String? = null
    private var image2: String? = null
    private var image3: String? = null
    private var image4: String? = null

    private var beautifyWorking: ArrayList<WorkingTime> = arrayListOf()
    private var beautifyServices: ArrayList<Services> = arrayListOf()
    private var beautifyImages: ArrayList<String> = arrayListOf()

    private var mBeautify: Beautify? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_beautify)
        presenter.setView(this)
        title = "จัดการร้าน"

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User

        initUI()
        presenter.onGetBeautify(user.store)
    }

    private fun initUI() {
        addBtnWorkingAction()
        checkCheckbox()
//        addSeekerAction()
        checkImage()
        addImagePickAction()
        btnAddBeautifySave.onClick {
            saveAction()
        }
    }

    private fun addBtnWorkingAction() {
        btnWorkingMonOpen.setOnClickListener(this)
        btnWorkingMonClose.setOnClickListener(this)
        btnWorkingTueOpen.setOnClickListener(this)
        btnWorkingTueClose.setOnClickListener(this)
        btnWorkingWedOpen.setOnClickListener(this)
        btnWorkingWedClose.setOnClickListener(this)
        btnWorkingThuOpen.setOnClickListener(this)
        btnWorkingThuClose.setOnClickListener(this)
        btnWorkingFriOpen.setOnClickListener(this)
        btnWorkingFriClose.setOnClickListener(this)
        btnWorkingSatOpen.setOnClickListener(this)
        btnWorkingSatClose.setOnClickListener(this)
        btnWorkingSunOpen.setOnClickListener(this)
        btnWorkingSunClose.setOnClickListener(this)
    }

    override fun onClick(btn: View) {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(applicationContext,
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
        checkBoxBeautifyWorkingMon.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingMonOpen.isEnabled = true
                btnWorkingMonClose.isEnabled = true
            } else {
                btnWorkingMonOpen.isEnabled = false
                btnWorkingMonClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingTue.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingTueOpen.isEnabled = true
                btnWorkingTueClose.isEnabled = true
            } else {
                btnWorkingTueOpen.isEnabled = false
                btnWorkingTueClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingWed.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingWedOpen.isEnabled = true
                btnWorkingWedClose.isEnabled = true
            } else {
                btnWorkingWedOpen.isEnabled = false
                btnWorkingWedClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingThu.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingThuOpen.isEnabled = true
                btnWorkingThuClose.isEnabled = true
            } else {
                btnWorkingThuOpen.isEnabled = false
                btnWorkingThuClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingFri.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingFriOpen.isEnabled = true
                btnWorkingFriClose.isEnabled = true
            } else {
                btnWorkingFriOpen.isEnabled = false
                btnWorkingFriClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingSat.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingSatOpen.isEnabled = true
                btnWorkingSatClose.isEnabled = true
            } else {
                btnWorkingSatOpen.isEnabled = false
                btnWorkingSatClose.isEnabled = false
            }
        }
        checkBoxBeautifyWorkingSun.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck) {
                btnWorkingSunOpen.isEnabled = true
                btnWorkingSunClose.isEnabled = true
            } else {
                btnWorkingSunOpen.isEnabled = false
                btnWorkingSunClose.isEnabled = false
            }
        }
    }

//    private fun addSeekerAction() {
//        seekBarServiceOne.max = 500
//        seekBarServiceTwo.max = 3000
//        seekBarServiceThree.max = 3000
//
//        seekBarServiceOne.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                textViewServiceOnePrice.text = price
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
//        seekBarServiceTwo.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                textViewServiceTwoPrice.text = price
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
//        seekBarServiceThree.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
//                val price = "$progress"
//                textViewServiceThreePrice.text = price
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
            imageViewBeautifyTwo.visibility = View.GONE
        } else {
            imageViewBeautifyTwo.visibility = View.VISIBLE
        }
        if (image2 == null) {
            imageViewBeautifyThree.visibility = View.GONE
        } else {
            imageViewBeautifyThree.visibility = View.VISIBLE
        }
        if (image3 == null) {
            imageViewBeautifyFour.visibility = View.GONE
        } else {
            imageViewBeautifyFour.visibility = View.VISIBLE
        }
    }

    private fun addImagePickAction() {
        imageViewBeautifyOne.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 11)
        }
        imageViewBeautifyTwo.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 12)
        }
        imageViewBeautifyThree.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 13)
        }
        imageViewBeautifyFour.onClick {
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
                imageViewBeautifyOne.setImageBitmap(bitmap)

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
                imageViewBeautifyTwo.setImageBitmap(bitmap)

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
                imageViewBeautifyThree.setImageBitmap(bitmap)

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
                imageViewBeautifyFour.setImageBitmap(bitmap)

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
            cursor = applicationContext.contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    private fun saveAction() {
        val name = editTextBeautifyAddName.text.toString()
        val lat = editTextBeautifyAddLat.text.toString()
        val lng = editTextBeautifyAddLng.text.toString()
        val tel = editTextBeautifyAddTel.text.toString()
        val address = editTextBeautifyAddAddress.text.toString()

        beautifyWorking = arrayListOf()
        if (checkBoxBeautifyWorkingMon.isChecked) {
            beautifyWorking.add(WorkingTime("จ", btnWorkingMonOpen.text.toString(), btnWorkingMonClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingTue.isChecked) {
            beautifyWorking.add(WorkingTime("อ", btnWorkingTueOpen.text.toString(), btnWorkingTueClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingWed.isChecked) {
            beautifyWorking.add(WorkingTime("พ", btnWorkingWedOpen.text.toString(), btnWorkingWedClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingThu.isChecked) {
            beautifyWorking.add(WorkingTime("พฤ", btnWorkingThuOpen.text.toString(), btnWorkingThuClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingFri.isChecked) {
            beautifyWorking.add(WorkingTime("ศ", btnWorkingFriOpen.text.toString(), btnWorkingFriClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingSat.isChecked) {
            beautifyWorking.add(WorkingTime("ส", btnWorkingSatOpen.text.toString(), btnWorkingSatClose.text.toString()))
        }
        if (checkBoxBeautifyWorkingSun.isChecked) {
            beautifyWorking.add(WorkingTime("อา", btnWorkingSunOpen.text.toString(), btnWorkingSunClose.text.toString()))
        }

        beautifyServices = arrayListOf()
        val servicePrice1 = textViewServiceOnePrice.text.toString().toInt()
        val servicePrice2 = textViewServiceTwoPrice.text.toString().toInt()
        val servicePrice3 = textViewServiceThreePrice.text.toString().toInt()
        beautifyServices.add(Services(textViewServiceOne.text.toString(), servicePrice1))
        beautifyServices.add(Services(textViewServiceTwo.text.toString(), servicePrice2))
        beautifyServices.add(Services(textViewServiceThree.text.toString(), servicePrice3))

        val detail = editTextBeautifyAddDetail.text.toString()

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
            Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
        } else if (beautifyWorking.size < 1) {
            Toast.makeText(applicationContext, "กรุณากรอกเลือกเวลาเปิด-ปิดร้าน", Toast.LENGTH_SHORT).show()
        } else if (servicePrice1 < 1 || servicePrice2 < 1 || servicePrice3 < 1) {
            Toast.makeText(applicationContext, "กรุณากรอกเพิ่มราคาของการบริการ", Toast.LENGTH_SHORT).show()
        } else if (detail.isBlank()) {
            Toast.makeText(applicationContext, "กรุณากรอกรายละเอียด", Toast.LENGTH_SHORT).show()
        } else if (beautifyImages.size < 1) {
            Toast.makeText(applicationContext, "กรุณากรอกเพิ่มรูปภาพของร้านอย่างน้อย 1 ภาพ", Toast.LENGTH_SHORT).show()
        } else {
            val beautify = Beautify(mBeautify!!.uid, name, lat, lng, tel, address, beautifyWorking, beautifyServices, detail, beautifyImages, mBeautify!!.rating)
            presenter.onUpdateBeautify(beautify)
        }
    }
    
    override fun onGetStore(beautify: Beautify) {
        mBeautify = beautify
        updateUI()
    }

    private fun updateUI() {
        editTextBeautifyAddName.setText(mBeautify!!.name)
        editTextBeautifyAddLat.setText(mBeautify!!.latitude)
        editTextBeautifyAddLng.setText(mBeautify!!.longitude)
        editTextBeautifyAddTel.setText(mBeautify!!.tel)
        editTextBeautifyAddAddress.setText(mBeautify!!.address)
        editTextBeautifyAddDetail.setText(mBeautify!!.detail)
        val curWorkingTime = mBeautify!!.working
        for (i in 0 until curWorkingTime.size) {
            when {
                curWorkingTime[i].day == "จ" -> {
                    checkBoxBeautifyWorkingMon.isChecked = true
                    btnWorkingMonOpen.text = curWorkingTime[i].open
                    btnWorkingMonClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "อ" -> {
                    checkBoxBeautifyWorkingTue.isChecked = true
                    btnWorkingTueOpen.text = curWorkingTime[i].open
                    btnWorkingTueClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "พ" -> {
                    checkBoxBeautifyWorkingWed.isChecked = true
                    btnWorkingWedOpen.text = curWorkingTime[i].open
                    btnWorkingWedClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "พฤ" -> {
                    checkBoxBeautifyWorkingThu.isChecked = true
                    btnWorkingThuOpen.text = curWorkingTime[i].open
                    btnWorkingThuClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "ศ" -> {
                    checkBoxBeautifyWorkingFri.isChecked = true
                    btnWorkingFriOpen.text = curWorkingTime[i].open
                    btnWorkingFriClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "ส" -> {
                    checkBoxBeautifyWorkingSat.isChecked = true
                    btnWorkingSatOpen.text = curWorkingTime[i].open
                    btnWorkingSatClose.text = curWorkingTime[i].close
                }
                curWorkingTime[i].day == "อา" -> {
                    checkBoxBeautifyWorkingSun.isChecked = true
                    btnWorkingSunOpen.text = curWorkingTime[i].open
                    btnWorkingSunClose.text = curWorkingTime[i].close
                }
            }
        }
        textViewServiceOnePrice.setText(mBeautify!!.services[0].price.toString())
        textViewServiceTwoPrice.setText(mBeautify!!.services[1].price.toString())
        textViewServiceThreePrice.setText(mBeautify!!.services[2].price.toString())

        val curImages = mBeautify!!.images
        for (x in 0 until curImages.size) {
            when (x) {
                0 -> {
                    image1 = curImages[x]
                    val byteArray = Base64.decode(image1, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageViewBeautifyOne.setImageBitmap(bitmap)
                }
                1 -> {
                    image2 = curImages[x]
                    val byteArray = Base64.decode(image2, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageViewBeautifyTwo.setImageBitmap(bitmap)
                }
                2 -> {
                    image3 = curImages[x]
                    val byteArray = Base64.decode(image3, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageViewBeautifyThree.setImageBitmap(bitmap)
                }
                3 -> {
                    image4 = curImages[x]
                    val byteArray = Base64.decode(image4, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageViewBeautifyFour.setImageBitmap(bitmap)
                }
            }
        }
        checkImage()
    }

    override fun onUpdateSuccess() {
        Toast.makeText(applicationContext, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show()
    }

    override fun onError() {
        Toast.makeText(applicationContext, "เกิดข้อผิดพลาดในการเรียกข้อมูล", Toast.LENGTH_SHORT).show()
    }
}
