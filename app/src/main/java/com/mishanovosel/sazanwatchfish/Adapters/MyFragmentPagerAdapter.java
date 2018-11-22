package com.mishanovosel.sazanwatchfish.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.InterfaceMap.MapConstant;

public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position) {
            case 0:
                title = "Map";
                break;

           // case 1:
               // title = "Weather Forecast";
              //  break;
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment  fragment = null;
        switch (position) {
            case 0:
                fragment = new MapFragment();
                break;
           // case 1:
              //  fragment = new WeatherForecastFragment();
               // break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return MapConstant.PAGE_COUNT;
    }
}
