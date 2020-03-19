package com.wipro.codingexcercise.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;


/**
 * @author jayeshhiray
 * THis is the main pojo class we get from the API call
 */
public class FactsDto implements Parcelable {

    public static final Creator<FactsDto> CREATOR = new Creator<FactsDto>() {
        @Override
        public FactsDto createFromParcel(Parcel source) {
            return new FactsDto(source);
        }

        @Override
        public FactsDto[] newArray(int size) {
            return new FactsDto[size];
        }
    };
    private String title;
    private ArrayList<Rows> rows;

    public FactsDto() {
    }

    private FactsDto(Parcel in) {
        this.title = in.readString();
        this.rows = new ArrayList<>();
        in.readList(this.rows, Rows.class.getClassLoader());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Rows> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Rows> rows) {
        this.rows = rows;
    }

    @NonNull
    @Override
    public String toString() {
        return "ClassPojo [title = " + title + ", rows = " + rows + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeList(this.rows);
    }
}