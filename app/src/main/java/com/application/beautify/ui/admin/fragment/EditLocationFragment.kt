package com.application.beautify.ui.admin.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.beautifyPresenter
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.common.onClick
import com.application.beautify.model.Beautify
import com.application.beautify.model.User
import kotlinx.android.synthetic.main.fragment_add_location.*

/**
 * Created by GuGolf on 26/4/2019 AD.
 */
class EditLocationFragment: DialogFragment(), BeautifyView {

    private val presenter by lazy { beautifyPresenter() }

    private lateinit var mDialog: Dialog

    private lateinit var beautify: Beautify

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_add_location, LinearLayout(activity), false)
        val builder = Dialog(activity!!)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.setContentView(view)

        presenter.setView(this)

        mDialog = builder

        beautify = StoredValue(context!!).getObject(Constant().PREFS_BEAUTIFY_KEY) as Beautify

        initUI()

        return builder
    }

    private fun initUI() {
        mDialog.textViewAddLocationName.text = beautify.name
        mDialog.editTextAddLat.setText(beautify.latitude)
        mDialog.editTextAddLng.setText(beautify.longitude)
        mDialog.btnSaveLocation.onClick {
            beautify.latitude = editTextAddLat.text.toString()
            beautify.longitude = editTextAddLng.text.toString()
            presenter.editLocation(beautify)
        }
    }

    override fun getBeautifySuccess(beautify: Beautify) {

    }

    override fun addBeautifySuccess() {
        Toast.makeText(context!!, "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show()
        mDialog.dismiss()
    }

    override fun getUserSuccess(user: User) {

    }

    override fun showError(message: String) {
        Toast.makeText(context!!, "เกิดข้อผิดพลาด! กรุณาลองใหม่อีกครั้ง", Toast.LENGTH_SHORT).show()
    }
}