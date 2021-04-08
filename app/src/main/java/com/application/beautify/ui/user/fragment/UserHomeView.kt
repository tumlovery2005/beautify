package com.application.beautify.ui.user.fragment

import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion

/**
 * Created by GuGolf on 22/4/2019 AD.
 */
interface UserHomeView {

    fun onGetSuggestBeautifySuccess(beautify: Beautify)

    fun onGetPromotionsSuccess(promotion: Promotion)

    fun onGetDataError()
}