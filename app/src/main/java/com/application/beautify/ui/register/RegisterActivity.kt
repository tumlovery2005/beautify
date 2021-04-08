package com.application.beautify.ui.register

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.onClick
import com.application.beautify.common.onTextChanged
import com.application.beautify.common.showGeneralError
import com.application.beautify.model.Beautify
import com.application.beautify.userPresenter
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_add_promotion.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class RegisterActivity : AppCompatActivity(), UserView {

    private val presenter by lazy { userPresenter() }

    private var isOwner: Boolean = false
    private var store: String = ""
    private var image: String = ""

    private var nameList: ArrayList<String> = arrayListOf()
    private var list: ArrayList<Beautify> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter.setView(this)
        presenter.getBeautify()
        title = "สมัครสมาชิก"

        initUI()
    }

    private fun initUI() {
        editTextAddUserName.onTextChanged { presenter.onUsernameChanged(it) }
        editTextAddUserEmail.onTextChanged { presenter.onEmailChanged(it) }
        editTextAddUserPassword.onTextChanged { presenter.onPasswordChanged(it) }
        editTextAddUserConfirmPass.onTextChanged { presenter.onConfirmPasswordChanged(it) }
        editTextAddUserTel.onTextChanged { presenter.onTelChanged(it) }

        spinnerBeautify.visibility = View.GONE
        textViewDocument.visibility = View.GONE
        btnAddDocument.visibility = View.GONE

        checkBoxAddUserType.setOnCheckedChangeListener { _, isChecked ->
            isOwner = isChecked
            if (isChecked) {
                spinnerBeautify.visibility = View.VISIBLE
                textViewDocument.visibility = View.VISIBLE
                btnAddDocument.visibility = View.VISIBLE
            } else {
                spinnerBeautify.visibility = View.GONE
                textViewDocument.visibility = View.GONE
                btnAddDocument.visibility = View.GONE
            }
        }

        btnAddUser.onClick {
            store = list[spinnerBeautify.selectedItemPosition].uid
            if (isOwner) {
                presenter.onCheckStore(store)
            } else {
                presenter.onRegisterTap(isOwner, store, image)
            }
        }

        btnAddDocument.onClick {
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
                btnAddDocument.setImageBitmap(bitmap)

                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val imageByteArray = stream.toByteArray()
                image = Base64.encodeToString(imageByteArray, Base64.DEFAULT)
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

    override fun onRegisterSuccess() {
        Toast.makeText(applicationContext, "เพิ่มผู้ใช้งานสำเร็จ", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onGetBeautify(beautify: Beautify) {
        nameList.add(beautify.name)
        list.add(beautify)
        val arrayAdapter = ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, nameList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerBeautify!!.adapter = arrayAdapter
    }

    override fun onCheckStoreResult(hasStore: Boolean) {
        if (hasStore) {
            presenter.onRegisterTap(isOwner, store, image)
        } else {
            Toast.makeText(applicationContext, "รหัสร้านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError(title: String, message: String, button: String) {
        showGeneralError(this, title, message, button)
    }
}
