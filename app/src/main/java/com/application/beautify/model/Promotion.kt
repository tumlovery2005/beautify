package com.application.beautify.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by GuGolf on 10/3/2019 AD.
 */
data class PromotionResponse (
    var uid: String = "",
    var store: String = "",
    var storeName: String = "",
    var detail: String = "",
    var image: String = "",
    var timestamp: String = ""
) {
    fun isValid() = uid.isNotBlank() && store.isNotBlank() && storeName.isNotBlank() && detail.isNotBlank() && image.isNotBlank()

    fun mapToPromotion() = Promotion(uid, store, storeName, detail, image, timestamp)
}

data class Promotion (
    var uid: String = "",
    var store: String = "",
    var storeName: String = "",
    var detail: String = "",
    var image: String = "",
    var timestamp: String = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(store)
        parcel.writeString(storeName)
        parcel.writeString(detail)
        parcel.writeString(image)
        parcel.writeString(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Promotion> {
        override fun createFromParcel(parcel: Parcel): Promotion {
            return Promotion(parcel)
        }

        override fun newArray(size: Int): Array<Promotion?> {
            return arrayOfNulls(size)
        }
    }
}