package com.mishanovosel.sazanwatchfish;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.mishanovosel.sazanwatchfish.InterfaceMap.DaggerMyComponent;
import com.mishanovosel.sazanwatchfish.InterfaceMap.MyComponent;
import com.mishanovosel.sazanwatchfish.Modul.AppDatabaseModule;
import com.mishanovosel.sazanwatchfish.Modul.ContextModule;

public class App extends Application {
    private static MyComponent myComponent;

    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        myComponent = DaggerMyComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();

        //instance = this;
       // database = Room.databaseBuilder(this, AppDatabase.class, "database")
               // .allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public static MyComponent getMyComponent() { return myComponent;}
}
