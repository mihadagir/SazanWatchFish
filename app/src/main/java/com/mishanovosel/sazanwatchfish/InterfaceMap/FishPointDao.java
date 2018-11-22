package com.mishanovosel.sazanwatchfish.InterfaceMap;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mishanovosel.sazanwatchfish.FishPointRoom;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FishPointDao {

    @Query("SELECT * FROM fishPointRoom")
    Flowable<List<FishPointRoom>> getAll();

    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    void insert(FishPointRoom fishPointRoom);

    @Update
    void update(FishPointRoom fishPointRoom);

    @Query("SELECT * FROM fishPointRoom WHERE name_lake LIKE :nameLake")
    FishPointRoom getFishPoint(String nameLake);

    @Query("DELETE FROM fishPointRoom WHERE name_lake LIKE :nameLake")
    void deleteByNameLake(String nameLake);

    @Delete
    void delete(FishPointRoom fishPointRoom);

    @Query("DELETE FROM fishPointRoom")
    void deleteTable();



}
