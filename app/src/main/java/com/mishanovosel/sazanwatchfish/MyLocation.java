package com.mishanovosel.sazanwatchfish;

import android.location.Location;

import java.io.Serializable;

public class MyLocation implements Serializable{

   private Location location;
   public MyLocation(){


   }

    public void setLocation(Location location){
     this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
