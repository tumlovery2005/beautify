package com.application.beautify.ui.admin.fragment

import com.application.beautify.model.Beautify
import com.application.beautify.model.User

/**
 * Created by GuGolf on 19/4/2019 AD.
 */
interface BeautifyView {

    fun getBeautifySuccess(beautify: Beautify)

    fun addBeautifySuccess()

    fun getUserSuccess(user: User)

    fun showError(message: String)
}