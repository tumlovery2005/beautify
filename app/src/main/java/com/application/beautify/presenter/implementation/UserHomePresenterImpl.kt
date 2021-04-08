package com.application.beautify.presenter.implementation

import android.util.Log
import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.presenter.UserHomePresenter
import com.application.beautify.ui.user.fragment.UserHomeView
import javax.inject.Inject

class UserHomePresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : UserHomePresenter {

    private lateinit var view: UserHomeView

    override fun setView(view: UserHomeView) {
        this.view = view
    }

    override fun onGetSuggestBeautify() {
        Log.d("UserHomePresenter", "Get max rating beautify")
        databaseInterface.getMaxRatingBeautify {
            view.onGetSuggestBeautifySuccess(it)
        }
    }

    override fun onGetPromotion() {
        Log.d("UserHomePresenter", "Get all promotions")
        databaseInterface.getAllPromotion {
            view.onGetPromotionsSuccess(it)
        }
    }
}

