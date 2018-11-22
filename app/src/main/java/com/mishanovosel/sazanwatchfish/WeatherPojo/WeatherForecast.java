package com.mishanovosel.sazanwatchfish.WeatherPojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherDay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast{

    private City city;

    private List<WeatherDay> list;// = new ArrayList<>();


    public List<WeatherDay> getList() {
        return list;
    }

    public void setList(List<WeatherDay> list) {
        this.list = list;
    }

    public City getCity() {
       return city;
    }

    public void setCity(City city) {
       this.city = city;
    }
}
