package com.application.beautify.model

/**
 * Created by GuGolf on 10/3/2019 AD.
 */
data class BookingResponse (
    var uid: String = "",
    var store: String = "",
    var name: String = "",
    var tel: String = "",
    var date: String = "",
    var time: String = ""
) {
    fun isValid() = uid.isNotBlank() && store.isNotBlank() && name.isNotBlank() && tel.isNotBlank() && date.isNotBlank() && time.isNotBlank()

    fun mapToBooking() = Booking(uid, store, name, tel, date, time)
}

data class Booking (
    var uid: String = "",
    var store: String = "",
    var name: String = "",
    var tel: String = "",
    var date: String = "",
    var time: String = ""
)