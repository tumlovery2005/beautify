package com.application.beautify.ui.owner.promotion

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion
import com.application.beautify.model.User
import com.application.beautify.ownerPromotionPresenter
import kotlinx.android.synthetic.main.fragment_add_promotion.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPromotionActivity : AppCompatActivity(), OwnerPromotionView {

    private val presenter by lazy { ownerPromotionPresenter() }
    private var mPromotion: Promotion? = null
    private lateinit var user: User

    private var promoImage: String? = null
    private lateinit var beautifyList: ArrayList<Beautify>
    private lateinit var beautifyNameList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_add_promotion)
        presenter.setView(this)

        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey("promotion")) {
                mPromotion = extras.getParcelable("promotion")
                title = "แก้ไขโปรโมชัน"
            } else {
                title = "เพิ่มโปรโมชัน"
            }
        } else {
            title = "เพิ่มโปรโมชัน"
        }

        user = StoredValue(applicationContext).getObject(Constant().PREFS_USER_KEY) as User
        initUI()
    }

    private fun initUI() {
        beautifyList = arrayListOf()
        beautifyNameList = arrayListOf()
        val arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, beautifyNameList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSelectBeautify!!.adapter = arrayAdapter

        presenter.onGetBeautify()

        addImagePickAction()
        spinnerSelectBeautify.isEnabled = false
        btnSavePromotion.onClick {
            saveAction()
        }
    }

    private fun addImagePickAction() {
        imageViewSelectedPromotion.onClick {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, 11)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 11) {
            val selectedImage = data!!.data
            try {
                val imageFile = File(getRealPathFromUri(selectedImage!!))
                val bitmap = BitmapFactory.decodeFile(imageFile.path)
                imageViewSelectedPromotion.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                promoImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getRealPathFromUri(contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    private fun saveAction() {
        val position = spinnerSelectBeautify.selectedItemPosition
        val beautify = beautifyList[position]
        val detail = editTextPromotionDetail.text.toString()
        val date = Date()
        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val timestamp = sfd.format(date)
        val promotion = Promotion("", beautify.uid, beautify.name, detail, promoImage!!, timestamp)
        presenter.onSavePromotion(promotion)
    }

    override fun onGetPromotionSuccess(promotion: Promotion) {

    }

    override fun onGetBeautify(beautify: Beautify) {
        beautifyList.add(beautify)
        beautifyNameList.add(beautify.name)
        val arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, beautifyNameList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSelectBeautify!!.adapter = arrayAdapter

        if (user.store.isNotBlank()) {
            for (i in 0 until beautifyList.size) {
                if (user.store == beautifyList[i].uid) {
                    spinnerSelectBeautify.setSelection(i)
                }
            }
        } else {
            spinnerSelectBeautify.isEnabled = true
        }
    }

    override fun onAddPromotionSuccess() {
        Toast.makeText(applicationContext, "เพิ่มโปรโมชันสำเร็จ", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onDeleteSuccess() {

    }

    override fun onError() {
        Toast.makeText(applicationContext, "มีการทำงานบางอย่างผิดพลาด", Toast.LENGTH_SHORT).show()
    }
}
