package com.mishanovosel.sazanwatchfish.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mishanovosel.sazanwatchfish.App;
import com.mishanovosel.sazanwatchfish.AppDatabase;
import com.mishanovosel.sazanwatchfish.Dialogs.DeletePointDialog;
import com.mishanovosel.sazanwatchfish.FishPointRoom;
import com.mishanovosel.sazanwatchfish.ForcastActivity;
import com.mishanovosel.sazanwatchfish.Helper.CompareKoordObserverHelper;
import com.mishanovosel.sazanwatchfish.Helper.JsonHelper;
import com.mishanovosel.sazanwatchfish.InterfaceMap.MapConstant;
import com.mishanovosel.sazanwatchfish.InterfaceMap.OnResizeMap;
import com.mishanovosel.sazanwatchfish.InterfaceMap.PassParametersMarker;
import com.mishanovosel.sazanwatchfish.MyClickAdd;
import com.mishanovosel.sazanwatchfish.MyLocation;
import com.mishanovosel.sazanwatchfish.R;
import com.mishanovosel.sazanwatchfish.RecyclerModel;
import com.mishanovosel.sazanwatchfish.WeatherAPI;
import com.mishanovosel.sazanwatchfish.WeatherPojo.Weather;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherDay;
import com.mishanovosel.sazanwatchfish.WeatherPojo.WeatherForecast;
import com.mishanovosel.sazanwatchfish.databinding.MapFragmentBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback, Serializable, PassParametersMarker,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, OnResizeMap {

    @Inject
    AppDatabase db;

    private Marker myMarker;
    private List<FishPointRoom> pointNew;
    private static final String TAG = "MapFragment";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private GoogleMap mGoogleMap;
    private Bundle mSavedInstanceState;
    private Boolean mLocationPermissionsGranted = false;
    private Location currentLocation;
    private MapView mMapView;
    // private TextView pointText;
    public MapFragmentBinding binding;
    private List<LatLng> arrayLatLng;
    private LatLng mCarentLatLng;
    MyClickAdd myClick;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getMyComponent().inject(this);

        // db.fishPointDao().deleteTable();
        getPointMarkersForMap();
        arrayLatLng = new ArrayList<LatLng>();

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, null, false);
        View view = binding.getRoot();
        mMapView = binding.map;
        servicesOK();
        // pointText = binding.textPoin;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mSavedInstanceState = savedInstanceState;

    }

    private void setMapAfterPermissionGranted() {

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnMapClickListener(MapFragment.this);
    }

    private void moveCamera(LatLng latLng, float zoom) {
        // Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        if (getActivity() == null) return;

        mGoogleMap = mMap;
        setMarkersForMap();
        getLocationPermission();
        setMapAfterPermissionGranted();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        MapConstant.LOCATION_PERMISSION_REQUEST_CODE);

        }

        getDeviceLocation();

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (getActivity() == null) return;
        if (ContextCompat.checkSelfPermission(getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        MapConstant.LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    MapConstant.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");


        if (getActivity() == null) return;
        final MyLocation myLocation = new MyLocation();

        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try {

            final Task location = mFusedLocationClient.getLastLocation();
            Log.d(TAG, "onComplete: 000000000  " + location.toString());

            myClick = new MyClickAdd(MapFragment.this, myLocation);


           /* task.addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        getPointMarkersForMap();

                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        Log.d(TAG, "lokation 1: " + String.valueOf(latitude));

                    }
                }
            });*/





            location.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    //getPointMarkersForMap();!!!!!!!!!!!!!!!
                    Handler handler = new Handler();
                    Log.d(TAG, "onComplete ttt: found location!" + task.getResult());

                    if (task.getResult() == null) {


                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 5000ms
                                getDeviceLocation();

                            }
                        }, 5000);


                        // Запрос на получение разрешения взятия геолокации? Может и не нужно
                        // TIMER 5SEC
                        // RUN = getDeviceLocation()
                        // handler.postDelayed(RUN, 5000)
                    }

                    Log.d(TAG, "onComplete: found location!");
                    currentLocation = (Location) task.getResult();//******* ERROR *********
