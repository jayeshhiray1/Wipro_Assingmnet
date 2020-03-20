package com.wipro.codingexcercise.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * @author jayeshhiray
 * THis is the main pojo class we get from the API call
 */
class FactsDto : Parcelable {
    var title: String? = null
    var rows: ArrayList<Rows?>? = null

    constructor() {}
    private constructor(`in`: Parcel) {
        title = `in`.readString()
        rows = ArrayList()
        `in`.readList(rows, Rows::class.java.classLoader)
    }

    override fun toString(): String {
        return "ClassPojo [title = $title, rows = $rows]"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeList(rows)
    }

    companion object {
        val CREATOR: Parcelable.Creator<FactsDto?> = object : Parcelable.Creator<FactsDto?> {
            override fun createFromParcel(source: Parcel): FactsDto? {
                return FactsDto(source)
            }

            override fun newArray(size: Int): Array<FactsDto?> {
                return arrayOfNulls(size)
            }
        }
    }
}