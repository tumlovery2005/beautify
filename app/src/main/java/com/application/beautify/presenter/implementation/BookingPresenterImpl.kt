package com.application.beautify.presenter.implementation

import com.application.beautify.firebase.authentication.FirebaseAuthenticationInterface
import com.application.beautify.firebase.database.FirebaseDatabaseInterface
import com.application.beautify.model.Beautify
import com.application.beautify.model.Booking
import com.application.beautify.model.Favorite
import com.application.beautify.presenter.BookingPresenter
import com.application.beautify.presenter.DetailPresenter
import com.application.beautify.ui.user.BookingView
import com.application.beautify.ui.user.DetailView
import javax.inject.Inject

class BookingPresenterImpl @Inject constructor(
    private val authentication: FirebaseAuthenticationInterface,
    private val databaseInterface: FirebaseDatabaseInterface
) : BookingPresenter {

    private lateinit var view: BookingView

    override fun setView(view: BookingView) {
        this.view = view
    }

    override fun onAddBooking(booking: Booking) {
        databaseInterface.addBooking(booking) {
            if (it) {
                view.addBookingSuccess()
            } else {
                view.onError()
            }
        }
    }
}

