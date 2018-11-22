package com.mishanovosel.sazanwatchfish;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mishanovosel.sazanwatchfish.InterfaceMap.MapConstant;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherForecast;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {

    private static Retrofit retrofit = null;

   // @Inject
   // static  OkHttpClient okHttpClient;

    public interface ApiInterface {
         /* @GET("weather")
     Call<WeatherDay> getToday(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                //@Query("units") String units,
                @Query("appid") String appid
        );*/

        /*  @GET("forecast")
        Observable<WeatherForecast> getForecast(
        //Flowable<WeatherForecast> getForecast(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("appid") String appid
        );
*/
      @GET("forecast")
        Call<WeatherForecast> getForecast(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String metric,
                @Query("appid") String appid
        );
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(MapConstant.BASE_URL)
                   // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    //.client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
