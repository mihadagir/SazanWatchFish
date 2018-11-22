package com.mishanovosel.sazanwatchfish.WeatherPojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherDay {

    @SerializedName("dt_txt")
    private String dateTextFormat;

    private  Wind wind;

    private Main main;


    private List<Weather> weather;

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }



  public List<Weather> getWeather() {
      return weather;
  }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getDateTextFormat() {
        return dateTextFormat;
    }

    public void setDateTextFormat(String dateTextFormat) {
        this.dateTextFormat = dateTextFormat;
    }

    public Main getMain() {
       return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
