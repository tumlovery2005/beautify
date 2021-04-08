package com.application.beautify.firebase.authentication

interface FirebaseAuthenticationInterface {

  fun login(email: String, password: String, onResult: (Boolean) -> Unit)

  fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit)

  fun updateProfile(username: String, onResult: (Boolean) -> Unit)

  fun getUserId(): String

  fun getUserName(): String

  fun getUserEmail(): String

  fun logOut(onResult: () -> Unit)
}