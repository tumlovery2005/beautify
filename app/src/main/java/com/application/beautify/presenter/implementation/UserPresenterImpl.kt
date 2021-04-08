package com.application.beautify.presenter.implementation

import com.application.beautify.common.arePasswordsSame
import com.application.beautify.common.isEmailValid
import com.application.beautify.common.isPasswordValid
import com.application.beautify.common.isUsernameValid
import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.User
import com.application.beautify.presenter.UserPresenter
import com.application.beautify.ui.register.UserView
import javax.inject.Inject

class UserPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : UserPresenter {

    private lateinit var view: UserView

    private var username = ""
    private var email = ""
    private var password = ""
    private var confirmPassword = ""
    private var tel = ""

    override fun setView(view: UserView) {
        this.view = view
    }

    override fun onRegisterTap(type: Boolean, store: String, document: String) {
        if (isUsernameValid(username)) {
            if (isEmailValid(email)) {
                if (isPasswordValid(password)) {
                    if (arePasswordsSame(password, confirmPassword)) {
                        val user = if (type) {
                            User("", username, email, password, tel, 1, store, document)
                        } else {
                            User("", username, email, password, tel, 0, "", "")
                        }
                        authentication.register(email, password, username) { isRegistered ->
                            if (isRegistered) {
                                databaseInterface.addUser(user) {
                                    if (it) {
                                        view.onRegisterSuccess()
                                    } else {
                                        view.showError("เกิดข้อผิดพลาด", "มีการทำงานบางอย่างไม่ถูกต้อง", "ตกลง")
                                    }
                                }
                            } else {
                                view.showError("เกิดข้อผิดพลาด", "มีการทำงานบางอย่างไม่ถูกต้อง", "ตกลง")
                            }
                        }
                    } else {
                        view.showError("เกิดข้อผิดพลาด", "รหัสผ่านไม่ตรงกัน", "ตกลง")
                    }
                } else {
                    view.showError("เกิดข้อผิดพลาด", "กรุณากรอกรหัสผ่านมากกว่า 6 ตัวอักษร", "ตกลง")
                }
            } else {
                view.showError("เกิดข้อผิดพลาด", "รูปแบบของอีเมลไม่ถูกต้อง", "ตกลง")
            }
        } else {
            view.showError("เกิดข้อผิดพลาด", "กรุณากรอกชื่อผู้ใช้งานมากกว่า 6 ตัวอักษร", "ตกลง")
        }
    }

    override fun onCheckStore(store: String) {
        databaseInterface.hasBeautify(store) {
            view.onCheckStoreResult(it)
        }
    }

    override fun onUsernameChanged(text: String) {
        username = text
    }

    override fun onEmailChanged(text: String) {
        email = text
    }

    override fun onPasswordChanged(text: String) {
        password = text
    }

    override fun onConfirmPasswordChanged(text: String) {
        confirmPassword = text
    }

    override fun onTelChanged(text: String) {
        tel = text
    }

    override fun getBeautify() {
        databaseInterface.listenToBeautify {
            view.onGetBeautify(it)
        }
    }
}

