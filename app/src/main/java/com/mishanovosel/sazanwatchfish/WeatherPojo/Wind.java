package com.mishanovosel.sazanwatchfish.WeatherPojo;

import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    private String windSpeed;

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }



}
