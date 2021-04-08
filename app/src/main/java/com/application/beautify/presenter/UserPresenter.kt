package com.application.beautify.presenter

import com.application.beautify.ui.register.UserView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface UserPresenter: BasePresenter<UserView> {

    fun onRegisterTap(type: Boolean, store: String, document: String)

    fun onCheckStore(store: String)

    fun onUsernameChanged(text: String)

    fun onEmailChanged(text: String)

    fun onPasswordChanged(text: String)

    fun onConfirmPasswordChanged(text: String)

    fun onTelChanged(text: String)

    fun getBeautify()
}