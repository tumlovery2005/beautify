package com.application.beautify.presenter.implementation

import android.util.Log
import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.User
import com.application.beautify.presenter.LoginPresenter
import com.application.beautify.ui.login.LoginView
import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : LoginPresenter {

    private lateinit var view: LoginView

    private var email = ""
    private var password = ""

    override fun setView(view: LoginView) {
        this.view = view
    }

    override fun onUsernameChanged(text: String) {
        email = text
    }

    override fun onPasswordChanged(text: String) {
        password = text
    }

    override fun onLoginTapped() {
        databaseInterface.hasProfile(email) { hasUser ->
            Log.d("LoginPresenter", "User has profile -> $hasUser")
            if (hasUser) {
                authentication.login(email, password) {
                    Log.d("LoginPresenter", "User loging on -> $it")
                    if (it) {
                        view.onLoginSuccess()
                    } else {
                        view.showError("เกิดข้อผิดพลาด!", "รหัสผ่านของท่านไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง", "ตกลง")
                    }
                }
            } else {
                view.showError("เกิดข้อผิดพลาด!", "ไม่พบอีเมลนี้ในระบบ กรุณาลองใหม่อีกครั้ง", "ตกลง")
            }
        }
    }

    override fun onCreateAdmin() {
        val user = User("", "admin", "admin@beautify.com", "1q2w3e4r", "", 9)
        authentication.register(user.email, user.password, user.username) { isRegistered ->
            if (isRegistered) {
                databaseInterface.addProfile(user) { isProfileAdded ->
                    if (isProfileAdded) {
                        view.createAdminSuccess()
                    }
                }
            }
        }
    }

    override fun getUserProfile() {
        Log.d("LoginPresenter", "Email: $email")
        databaseInterface.getProfile(email) {
            Log.d("LoginPresenter", "Email: ${it.email}, Name: ${it.username}, UID: ${it.uid}")
            view.getUserSuccess(it)
        }
    }
}

