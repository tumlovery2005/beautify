package com.application.beautify.firebase.database

import android.util.Log
import com.application.beautify.model.*
import com.google.firebase.database.*
import javax.inject.Inject

private const val KEY_UESR = "users"
private const val KEY_BEAUTIFY = "beautify"
private const val KEY_PROMOTION = "promotions"
private const val KEY_BOOKING = "booking"
private const val KEY_FAVORITE = "favorite"

class FirebaseDatabaseManager @Inject constructor(private val database: FirebaseDatabase) : FirebaseDatabaseInterface {

    //////////////////////////////////////////   PROFILE    //////////////////////////////////////////

    override fun hasProfile(email: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_UESR)
            .orderByChild("email")
            .equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(snapshotError: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val count = snapshot.childrenCount
                    if (count > 0) {
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                }

            })
    }

    override fun getProfile(email: String, onResult: (User) -> Unit) {
        database.reference
            .child(KEY_UESR)
            .orderByChild("email")
            .equalTo(email)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(UserResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToUser())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun addProfile(user: User, onResult: (Boolean) -> Unit) {
        val newUserReference = database.reference.child(KEY_UESR).push()
        val uid = newUserReference.key!!
        val newUser = user.copy(uid = uid)

        newUserReference.setValue(newUser)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    //////////////////////////////////////////   BEAUTIFY    //////////////////////////////////////////

    override fun listenToBeautify(onResult: (Beautify) -> Unit) {
        database.reference
            .child(KEY_BEAUTIFY)
            .orderByKey()
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(BeautifyResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToBeautify())
                        }
                    }
                }
            })
    }

    override fun getBeautify(uid: String, onResult: (Beautify) -> Unit) {
        database.reference
            .child(KEY_BEAUTIFY)
            .orderByChild("uid")
            .equalTo(uid)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(BeautifyResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToBeautify())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun getMaxRatingBeautify(onResult: (Beautify) -> Unit) {
        Log.d("FirebaseDatabase", "Beatify get max rating")
        database.reference
            .child(KEY_BEAUTIFY)
            .orderByChild("rating")
            .limitToLast(1)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("FirebaseDatabase", "Beatify get max rating " + snapshot.childrenCount)
                    for (childSnapshot in snapshot.children) {
                        childSnapshot.getValue(BeautifyResponse::class.java).run {
                            if (this!!.isValid()) {
                                onResult(mapToBeautify())
                            }
                        }
                    }
                }

            })
    }

    override fun hasBeautify(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_BEAUTIFY)
            .orderByChild("uid")
            .equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(snapshotError: DatabaseError) {

                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val count = snapshot.childrenCount
                    if (count > 0) {
                        onResult(true)
                    } else {
                        onResult(false)
                    }
                }

            })
    }

    override fun addBeautify(beautify: Beautify, onResult: (Boolean) -> Unit) {
        val newBeautifyReference = database.reference.child(KEY_BEAUTIFY).push()
        val uid = newBeautifyReference.key!!
        val newBeautify = beautify.copy(uid = uid)

        newBeautifyReference.setValue(newBeautify)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun updateBeautify(beautify: Beautify, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_BEAUTIFY)
            .child(beautify.uid)
            .setValue(beautify)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun updateLocationBeautify(uid: String, lat: String, lng: String, onResult: (Boolean) -> Unit) {
        val childUpdate = HashMap<String, Any>()
        childUpdate["latitude"] = lat
        childUpdate["longitude"] = lng

        database.reference
            .child(KEY_BEAUTIFY)
            .child(uid)
            .updateChildren(childUpdate)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun removeBeautify(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_BEAUTIFY)
            .child(uid)
            .removeValue()
            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
    }

    //////////////////////////////////////////   USER    //////////////////////////////////////////

    override fun listenToUser(onResult: (User) -> Unit) {
        database.reference
            .child(KEY_UESR)
            .orderByKey()
            .addChildEventListener(object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(UserResponse::class.java).run {
                        if (this!!.isValid() && this.type != 9) {
                            onResult(mapToUser())
                        }
                    }
                }
            })
    }

    override fun addUser(user: User, onResult: (Boolean) -> Unit) {
        val newUserReference = database.reference.child(KEY_UESR).push()
        val uid = newUserReference.key!!
        val newUser = user.copy(uid = uid)

        newUserReference.setValue(newUser)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun updateUser(user: User, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_UESR)
            .child(user.uid)
            .setValue(user)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun removeUser(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_UESR)
            .child(uid)
            .removeValue()
            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
    }

    //////////////////////////////////////////   PROMOTION    //////////////////////////////////////////

    override fun listenToPromotion(storeID: String, onResult: (Promotion) -> Unit) {
        database.reference
            .child(KEY_PROMOTION)
            .orderByChild("store")
            .equalTo(storeID)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(PromotionResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToPromotion())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun getAllPromotion(onResult: (Promotion) -> Unit) {
        Log.d("FirebaseDatabase", "All promotion")
        database.reference
            .child(KEY_PROMOTION)
            .orderByKey()
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    Log.d("FirebaseDatabase", "All promotion count = " + snapshot.childrenCount)
                    snapshot.getValue(PromotionResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToPromotion())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun addPromotion(promotion: Promotion, onResult: (Boolean) -> Unit) {
        val newPromotionReference = database.reference.child(KEY_PROMOTION).push()
        val uid = newPromotionReference.key!!
        val newPromotion = promotion.copy(uid = uid)

        newPromotionReference.setValue(newPromotion)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun updatePromotion(promotion: Promotion, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_PROMOTION)
            .child(promotion.uid)
            .setValue(promotion)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun removePromotion(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_PROMOTION)
            .child(uid)
            .removeValue()
            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
    }

    //////////////////////////////////////////   BOOKING    //////////////////////////////////////////

    override fun listenToBooking(storeID: String, onResult: (Booking) -> Unit) {
        database.reference
            .child(KEY_BOOKING)
            .orderByChild("store")
            .equalTo(storeID)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(BookingResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToBooking())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun addBooking(booking: Booking, onResult: (Boolean) -> Unit) {
        val newBookingReference = database.reference.child(KEY_BOOKING).push()
        val uid = newBookingReference.key!!
        val newBooking = booking.copy(uid = uid)

        newBookingReference.setValue(newBooking)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun updateBooking(booking: Booking, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_BOOKING)
            .child(booking.uid)
            .setValue(booking)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun removeBooking(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_BOOKING)
            .child(uid)
            .removeValue()
            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
    }

    override fun listenToFavorite(userID: String, onResult: (Favorite) -> Unit) {
        database.reference
            .child(KEY_FAVORITE)
            .orderByChild("user")
            .equalTo(userID)
            .addChildEventListener(object: ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
                    snapshot.getValue(FavoriteResponse::class.java).run {
                        if (this!!.isValid()) {
                            onResult(mapToFav())
                        }
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }

    override fun addFavorite(favorite: Favorite, onResult: (Boolean) -> Unit) {
        val newFavReference = database.reference.child(KEY_FAVORITE).push()
        val uid = newFavReference.key!!
        val newFav = favorite.copy(uid = uid)

        newFavReference.setValue(newFav)
            .addOnCompleteListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    override fun removeFavorite(uid: String, onResult: (Boolean) -> Unit) {
        database.reference
            .child(KEY_FAVORITE)
            .child(uid)
            .removeValue()
            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
    }

//    override fun listenToBusLine(onResult: (String) -> Unit) {
//        database.reference
//            .child(KEY_BUS)
//            .orderByKey()
//            .addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(BusResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onResult(mapToBus().bus_line)
//                        }
//                    }
//                }
//            })
//    }
//
//    override fun listenToRoute(busLine: String, onResult: (Route) -> Unit) {
//        database.reference
//            .child(KEY_ROUTE)
//            .orderByChild("bus_line")
//            .equalTo(busLine)
//            .addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(RouteResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onResult(mapToRoute())
//                        }
//                    }
//                }
//            })
//    }
//
//    override fun getTimetable(uid: String, onResult: (TimetableResponse) -> Unit) {
//        database.reference
//            .child(KEY_TIMETABLE)
//            .orderByChild("uid")
//            .equalTo(uid)
//            .addChildEventListener(object: ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(TimetableResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onResult(this)
//                        }
//                    }
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//            })
//    }
//
//    override fun addTimetable(timetable: TimetableResponse, onResult: (String?) -> Unit) {
//        val newTimetableReference = database.reference.child(KEY_TIMETABLE).push()
//        val uid = newTimetableReference.key!!
//        val newTimetable = timetable.copy(uid = uid)
//
//        newTimetableReference.setValue(newTimetable)
//            .addOnCompleteListener { onResult(newTimetable.uid) }
//            .addOnFailureListener { onResult(null) }
//    }
//
//    override fun updateTimetable(timetable: Timetable, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_TIMETABLE)
//            .child(timetable.uid)
//            .child("waypoints")
//            .setValue(timetable.waypoints)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun removeTimetable(uid: String, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_TIMETABLE)
//            .child(uid)
//            .removeValue()
//            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
//    }
//
//    override fun addDrive(drive: Drive, onResult: (String?) -> Unit) {
//        val newDriveReference = database.reference.child(KEY_DRIVE).push()
//        val uid = newDriveReference.key!!
//        val newDrive = drive.copy(uid = uid)
//
//        newDriveReference.setValue(newDrive)
//            .addOnCompleteListener { onResult(newDrive.uid) }
//            .addOnFailureListener { onResult(null) }
//    }
//
//    override fun updateDrive(drive: Drive, onResult: (Boolean) -> Unit) {
//        val childUpdate = HashMap<String, Any>()
//        childUpdate["lat"] = drive.lat
//        childUpdate["lng"] = drive.lng
//
//        database.reference
//            .child(KEY_DRIVE)
//            .child(drive.uid)
//            .updateChildren(childUpdate)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun updateDriveRoutes(uid: String, routes: ArrayList<CheckRouteTime>, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_DRIVE)
//            .child(uid)
//            .child("routes")
//            .setValue(routes)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun removeDrive(uid: String, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_DRIVE)
//            .child(uid)
//            .removeValue()
//            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
//    }
//
//    override fun addReport(report: Report, onResult: (Boolean) -> Unit) {
//        val newReportReference = database.reference.child(KEY_REPORT).push()
//        val uid = newReportReference.key!!
//        val newReport = report.copy(uid = uid)
//
//        newReportReference.setValue(newReport)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }

//    override fun createUser(
//        id: String,
//        username: String,
//        image: String,
//        email: String,
//        timestamp: String,
//        onResult: (User) -> Unit
//    ) {
//        val user = User(id, username, image, email, timestamp)
//
//        database.reference.child(KEY_USER).child(id).setValue(user).addOnCompleteListener { getProfile(id, onResult) }
//    }

//    override fun updateProfile(id: String, username: String, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_USER)
//            .child(id)
//            .child("username")
//            .setValue(username)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }

//    override fun updateProfileWithPicture(id: String, username: String, image: String, onResult: (Boolean) -> Unit) {
//        val childUpdate = HashMap<String, Any>()
//        childUpdate["username"] = username
//        childUpdate["image"] = image
//        database.reference
//            .child(KEY_USER)
//            .child(id)
//            .updateChildren(childUpdate)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }

//    override fun addNewPost(post: Post, onResult: (String) -> Unit) {
//        val newPostReference = database.reference.child(KEY_POST).push()
//        val newPost = post.copy(id = newPostReference.key!!)
//
//        newPostReference.setValue(newPost).addOnCompleteListener { onResult(newPost.id) }
//    }

//    override fun getUserCount(id: String, onResult: (Int) -> Unit) {
//        database.reference
//            .child(KEY_USER)
//            .orderByChild("id")
//            .equalTo(id)
//            .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(snapshotError: DatabaseError) {
//
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val count = snapshot.childrenCount
//                    onResult(count.toInt())
//                }
//
//            })
//    }

//    override fun getPostCount(onResult: (Int) -> Unit) {
//        database.reference
//            .child(KEY_POST)
//            .orderByKey()
//            .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(snapshotError: DatabaseError) {
//
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val count = snapshot.childrenCount
//                    onResult(count.toInt())
//                }
//
//            })
//    }

//    override fun updatePost(id: String, status: String, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_POST)
//            .child(id)
//            .child("detail")
//            .setValue(status).addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
//    }

//    override fun deletePost(id: String, onResult: (Boolean) -> Unit) {
//        database.reference
//            .child(KEY_POST)
//            .child(id)
//            .removeValue()
//            .addOnCompleteListener { onResult(it.isComplete && it.isSuccessful) }
//    }

//    override fun listenToPosts(onPostAdded: (Post) -> Unit) {
//        database.reference.child(KEY_POST)
//            .orderByKey()
//            .addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(PostResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onPostAdded(mapToPost())
//                        }
//                    }
//                }
//            })
//    }

//    override fun listenToUsers(id: String, onPostAdded: (Post) -> Unit) {
//        database.reference
//            .child(KEY_POST)
//            .orderByChild("username")
//            .equalTo(id)
//            .addChildEventListener(object: ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(PostResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onPostAdded(mapToPost())
//                        }
//                    }
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//            })
//    }

//    override fun addRanking(ranking: Ranking, onResult: (Boolean) -> Unit) {
//        val newRankingReference = database.reference.child(KEY_RANKING).push()
//        val newRanking = ranking.copy(id = newRankingReference.key!!)
//
//        newRankingReference.setValue(newRanking)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun addRank(rank: Rank, onResult: (Boolean) -> Unit) {
//        val newRankReference = database.reference.child(KEY_RANK).push()
//        val newRank = rank.copy(id = newRankReference.key!!)
//
//        newRankReference.setValue(newRank)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun getRank(district: String, subdistrict: String, onResult: (Rank) -> Unit) {
//        database.reference
//            .child(KEY_RANK)
//            .orderByChild("subdistrict")
//            .equalTo(subdistrict)
//            .addChildEventListener(object: ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(RankResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onResult(mapToRank())
//                        }
//                    }
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//            })
//    }
//
//    override fun getRankCount(subdistrict: String, onResult: (Int) -> Unit) {
//        database.reference
//            .child(KEY_RANK)
//            .orderByChild("subdistrict")
//            .equalTo(subdistrict)
//            .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(snapshotError: DatabaseError) {
//
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val count = snapshot.childrenCount
//                    onResult(count.toInt())
//                }
//            })
//    }
//
//    override fun updateRank(id: String, sayno: Int, reuse: Int, total: Int, onResult: (Boolean) -> Unit) {
//        val childUpdate = HashMap<String, Any>()
//        childUpdate["sayno"] = sayno
//        childUpdate["reuse"] = reuse
//        childUpdate["total"] = total
//
//        database.reference
//            .child(KEY_RANK)
//            .child(id)
//            .updateChildren(childUpdate)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun listenToRank(onRankAdded: (Rank) -> Unit) {
//        database.reference
//            .child(KEY_RANK)
//            .orderByChild("total")
//            .addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(RankResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onRankAdded(mapToRank())
//                        }
//                    }
//                }
//            })
//    }
//
//    override fun addDistrict(district: District, onResult: (Boolean) -> Unit) {
//        val newDistrictReference = database.reference.child(KEY_DISTRICT).push()
//        val newDistrict = district.copy(id = newDistrictReference.key!!)
//
//        newDistrictReference.setValue(newDistrict)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun getDistrictCount(district: String, onResult: (Int) -> Unit) {
//        database.reference
//            .child(KEY_DISTRICT)
//            .orderByChild("district")
//            .equalTo(district)
//            .addListenerForSingleValueEvent(object: ValueEventListener {
//                override fun onCancelled(snapshotError: DatabaseError) {
//
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val count = snapshot.childrenCount
//                    onResult(count.toInt())
//                }
//            })
//    }
//
//    override fun getDistrict(district: String, onResult: (District) -> Unit) {
//        database.reference
//            .child(KEY_DISTRICT)
//            .orderByChild("district")
//            .equalTo(district)
//            .addChildEventListener(object: ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(DistrictResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onResult(mapToDistrict())
//                        }
//                    }
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//            })
//    }
//
//    override fun updateDistrict(id: String, sayno: Int, reuse: Int, total: Int, onResult: (Boolean) -> Unit) {
//        val childUpdate = HashMap<String, Any>()
//        childUpdate["sayno"] = sayno
//        childUpdate["reuse"] = reuse
//        childUpdate["total"] = total
//
//        database.reference
//            .child(KEY_DISTRICT)
//            .child(id)
//            .updateChildren(childUpdate)
//            .addOnCompleteListener { onResult(true) }
//            .addOnFailureListener { onResult(false) }
//    }
//
//    override fun listenToDistrict(onDistrictAdded: (District) -> Unit) {
//        database.reference
//            .child(KEY_DISTRICT)
//            .orderByChild("-total")
//            .addChildEventListener(object : ChildEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//
//                }
//
//                override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
//                    snapshot.getValue(DistrictResponse::class.java).run {
//                        if (this!!.isValid()) {
//                            onDistrictAdded(mapToDistrict())
//                        }
//                    }
//                }
//            })
//    }
}