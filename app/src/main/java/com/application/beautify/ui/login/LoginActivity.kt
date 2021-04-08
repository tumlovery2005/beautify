package com.application.beautify.ui.login

import android.Manifest
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast
import com.application.beautify.R
import com.application.beautify.common.*
import com.application.beautify.loginPresenter
import com.application.beautify.model.User
import com.application.beautify.ui.admin.AdminActivity
import com.application.beautify.ui.owner.OwnerActivity
import com.application.beautify.ui.register.RegisterActivity
import com.application.beautify.ui.user.UserActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter by lazy { loginPresenter() }

    private lateinit var storedValue: StoredValue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "login"
        presenter.setView(this)

        storedValue = StoredValue(applicationContext)
        initUI()
    }

    private fun initUI() {
        editTextEmail.onTextChanged { presenter.onUsernameChanged(it) }
        editTextPassword.onTextChanged { presenter.onPasswordChanged(it) }

        btnLogin.onClick {
            if (editTextEmail.text.isBlank() || editTextPassword.text.isBlank()) {
                Toast.makeText(applicationContext, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
            } else if (!isEmailValid(editTextEmail.text.toString())) {
                Toast.makeText(applicationContext, "รูปแบบของอีเมลไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
            } else if (!isPasswordValid(editTextPassword.text.toString())) {
                Toast.makeText(applicationContext, "รหัสผ่านควรมีมากกว่า 6 ตัวอักษร", Toast.LENGTH_SHORT).show()
            } else {
                presenter.onLoginTapped()
            }
        }
        btnRegister.onClick {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onLoginSuccess() {
        presenter.getUserProfile()
    }

    override fun getUserSuccess(user: User) {
        storedValue.saveObject(Constant().PREFS_USER_KEY, user)

        // Check user's type
        when {
            user.type == 9 -> { // Admin
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
            user.type == 1 -> { // Owner
                startActivity(Intent(this, OwnerActivity::class.java))
                finish()
            }
            else -> { // User
                startActivity(Intent(this, UserActivity::class.java))
                finish()
            }
        }
    }

    override fun createAdminSuccess() {
        Toast.makeText(applicationContext, "Admin created!", Toast.LENGTH_SHORT).show()
    }

    override fun showError(title: String, message: String, button: String) {
        showGeneralError(this, title, message, button)
    }
}
