package com.mishanovosel.sazanwatchfish;

import android.os.Parcel;
import android.os.Parcelable;

public class RecyclerModel  {

    private String dayText;
    private String dayTextSmall;
    private String dayTimeSmall;
    private String windSpeed;
    private String rainDescription;
    private String temp;
    private String pressure;
    private String icon;
    private String cityName;
    private String countryName;


    public String getDayText() {
        return dayText;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getRainDescription() {
        return rainDescription;
    }

    public void setRainDescription(String rainDescription) {
        this.rainDescription = rainDescription;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = String.valueOf(temp.intValue());
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = String.valueOf(pressure);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDayTimeSmall() {
        return dayTimeSmall;
    }

    public void setDayTimeSmall(String dayTimeSmall) {
        this.dayTimeSmall = dayTimeSmall;
    }


    public String getDayTextSmall() {
        return dayTextSmall;
    }

    public void setDayTextSmall(String dayTextSmall) {
        this.dayTextSmall = dayTextSmall;
    }


}
