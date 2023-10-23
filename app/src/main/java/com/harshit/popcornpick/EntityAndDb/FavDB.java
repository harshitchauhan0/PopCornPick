package com.harshit.popcornpick.EntityAndDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavEntity.class},version = 1)
public abstract class FavDB extends RoomDatabase {

    public abstract FavDao getFavDao();

    private static FavDB instance;

    public static synchronized FavDB getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),FavDB.class,Crediantials.FAVOURITE_TABLE)
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }

}
