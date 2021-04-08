package com.application.beautify.firebase.database

import com.application.beautify.model.*

interface FirebaseDatabaseInterface {

    // Profile

    fun hasProfile(email: String, onResult: (Boolean) -> Unit)

    fun getProfile(email: String, onResult: (User) -> Unit)

    fun addProfile(user: User, onResult: (Boolean) -> Unit)

    // Beautify

    fun listenToBeautify(onResult: (Beautify) -> Unit)

    fun getBeautify(uid: String, onResult: (Beautify) -> Unit)

    fun getMaxRatingBeautify(onResult: (Beautify) -> Unit)

    fun hasBeautify(uid: String, onResult: (Boolean) -> Unit)

    fun addBeautify(beautify: Beautify, onResult: (Boolean) -> Unit)

    fun updateBeautify(beautify: Beautify, onResult: (Boolean) -> Unit)

    fun updateLocationBeautify(uid: String, lat: String, lng: String, onResult: (Boolean) -> Unit)

    fun removeBeautify(uid: String, onResult: (Boolean) -> Unit)

    // User

    fun listenToUser(onResult: (User) -> Unit)

    fun addUser(user: User, onResult: (Boolean) -> Unit)

    fun updateUser(user: User, onResult: (Boolean) -> Unit)

    fun removeUser(uid: String, onResult: (Boolean) -> Unit)

    // Promotion

    fun listenToPromotion(storeID: String, onResult: (Promotion) -> Unit)

    fun getAllPromotion(onResult: (Promotion) -> Unit)

    fun addPromotion(promotion: Promotion, onResult: (Boolean) -> Unit)

    fun updatePromotion(promotion: Promotion, onResult: (Boolean) -> Unit)

    fun removePromotion(uid: String, onResult: (Boolean) -> Unit)

    // Booking

    fun listenToBooking(storeID: String, onResult: (Booking) -> Unit)

    fun addBooking(booking: Booking, onResult: (Boolean) -> Unit)

    fun updateBooking(booking: Booking, onResult: (Boolean) -> Unit)

    fun removeBooking(uid: String, onResult: (Boolean) -> Unit)

    // Favorite

    fun listenToFavorite(userID: String, onResult: (Favorite) -> Unit)

    fun addFavorite(favorite: Favorite, onResult: (Boolean) -> Unit)

    fun removeFavorite(uid: String, onResult: (Boolean) -> Unit)

//    fun listenToBusLine(onResult: (String) -> Unit)
//
//    fun listenToRoute(busLine: String, onResult: (Route) -> Unit)
//
//    fun getTimetable(uid: String, onResult: (TimetableResponse) -> Unit)
//
//    fun addTimetable(timetable: TimetableResponse, onResult: (String?) -> Unit)
//
//    fun updateTimetable(timetable: Timetable, onResult: (Boolean) -> Unit)
//
//    fun removeTimetable(uid: String, onResult: (Boolean) -> Unit)
//
//    fun addDrive(drive: Drive, onResult: (String?) -> Unit)
//
//    fun updateDrive(drive: Drive, onResult: (Boolean) -> Unit)
//
//    fun updateDriveRoutes(uid: String, routes: ArrayList<CheckRouteTime> ,onResult: (Boolean) -> Unit)
//
//    fun removeDrive(uid: String, onResult: (Boolean) -> Unit)
//
//    fun addReport(report: Report, onResult: (Boolean) -> Unit)

}