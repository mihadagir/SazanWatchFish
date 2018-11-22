package com.mishanovosel.sazanwatchfish;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mishanovosel.sazanwatchfish.Adapters.MyFragmentPagerAdapter;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherDay;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherForecast;
import com.mishanovosel.sazanwatchfish.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MapFragment";
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    public ActivityMainBinding mBinding;

    /////////

    //GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

       // viewPager = mBinding.pager;
        //pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(pagerAdapter);

       ////////////

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                //if(position == 2)  showFloatingActionButton();
                // else hideFloatingActionButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "DestroyMain");
    }
}
