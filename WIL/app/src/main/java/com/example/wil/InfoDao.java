package com.example.wil;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface InfoDao {

    @Insert
    void insert(Info note);

    @Delete
    void delete(Info note);

    @Update
    void update(Info note);

    @Query("SELECT * FROM user_info")
    List<Info> getAllData();

    @Query("DELETE FROM user_info WHERE id=1")
    void deleteFirstRow();


}
