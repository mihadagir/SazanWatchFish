package com.mishanovosel.sazanwatchfish;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

@Entity (indices = {@Index(value = {"name_lake"}, unique = true)})
public class FishPointRoom {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name_lake")
    public String nameLake;

    public double latitude;

    public double longitude;
}
