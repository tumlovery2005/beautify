package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.model.User
import com.application.beautify.presenter.BeautifyPresenter
import com.application.beautify.presenter.OwnerManagePresenter
import com.application.beautify.ui.admin.fragment.BeautifyView
import com.application.beautify.ui.owner.manage.OwnerManageView
import javax.inject.Inject

class OwnerManagePresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : OwnerManagePresenter {

    private lateinit var view: OwnerManageView

    override fun setView(view: OwnerManageView) {
        this.view = view
    }

    override fun onGetBeautify(uid: String) {
        databaseInterface.getBeautify(uid) {
            view.onGetStore(it)
        }
    }

    override fun onUpdateBeautify(beautify: Beautify) {
        databaseInterface.updateBeautify(beautify) {
            if (it) {
                view.onUpdateSuccess()
            } else {
                view.onError()
            }
        }
    }
}

