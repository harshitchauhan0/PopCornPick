package com.harshit.popcornpick.EntityAndDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavDao {

    @Insert
    void insert(FavEntity favEntity);

    @Delete
    void delete(FavEntity favEntity);


    @Query("SELECT * FROM MY_TABLE")
    List<FavEntity> getAll();
}
