package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.model.Favorite
import com.application.beautify.presenter.DetailPresenter
import com.application.beautify.ui.user.DetailView
import javax.inject.Inject

class DetailPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : DetailPresenter {

    private lateinit var view: DetailView

    override fun setView(view: DetailView) {
        this.view = view
    }

    override fun onAddFav(user: String, beautify: Beautify) {
        val fav = Favorite("", user, beautify)
        databaseInterface.addFavorite(fav) {
            if (it) {
                view.addFavoriteSuccess()
            } else {
                view.onError()
            }
        }
    }

    override fun getFav(user: String) {
        databaseInterface.listenToFavorite(user) {
            view.onGetFavorite(it)
        }
    }
}

