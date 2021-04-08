package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.model.Promotion
import com.application.beautify.model.User
import com.application.beautify.presenter.BeautifyPresenter
import com.application.beautify.presenter.OwnerManagePresenter
import com.application.beautify.presenter.OwnerPromotionPresenter
import com.application.beautify.presenter.OwnerQueuePresenter
import com.application.beautify.ui.admin.fragment.BeautifyView
import com.application.beautify.ui.owner.manage.OwnerManageView
import com.application.beautify.ui.owner.promotion.OwnerPromotionView
import com.application.beautify.ui.owner.queue.OwnerQueueView
import javax.inject.Inject

class OwnerPromotionPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : OwnerPromotionPresenter {

    private lateinit var view: OwnerPromotionView

    override fun setView(view: OwnerPromotionView) {
        this.view = view
    }

    override fun onGetPromotion(uid: String) {
        databaseInterface.listenToPromotion(uid) {
            view.onGetPromotionSuccess(it)
        }
    }

    override fun onSavePromotion(promotion: Promotion) {
        databaseInterface.addPromotion(promotion) {
            if (it) {
                view.onAddPromotionSuccess()
            } else {
                view.onError()
            }
        }
    }

    override fun onGetBeautify() {
        databaseInterface.listenToBeautify {
            view.onGetBeautify(it)
        }
    }

    override fun onDeletePromotion(uid: String) {
        databaseInterface.removePromotion(uid) {
            if (it) {
                view.onDeleteSuccess()
            } else {
                view.onError()
            }
        }
    }
}

