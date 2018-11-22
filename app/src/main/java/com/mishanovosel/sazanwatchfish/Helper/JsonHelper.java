package com.mishanovosel.sazanwatchfish.Helper;

import android.util.Log;

import com.mishanovosel.sazanwatchfish.RecyclerModel;
import com.mishanovosel.sazanwatchfish.WeatherPojo.Weather;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherDay;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JsonHelper {

    private static final String TAG = "MapFragment";
    private static JsonHelper jsonHelper = null;
    //private String dayTextSmallRecModel = "";


    public static JsonHelper getInstance() {
        if (jsonHelper == null) {
            jsonHelper = new JsonHelper();
        }
        return jsonHelper;
    }


    public ArrayList<RecyclerModel> workWithJson(WeatherForecast jsonResult) {

        ArrayList<RecyclerModel> recModelarray = new ArrayList<>();


        Observable.fromIterable(jsonResult.getList()).subscribe(new Observer<WeatherDay>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeatherDay weatherDay) {
                RecyclerModel recyclerModel = new RecyclerModel();
                recyclerModel.setCityName(jsonResult.getCity().getName());
                recyclerModel.setCountryName(jsonResult.getCity().getCountry());


                /*String dayTextSmall = weatherDay.getDateTextFormat().substring(0, 10);
                Log.e(TAG, dayTextSmallRecModel + "   " + dayTextSmall);

               if (!dayTextSmall.equals(dayTextSmallRecModel)) {
                    dayTextSmallRecModel = dayTextSmall;
                    recyclerModel.setDayTextSmall(dayTextSmallRecModel);

                } else {
                    recyclerModel.setDayTextSmall("");
                }*/

                String dayTextSmall = weatherDay.getDateTextFormat().substring(0, 10);
                recyclerModel.setDayTextSmall(dayTextSmall);

                String dayTimeSmall = weatherDay.getDateTextFormat().substring(11, 18);
                recyclerModel.setDayTimeSmall(dayTimeSmall);

                recyclerModel.setWindSpeed(weatherDay.getWind().getWindSpeed());
                recyclerModel.setRainDescription(weatherDay.getWeather().get(0).getRain());
                recyclerModel.setTemp(weatherDay.getMain().getTemp());
                recyclerModel.setPressure(weatherDay.getMain().getPressure());
                recyclerModel.setIcon(weatherDay.getWeather().get(0).getIcon() + ".png");

                recModelarray.add(recyclerModel);

            }

            @Override
            public void onError(Throwable e) {
                // do nothing
            }

            @Override
            public void onComplete() {

            }
        });


        return recModelarray;


     /*   Observable.fromIterable(jsonResult.getList()).subscribe(new Consumer<WeatherDay>() {
            @Override
            public void accept(WeatherDay weatherDay) throws Exception {
                RecyclerModel recyclerModel = new RecyclerModel();
                recyclerModel.setCityName(jsonResult.getCity().getName());
                recyclerModel.setCountryName(jsonResult.getCity().getCountry());
                recyclerModel.setDayText(weatherDay.getDateTextFormat());
                recyclerModel.setWindSpeed(weatherDay.getWind().getWindSpeed());
                recyclerModel.setRainDescription(weatherDay.getWeather().get(0).getRain());
                recyclerModel.setTemp(weatherDay.getMain().getTemp());
                recyclerModel.setPressure(weatherDay.getMain().getPressure());
                recyclerModel.setIcon(weatherDay.getWeather().get(0).getIcon());

                recModelarray.add(recyclerModel);
            }

        });

              /*  for(WeatherDay weatherDay : jsonResult.getList()) {

                    RecyclerModel recyclerModel = new RecyclerModel();
                    recyclerModel.setCityName(jsonResult.getCity().getName());
                    recyclerModel.setCountryName(jsonResult.getCity().getCountry());
                    recyclerModel.setDayText(weatherDay.getDateTextFormat());
                    recyclerModel.setWindSpeed(weatherDay.getWind().getWindSpeed());
                    recyclerModel.setRainDescription(weatherDay.getWeather().get(0).getRain());
                    recyclerModel.setTemp(weatherDay.getMain().getTemp());
                    recyclerModel.setPressure(weatherDay.getMain().getPressure());
                    recyclerModel.setIcon(weatherDay.getWeather().get(0).getIcon());

                    recModelarray.add(recyclerModel);
                    //Log.d(TAG, "city-name333: " + recModelarray.get(0).getCityName());
                }*/


    }
}
