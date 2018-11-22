package com.mishanovosel.sazanwatchfish.WeatherPojo;

import com.google.gson.annotations.SerializedName;

public class Clouds {

    @SerializedName("all")
    int cloud;

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }


}
