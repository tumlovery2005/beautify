package com.application.beautify.presenter

import com.application.beautify.ui.user.fragment.UserHomeView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface UserHomePresenter: BasePresenter<UserHomeView> {

    fun onGetSuggestBeautify()

    fun onGetPromotion()
}