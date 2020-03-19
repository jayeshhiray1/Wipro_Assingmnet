package com.wipro.codingexcercise.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * @author jayeshhiray
 * pojo class we showing inside the listy of facts
 */
public class Rows implements Parcelable {
    public static final Creator<Rows> CREATOR = new Creator<Rows>() {
        @Override
        public Rows createFromParcel(Parcel source) {
            return new Rows(source);
        }

        @Override
        public Rows[] newArray(int size) {
            return new Rows[size];
        }
    };
    private String imageHref;
    private String description;
    private String title;

    public Rows(String imageHref, String description, String title) {
        this.imageHref = imageHref;
        this.description = description;
        this.title = title;
    }

    public Rows() {
    }

    protected Rows(Parcel in) {
        this.imageHref = in.readString();
        this.description = in.readString();
        this.title = in.readString();
    }

    public String getImageHref() {
        return imageHref;
    }

    public void setImageHref(String imageHref) {
        this.imageHref = imageHref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return "ClassPojo [imageHref = " + imageHref + ", description = " + description + ", title = " + title + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageHref);
        dest.writeString(this.description);
        dest.writeString(this.title);
    }
}