package com.application.beautify.presenter

import com.application.beautify.model.Booking
import com.application.beautify.ui.user.BookingView

/**
 * Created by GuGolf on 24/3/2019 AD.
 */
interface BookingPresenter: BasePresenter<BookingView> {

    fun onAddBooking(booking: Booking)
}