//                        Log.d(TAG, "onComplete: found location! " + task.getResult().toString());
                    if (currentLocation != null) {
                        mCarentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        moveCamera(mCarentLatLng, MapConstant.DEFAULT_ZOOM);//+++
                        myLocation.setLocation(currentLocation);

                        //Toast.makeText(getActivity(),
                        // "L1 " + mCarentLatLng.toString() + " T2 " + arrayLatLng.get(0).toString(), Toast.LENGTH_LONG).show();

                        compireMarkerWithPosition();
                        binding.setHandler(myClick);
                    }

                } else {
                    Log.d(TAG, "onComplete: current location is null");
                    Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    private void initMap() {
        if(!checkConnection()) return;//&&&&&&&&&&&&&

        if (mMapView != null) {
            mMapView.onCreate(this.mSavedInstanceState);
            mMapView.onResume();// needed to get the map to display immediatel
            mMapView.getMapAsync(MapFragment.this);
        }
    }

    public void servicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");


        if (getActivity() == null) return;
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            initMap();
            // getLocationPermission();

        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available,
                    MapConstant.ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;
        if (getActivity() == null) return;

        switch (requestCode) {
            case MapConstant.LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;

                    Log.e(TAG, "onResponse");
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "DestroyMapFragment");
    }

    @Override
    public void onPassParametersMarker(FishPointRoom fishPointRoom) {

        myMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.fishsazan_normal))
                .position(new LatLng(fishPointRoom.latitude, fishPointRoom.longitude))
                .title(fishPointRoom.nameLake));


        arrayLatLng.clear();
        setMarkersForMap();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        myMarker = marker;

        Log.d(TAG, "lake  " );

        if (getFragmentManager() != null) {
            // Log.d(TAG, "Hello");
            String nameDeletePointString = myMarker.getTitle();
            Log.d(TAG, "lake  " + nameDeletePointString);

            Bundle args = new Bundle();
            args.putString("delete_point", nameDeletePointString);
            args.putSerializable("map_fragment_context", MapFragment.this);
            // args.putSerializable("map_fragment", (Serializable) mapFragment);

            DialogFragment deletePointDialog = new DeletePointDialog();
            deletePointDialog.setCancelable(false);
            deletePointDialog.setArguments(args);
            Log.d(TAG, "lake  " );
            deletePointDialog.show(getFragmentManager(), "dlgdeletePoint");

        }
        return true;
    }

    @Override
    public void onResize() {
       // getPointMarkersForMap();
       // setMarkersForMap();

        arrayLatLng.clear();
        //setMarkersForMap();
        myMarker.remove();
    }

    void getPointMarkersForMap() {
        if (db != null) {
            db.fishPointDao().getAll().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<FishPointRoom>>() {
                        @Override
                        public void accept(List<FishPointRoom> point) throws Exception {
                            pointNew = point;
                        }
                    });
        }
    }

    void setMarkersForMap() {
        if (pointNew != null) {
            for (FishPointRoom fishPointRoomNew : pointNew) {

                myMarker = mGoogleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.fishsazan_normal))
                        .position(new LatLng(fishPointRoomNew.latitude, fishPointRoomNew.longitude))
                        .title(fishPointRoomNew.nameLake));


                arrayLatLng.add(new LatLng(fishPointRoomNew.latitude, fishPointRoomNew.longitude));

                Log.d(TAG, fishPointRoomNew.nameLake + " lat " + String.valueOf(fishPointRoomNew.latitude)
                        + " lng " + String.valueOf(fishPointRoomNew.longitude)
                        + " SIZE " + String.valueOf(String.valueOf(pointNew.size()) + " ID " + String.valueOf(String.valueOf(fishPointRoomNew.id))));
            }
        }
    }

    //Is on POINT ???????
    void compireMarkerWithPosition() {
        if (arrayLatLng != null) {

            //Vse vremya sveryaem koordinaty, esli stal tochno na zagruzhenuyu tochku
            Observable<LatLng> observable = Observable.fromIterable(arrayLatLng).subscribeOn(Schedulers.io())
                    // observeOn(Schedulers.io()).filter(latLng -> latLng != mCarentLatLng)
                    .observeOn(AndroidSchedulers.mainThread());

            CompareKoordObserverHelper.getInstance().getAplicationContext(getActivity().getApplicationContext());//...................
            Observer<LatLng> observer = CompareKoordObserverHelper.getInstance().getObserverHelper();

            observable.subscribe(observer);
        }
    }
    

    @Override
    public void onMapClick(LatLng latLng) {
        //Log.d(TAG, "Click!");
       // Log.d(TAG, "onComplete: found location!");

        //Toast.makeText(getActivity(),
           //     "Click ", Toast.LENGTH_LONG).show();


        if(!checkConnection()) return;

        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.d(TAG, String.valueOf(latitude) + " " + String.valueOf(longitude));
        Intent myIntent = new Intent(getActivity(), ForcastActivity.class);
        myIntent.putExtra("keyLatitude", latitude);
        myIntent.putExtra("keyLongitude", longitude);
        getActivity().startActivity(myIntent);
    }


    protected boolean isOnline() {
        NetworkInfo netInfo = null;
        if(getActivity()!= null) {
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            netInfo = cm.getActiveNetworkInfo();
        }
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkConnection(){
        if(isOnline()){
            Log.e(TAG, "You are connected to Internet");//TO DO Check internet available
            return true;
        }else{
            Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}

