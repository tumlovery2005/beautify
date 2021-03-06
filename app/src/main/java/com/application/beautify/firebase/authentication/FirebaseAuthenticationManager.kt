package com.application.beautify.firebase.authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class FirebaseAuthenticationManager @Inject constructor(private val authentication: FirebaseAuth) :
    FirebaseAuthenticationInterface {

    override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.isComplete && it.isSuccessful)
        }
    }

    override fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit) {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful) {
                authentication.currentUser?.updateProfile(
                    UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(userName)
                        .build()
                )

                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    override fun updateProfile(username: String, onResult: (Boolean) -> Unit) {
        authentication.currentUser?.updateProfile(
            UserProfileChangeRequest
                .Builder()
                .setDisplayName(username)
                .build()
        )!!.addOnCompleteListener {
            if (it.isSuccessful) {
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    override fun getUserId(): String = authentication.currentUser?.uid ?: ""
    override fun getUserName(): String = authentication.currentUser?.displayName ?: ""
    override fun getUserEmail(): String = authentication.currentUser?.email ?: ""

    override fun logOut(onResult: () -> Unit) {
        authentication.signOut()

        onResult()
    }
}