package com.application.beautify.ui.owner.queue

import com.application.beautify.model.Booking

/**
 * Created by GuGolf on 21/4/2019 AD.
 */
interface OwnerQueueView {

    fun onGetQueueSuccess(booking: Booking)

    fun onDeleteSuccess()

    fun onError()
}