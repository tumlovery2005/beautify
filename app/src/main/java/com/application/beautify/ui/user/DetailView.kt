package com.application.beautify.ui.user

import com.application.beautify.model.Favorite

/**
 * Created by GuGolf on 22/4/2019 AD.
 */
interface DetailView {

    fun addFavoriteSuccess()

    fun onGetFavorite(favorite: Favorite)

    fun onError()
}