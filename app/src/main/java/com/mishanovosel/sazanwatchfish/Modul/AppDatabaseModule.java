package com.mishanovosel.sazanwatchfish.Modul;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.mishanovosel.sazanwatchfish.AppDatabase;

import dagger.Module;
import dagger.Provides;

@Module(includes = ContextModule.class)
public class AppDatabaseModule {

    @Provides
    AppDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")//fallbackToDestructiveMigration()
                //.allowMainThreadQueries()
        .build();
    }
}
