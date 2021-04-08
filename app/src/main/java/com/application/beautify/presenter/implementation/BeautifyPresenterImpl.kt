package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.presenter.BeautifyPresenter
import com.application.beautify.ui.admin.fragment.BeautifyView
import javax.inject.Inject

class BeautifyPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : BeautifyPresenter {

    private lateinit var view: BeautifyView

    override fun setView(view: BeautifyView) {
        this.view = view
    }

    override fun onAddBeautify(beautify: Beautify) {
        databaseInterface.addBeautify(beautify) {
            if (it) {
                view.addBeautifySuccess()
            } else {
                view.showError("เกิดข้อผิดพลาด! กรุณาลองใหม่ภายหลัง")
            }
        }
    }

    override fun editLocation(beautify: Beautify) {
        databaseInterface.updateLocationBeautify(beautify.uid, beautify.latitude, beautify.longitude) {
            if (it) {
                view.addBeautifySuccess()
            } else {
                view.showError("")
            }
        }
    }

    override fun onGetBeautify() {
        databaseInterface.listenToBeautify {
            view.getBeautifySuccess(it)
        }
    }

    override fun onGetUser() {
        databaseInterface.listenToUser {
            view.getUserSuccess(it)
        }
    }
}

