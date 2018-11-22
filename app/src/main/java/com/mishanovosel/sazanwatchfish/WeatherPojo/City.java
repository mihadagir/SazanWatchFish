package com.mishanovosel.sazanwatchfish.WeatherPojo;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("name")
    private String city;

    private String country;

    public String getName() {
        return city;
    }

    public void setName(String name) {
        this.city = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
