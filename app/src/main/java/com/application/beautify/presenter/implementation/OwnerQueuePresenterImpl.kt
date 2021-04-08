package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.model.User
import com.application.beautify.presenter.BeautifyPresenter
import com.application.beautify.presenter.OwnerManagePresenter
import com.application.beautify.presenter.OwnerQueuePresenter
import com.application.beautify.ui.admin.fragment.BeautifyView
import com.application.beautify.ui.owner.manage.OwnerManageView
import com.application.beautify.ui.owner.queue.OwnerQueueView
import javax.inject.Inject

class OwnerQueuePresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : OwnerQueuePresenter {

    private lateinit var view: OwnerQueueView

    override fun setView(view: OwnerQueueView) {
        this.view = view
    }

    override fun onGetQueue(uid: String) {
        databaseInterface.listenToBooking(uid) {
            view.onGetQueueSuccess(it)
        }
    }

    override fun onDeleteQueue(uid: String) {
        databaseInterface.removeBooking(uid) {
            if (it) {
                view.onDeleteSuccess()
            } else {
                view.onError()
            }
        }
    }
}

