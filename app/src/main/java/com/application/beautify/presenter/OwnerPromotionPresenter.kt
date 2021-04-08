package com.application.beautify.presenter

import com.application.beautify.model.Promotion
import com.application.beautify.ui.owner.promotion.OwnerPromotionView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface OwnerPromotionPresenter: BasePresenter<OwnerPromotionView> {

    fun onGetPromotion(uid: String)

    fun onSavePromotion(promotion: Promotion)

    fun onGetBeautify()

    fun onDeletePromotion(uid: String)
}