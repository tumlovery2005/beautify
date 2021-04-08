package com.application.beautify.presenter

import com.application.beautify.model.Beautify
import com.application.beautify.ui.admin.fragment.BeautifyView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface BeautifyPresenter: BasePresenter<BeautifyView> {

    fun onAddBeautify(beautify: Beautify)

    fun editLocation(beautify: Beautify)

    fun onGetBeautify()

    fun onGetUser()
}