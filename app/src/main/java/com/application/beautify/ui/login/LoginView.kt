package com.application.beautify.ui.login

import com.application.beautify.model.User

interface LoginView {

    fun onLoginSuccess()

    fun getUserSuccess(user: User)

    fun createAdminSuccess()

    fun showError(title: String, message: String, button: String)
}