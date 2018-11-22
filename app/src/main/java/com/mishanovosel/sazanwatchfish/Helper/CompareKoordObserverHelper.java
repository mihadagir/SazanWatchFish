package com.mishanovosel.sazanwatchfish.Helper;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mishanovosel.sazanwatchfish.App;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CompareKoordObserverHelper extends AppCompatActivity {

    Observer<LatLng> observer;
    Activity activity;
    Context context;
    private static final String TAG = "MapFragment";
    private static CompareKoordObserverHelper compareObserverHelper = null;


    public static CompareKoordObserverHelper getInstance() {
        if (compareObserverHelper == null) {
            compareObserverHelper = new CompareKoordObserverHelper();
        }
        return compareObserverHelper;
    }


    public void getAplicationContext(Context context){
        this.context = context;
    }


    public  Observer<LatLng> getObserverHelper() {

        App.getMyComponent().inject(this);

        observer = new Observer<LatLng>() {

            @Override
            public void onError(Throwable e) {
               // Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onCompleted");
                //Toast.makeText(context,
                    //    "onCompleted ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LatLng s) {
                Log.d(TAG, "onNext: " + s);
                Toast.makeText(context,
                        "OK, YOU ON POINT ", Toast.LENGTH_LONG).show();

            }
        };

        return observer;
    }

}
