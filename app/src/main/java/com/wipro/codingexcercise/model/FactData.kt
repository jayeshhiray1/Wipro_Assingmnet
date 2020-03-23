package com.wipro.codingexcercise.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FactData(
    val rows: List<Row>,
    val title: String
): Parcelable

