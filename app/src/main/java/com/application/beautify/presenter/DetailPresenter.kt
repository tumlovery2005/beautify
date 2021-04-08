package com.application.beautify.presenter

import com.application.beautify.model.Beautify
import com.application.beautify.ui.user.DetailView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface DetailPresenter: BasePresenter<DetailView> {

    fun onAddFav(user: String, beautify: Beautify)

    fun getFav(user: String)
}