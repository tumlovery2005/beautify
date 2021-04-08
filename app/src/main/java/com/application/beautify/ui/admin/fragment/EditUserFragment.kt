package com.application.beautify.ui.admin.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import com.application.beautify.R
import com.application.beautify.common.Constant
import com.application.beautify.common.StoredValue
import com.application.beautify.model.User
import kotlinx.android.synthetic.main.fragment_user_register.*

/**
 * Created by GuGolf on 26/4/2019 AD.
 */
class EditUserFragment : DialogFragment() {

    private lateinit var mDialog: Dialog

    private lateinit var user: User

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.fragment_user_register, LinearLayout(activity), false)
        val builder = Dialog(activity!!)
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE)
        builder.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        builder.setContentView(view)

        mDialog = builder

        user = StoredValue(context!!).getObject(Constant().PREFS_USERS_KEY) as User

        initUI()

        return builder
    }

    private fun initUI() {
        mDialog.editTextAddUserEmail.setText(user.email)
        mDialog.editTextAddUserName.setText(user.username)
        mDialog.editTextAddUserPassword.setText(user.password)
        mDialog.editTextAddUserTel.setText(user.tel)
    }
}