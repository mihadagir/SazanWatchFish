package com.mishanovosel.sazanwatchfish;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.mishanovosel.sazanwatchfish.Dialogs.AddPointDialog;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.InterfaceMap.FishPointDao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;

public class MyClickAdd {


    private FragmentManager fragManager;
    private MyLocation myLocation;
    private MapFragment mapFragment;

    public MyClickAdd(MapFragment mapFragment, MyLocation myLocation) {
        Log.d("MYCLICK", "firstCreate");
       this.myLocation = myLocation;
       this.mapFragment = mapFragment;
    }

    public void onShowAddDialog() {
        //this.location = location.getLocation();
       // App.getMyComponent().inject(this);
        //db = App.getInstance().getDatabase();

        Log.d("MYCLICK", "BUTTON CLICKED");


        DialogFragment addPointDialog = new AddPointDialog();
        Log.d("MYCLICK", "firstCreate");
        Bundle args = new Bundle();
        args.putSerializable("my_location", (Serializable) myLocation);
        args.putSerializable("map_fragment", (Serializable) mapFragment);
        addPointDialog.setArguments(args);
        addPointDialog.setCancelable(false);
        addPointDialog.show(mapFragment.getFragmentManager(), "dlgAddPoint");


        //Log.d(TAG, " SIZE777 " );
       /* FishPointDao fishPointDao = db.fishPointDao();
        FishPointRoom fishPointRoom = new FishPointRoom();
        fishPointRoom.nameLake = "Vareha";
        fishPointRoom.latitude = this.location.getLatitude();
        fishPointRoom.longitude = this.location.getLongitude();
/////////////////////
        //fishPointDao.insert(fishPointRoom);555
        //fishPointDao.deleteTable();
        /*db.fishPointDao().getAll()555
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FishPointRoom>>() {
                    @Override
                    public void accept(List<FishPointRoom> point) throws Exception {
                         pointNew = point;
                    }
                });*/  //555

       /* if(pointNew != null) {
            for(FishPointRoom fishPointRoomNew : pointNew){
                Log.d(TAG, fishPointRoomNew.nameLake + " lat " + String.valueOf(fishPointRoomNew.latitude).toString()
                        + " lng " + String.valueOf(fishPointRoomNew.longitude).toString()
                        + " SIZE " + String.valueOf(String.valueOf(pointNew.size()).toString() + " ID " + String.valueOf(String.valueOf(fishPointRoomNew.id).toString())));
            }
        }*/ //555
    }


}
