package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.presenter.UserSearchPresenter
import com.application.beautify.ui.user.fragment.UserSearchView
import javax.inject.Inject

class UserSearchPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : UserSearchPresenter {

    private lateinit var view: UserSearchView

    override fun setView(view: UserSearchView) {
        this.view = view
    }

    override fun onGetBeautify() {
        databaseInterface.listenToBeautify {
            view.getBeautifySuccess(it)
        }
    }
}

