package com.application.beautify.ui.owner.promotion

import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion

/**
 * Created by GuGolf on 21/4/2019 AD.
 */
interface OwnerPromotionView {

    fun onGetPromotionSuccess(promotion: Promotion)

    fun onGetBeautify(beautify: Beautify)

    fun onAddPromotionSuccess()

    fun onDeleteSuccess()

    fun onError()
}