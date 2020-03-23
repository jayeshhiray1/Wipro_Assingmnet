package com.wipro.codingexcercise.model

import android.os.Parcelable
import android.text.TextUtils
import android.util.Log
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Row(
    val description: String,
    val imageHref: String,
    val title: String
): Parcelable{

    fun getImageUrl():String{
        if(!TextUtils.isEmpty(imageHref)) {
            return "https"+imageHref.substring(4, imageHref.length)
        }
        return ""
    }
}