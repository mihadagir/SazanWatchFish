package com.mishanovosel.sazanwatchfish;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.mishanovosel.sazanwatchfish.Adapters.RecyclerWeatherAdapter;
import com.mishanovosel.sazanwatchfish.Helper.JsonHelper;
import com.mishanovosel.sazanwatchfish.InterfaceMap.MapConstant;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherForecast;
import com.mishanovosel.sazanwatchfish.databinding.ActivityForcastBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForcastActivity extends AppCompatActivity {

    public ActivityForcastBinding mForecastBinding;
    WeatherAPI.ApiInterface api;
    WeatherForecast data;
    private static final String TAG = "MapFragment";
    ArrayList<RecyclerModel> recModelarray;
    public String cityCountryText;
    Call<WeatherForecast> callForecast;
    Timer timer;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mForecastBinding = DataBindingUtil.setContentView(this, R.layout.activity_forcast);
        
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdfDate = new SimpleDateFormat("MMM dd yyyy");
                                SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss");

                                String dateString = sdfDate.format(date);
                                String timeString = sdfTime.format(date);

                                mForecastBinding.textDates.setText(dateString);
                                mForecastBinding.textTime.setText(timeString);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Log.e(TAG, e.toString());
                }
            }
        };
        t.start();

        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("keyLatitude", 0.0);
        double longitude = intent.getDoubleExtra("keyLongitude", 0.0);

        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);

        //loadData();

        timer = new Timer();
//Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          callForecast = api.getForecast(latitude, longitude, MapConstant.UNITS_METRIC, MapConstant.KEY_APPID);
                                          loadData();
                                          //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                      }
                                  },
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                3600000);



        //flForecast.observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherForecast>() {
       /* flForecast.observeOn(Schedulers.io()).subscribe(new Consumer<WeatherForecast>() {
            @Override
            public void accept(
                    @io.reactivex.annotations.NonNull final WeatherForecast geonames)
                    throws Exception {
                weatherForecast = geonames;

                Log.d(TAG, "city-name: " + geonames.getList().get(3).getWeather().get(0).getRain());

            }
        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        timer.cancel();
        timer = null;
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = mForecastBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(4), true));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setNestedScrollingEnabled(false);
        RecyclerWeatherAdapter mAdapter = new RecyclerWeatherAdapter(recModelarray);
        recyclerView.setAdapter(mAdapter);
    }

    public void initUI(){

        if (recModelarray == null) {
            return;
        }
        cityCountryText = recModelarray.get(0).getCityName()
                + ", " + recModelarray.get(0).getCountryName();

        mForecastBinding.textCitys.setText(cityCountryText);
        // mForecastBinding.textDates.setText(recModelarray.get(0).getDayText());
        String temp = recModelarray.get(0).getTemp() + " " + "\u00B0C";
        mForecastBinding.textTemp.setText(temp);

        Picasso.get().load(MapConstant.HTTP_OPENWEATHERMAP +
                recModelarray.get(0).getIcon()).resize(150, 150)
                .centerCrop().into(mForecastBinding.imageCloud);

    }

    private void loadData() {
       // loadDataRetrofit = true;

        mForecastBinding.progresBar.setVisibility(View.VISIBLE);

       //setProgressBarIndeterminateVisibility(true);

        callForecast.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {

                data = response.body();
                // Log.d(TAG,response.toString());


                if (response.isSuccessful()) {

                    mHandler = new Handler();

                    new Thread(new Runnable() {
                        @Override
                        public void run () {
                            recModelarray = JsonHelper.getInstance().workWithJson(data);
                            // Perform long-running task here
                            // (like audio buffering).
                            // you may want to update some progress
                            // bar every second, so use handler:
                            mHandler.post(new Runnable() {
                                @Override
                                public void run () {
                                    // make operation on UI - on example
                                    // on progress bar.
                                    initUI();
                                    initRecyclerView();
                                    mForecastBinding.progresBar.setVisibility(View.GONE);
                                   // setProgressBarIndeterminateVisibility(false);

                                }
                            });
                        }
                    }).start();

                   // recModelarray = JsonHelper.getInstance().workWithJson(data);...................
                    //initUI();............
                    //initRecyclerView();..................
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure");//TO DO Check internet available
                Log.e(TAG, t.toString());
                mForecastBinding.progresBar.setVisibility(View.GONE);
                Toast.makeText(ForcastActivity.this, "Try Later", Toast.LENGTH_LONG).show();
            }
        });
    }

}
//Flowable<WeatherForecast> flForecast = api.getForecast(48.5132, 32.2597, MapConstant.KEY_APPID).subscribeOn(Schedulers.io());
       /* Observable<WeatherForecast> obsForecast = api.getForecast(48.5132, 32.2597, MapConstant.KEY_APPID).subscribeOn(Schedulers.io());


        Observer<WeatherForecast> observer = new Observer<WeatherForecast>() {

            @Override
            public void onError(Throwable e) {
                // Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
                //Toast.makeText(context,
                //    "onCompleted ", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),
                        "1111 ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ForcastActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("weatherForecastSerealiz", weatherForecast);

               // intent.putExtra("weatherIntent", bundle);
               // intent.putExtra("weatherIntent", weatherForecast.getList().get(3).getWeather().get(0).getRain());
                Log.d(TAG, "city-name: " + weatherForecast.getList().get(3).getWeather().get(0).getRain());
                startActivity(intent);

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(WeatherForecast weatherForcast) {
                weatherForecast = weatherForcast;
            }
        };
        obsForecast.subscribe(observer);*/