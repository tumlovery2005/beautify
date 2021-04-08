package com.application.beautify.firebase.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import javax.inject.Inject

private const val KEY_IMAGE = "images/"

class FirebaseStorageManager @Inject constructor(private val storage: FirebaseStorage) : FirebaseStorageInterface {

    override fun uploadImage(filePath: Uri, onResult: (String) -> Unit) {
        val fileName = UUID.randomUUID().toString()
        val ref = storage.reference.child(KEY_IMAGE + fileName)
        val uploadTask = ref.putFile(filePath)
        uploadTask.addOnCompleteListener {
            onResult(fileName)
        }
    }

    override fun getUploadImage(name: String, onResult: (Uri) -> Unit) {
        storage.reference.child(KEY_IMAGE + name)
            .downloadUrl
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResult(it.result!!)
                }
            }
    }

}