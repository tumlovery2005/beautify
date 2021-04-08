package com.application.beautify.presenter

import com.application.beautify.ui.login.LoginView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface LoginPresenter: BasePresenter<LoginView> {

    fun onLoginTapped()

    fun onCreateAdmin()

    fun getUserProfile()

    fun onUsernameChanged(text: String)

    fun onPasswordChanged(text: String)
}