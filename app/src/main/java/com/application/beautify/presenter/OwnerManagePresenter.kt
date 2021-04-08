package com.application.beautify.presenter

import com.application.beautify.model.Beautify
import com.application.beautify.ui.owner.manage.OwnerManageView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface OwnerManagePresenter: BasePresenter<OwnerManageView> {

    fun onGetBeautify(uid: String)

    fun onUpdateBeautify(beautify: Beautify)
}