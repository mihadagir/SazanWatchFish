package com.mishanovosel.sazanwatchfish;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.mishanovosel.sazanwatchfish.InterfaceMap.FishPointDao;

@Database(entities = {FishPointRoom.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FishPointDao fishPointDao();
}
