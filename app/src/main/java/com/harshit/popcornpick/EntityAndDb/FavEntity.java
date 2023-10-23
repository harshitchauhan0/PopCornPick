package com.harshit.popcornpick.EntityAndDb;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Crediantials.FAVOURITE_TABLE)
public class FavEntity {

    @ColumnInfo(name ="id")
    @PrimaryKey()
    private int movie_id;

//    @ColumnInfo(name = "date")
//    private String date;

    public FavEntity(){

    }

    public FavEntity(int movie_id){
        this.movie_id = movie_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
}
