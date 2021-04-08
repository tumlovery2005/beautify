package com.application.beautify.model

/**
 * Created by GuGolf on 10/3/2019 AD.
 */
data class UserResponse (
    var uid: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var tel: String = "",
    var type: Int = 0,
    var store: String = "",
    var document: String = ""
) {
    fun isValid() = uid.isNotBlank() && username.isNotBlank() && email.isNotBlank() && password.isNotBlank()

    fun mapToUser() = User(uid, username, email, password, tel, type, store, document)
}

data class User (
    var uid: String = "",
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var tel: String = "",
    var type: Int = 0,
    var store: String = "",
    var document: String = ""
)