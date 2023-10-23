package com.harshit.popcornpick.EntityAndDb;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MovieModelList implements Parcelable{
    private List<MovieModel> list;
    public MovieModelList() {
        list = new ArrayList<>();
    }

    protected MovieModelList(Parcel in) {
        list = in.createTypedArrayList(MovieModel.CREATOR);
    }

    public static final Creator<MovieModelList> CREATOR = new Creator<MovieModelList>() {
        @Override
        public MovieModelList createFromParcel(Parcel in) {
            return new MovieModelList(in);
        }

        @Override
        public MovieModelList[] newArray(int size) {
            return new MovieModelList[size];
        }
    };

    public List<MovieModel> getList() {
        return list;
    }

    public void setList(List<MovieModel> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedList(list);
    }
}
