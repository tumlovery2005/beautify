package com.application.beautify.ui.owner.manage

import com.application.beautify.model.Beautify

/**
 * Created by GuGolf on 21/4/2019 AD.
 */
interface OwnerManageView {

    fun onGetStore(beautify: Beautify)

    fun onUpdateSuccess()

    fun onError()
}