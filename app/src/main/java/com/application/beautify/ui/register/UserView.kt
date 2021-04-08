package com.application.beautify.ui.register

import com.application.beautify.model.Beautify

interface UserView {

    fun onRegisterSuccess()

    fun onCheckStoreResult(hasStore: Boolean)

    fun onGetBeautify(beautify: Beautify)

    fun showError(title: String, message: String, button: String)
}