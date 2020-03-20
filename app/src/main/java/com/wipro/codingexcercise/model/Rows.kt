package com.wipro.codingexcercise.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author jayeshhiray
 * pojo class we showing inside the listy of facts
 */
class Rows : Parcelable {
    var imageHref: String? = null
    var description: String? = null
    var title: String? = null

    constructor(imageHref: String?, description: String?, title: String?) {
        this.imageHref = imageHref
        this.description = description
        this.title = title
    }

    constructor() {}
    protected constructor(`in`: Parcel) {
        imageHref = `in`.readString()
        description = `in`.readString()
        title = `in`.readString()
    }

    override fun toString(): String {
        return "ClassPojo [imageHref = $imageHref, description = $description, title = $title]"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(imageHref)
        dest.writeString(description)
        dest.writeString(title)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Rows?> = object : Parcelable.Creator<Rows?> {
            override fun createFromParcel(source: Parcel): Rows? {
                return Rows(source)
            }

            override fun newArray(size: Int): Array<Rows?> {
                return arrayOfNulls(size)
            }
        }
    }
}