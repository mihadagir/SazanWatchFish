package com.mishanovosel.sazanwatchfish.WeatherPojo;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("main")
    private String rain;

    private String icon;

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


   // public String getIcon() { return desctiption.get(0).icon; }

    //public String getIconUrl() {
    //    return "http://openweathermap.org/img/w/" + desctiption.get(0).icon + ".png";
   // }



}
