package com.application.beautify.model

/**
 * Created by GuGolf on 10/3/2019 AD.
 */
data class BeautifyResponse (
    var uid: String = "",
    var name: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var tel: String = "",
    var address: String = "",
    var working: ArrayList<WorkingTime> = arrayListOf(),
    var services: ArrayList<Services> = arrayListOf(),
    var detail: String = "",
    var images: ArrayList<String> = arrayListOf(),
    var rating: String = ""
) {
    fun isValid() = uid.isNotBlank() && name.isNotBlank()

    fun mapToBeautify() = Beautify(uid, name, latitude, longitude, tel, address, working, services, detail, images, rating)
}

data class Beautify (
    var uid: String = "",
    var name: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var tel: String = "",
    var address: String = "",
    var working: ArrayList<WorkingTime> = arrayListOf(),
    var services: ArrayList<Services> = arrayListOf(),
    var detail: String = "",
    var images: ArrayList<String> = arrayListOf(),
    var rating: String = ""
) {
    fun mapToFull() = FullBeautify(uid, name, latitude, longitude, tel, address, working, services, detail, images, rating)
}

data class WorkingTime (
    var day: String = "",
    var open: String = "",
    var close: String = ""
)

data class Services (
    var name: String = "",
    var price: Int = 0
)

data class FullBeautify (
    var uid: String = "",
    var name: String = "",
    var latitude: String = "",
    var longitude: String = "",
    var tel: String = "",
    var address: String = "",
    var working: ArrayList<WorkingTime> = arrayListOf(),
    var services: ArrayList<Services> = arrayListOf(),
    var detail: String = "",
    var images: ArrayList<String>? = arrayListOf(),
    var rating: String = "",
    var distance: String = ""
) {
    fun mapToBeautify() = Beautify(uid, name, latitude, longitude, tel, address, working, services, detail)
}