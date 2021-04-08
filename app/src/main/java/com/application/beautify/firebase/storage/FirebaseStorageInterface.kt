package com.application.beautify.firebase.storage

import android.net.Uri

interface FirebaseStorageInterface {

    fun uploadImage(filePath: Uri, onResult: (String) -> Unit)

    fun getUploadImage(name: String, onResult: (Uri) -> Unit)
}