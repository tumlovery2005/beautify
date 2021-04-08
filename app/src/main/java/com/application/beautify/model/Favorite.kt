package com.application.beautify.model

/**
 * Created by GuGolf on 10/3/2019 AD.
 */
class FavoriteResponse  {

    var uid: String = ""
    var user: String = ""
    lateinit var beautify: Beautify

    constructor() {}

    constructor(
                uid: String = "",
                user: String = "",
                beautify: Beautify
    ) {
        this.uid = uid
        this.user = user
        this.beautify = beautify
    }

    fun isValid() = uid.isNotBlank() && user.isNotBlank()

    fun mapToFav() = Favorite(uid, user, beautify)
}

data class Favorite (
    var uid: String = "",
    var user: String = "",
    var beautify: Beautify
)