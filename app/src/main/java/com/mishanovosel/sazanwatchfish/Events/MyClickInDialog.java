package com.mishanovosel.sazanwatchfish.Events;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.mishanovosel.sazanwatchfish.App;
import com.mishanovosel.sazanwatchfish.AppDatabase;
import com.mishanovosel.sazanwatchfish.Dialogs.AddPointDialog;
import com.mishanovosel.sazanwatchfish.FishPointRoom;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.InterfaceMap.FishPointDao;
import com.mishanovosel.sazanwatchfish.InterfaceMap.PassParametersMarker;
import com.mishanovosel.sazanwatchfish.MyLocation;
import com.mishanovosel.sazanwatchfish.databinding.AddPointDialogBinding;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyClickInDialog {

    @Inject
    AppDatabase db;

    //private List<FishPointRoom> pointNew;

    private AddPointDialogBinding addPointDialogBinding;

    private static final String TAG = "MapFragment";

    private AddPointDialog addPointDialog;

    PassParametersMarker passParametersMarker;
    MapFragment mapFragment;
    FishPointRoom fishPointRoom;

    public MyClickInDialog(AddPointDialog addPointDialog, MapFragment mapFragment, AddPointDialogBinding addPointDialogBinding) {
        this.addPointDialog = addPointDialog;
        this.mapFragment = mapFragment;
        this.addPointDialogBinding = addPointDialogBinding;
    }

    public void onAddNewPoint(MyLocation location){

        App.getMyComponent().inject(this);
        //FishPointDao fishPointDao = db.fishPointDao();
        fishPointRoom = new FishPointRoom();
        addPointDialogBinding.setFishpoint(fishPointRoom);

        String editLakename = addPointDialogBinding.editAddLake.getText().toString().trim();
        if(editLakename.length() == 0) {
            Toast.makeText(addPointDialogBinding.editAddLake.getContext(),
                    "You did not enter a name of lake", Toast.LENGTH_LONG).show();

            return;
        }
        fishPointRoom.nameLake = editLakename;
        fishPointRoom.latitude = location.getLocation().getLatitude();
        fishPointRoom.longitude = location.getLocation().getLongitude();
        //fishPointDao.insert(fishPointRoom);

        onInsetFishPoint(fishPointRoom);

        passParametersMarker = mapFragment;
        passParametersMarker.onPassParametersMarker(fishPointRoom);

        //Log.d(TAG, addPointDialogBinding.editAddLake.getText().toString() + "EDIT");
       /* db.fishPointDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FishPointRoom>>() {
                    @Override
                    public void accept(List<FishPointRoom> point) throws Exception {
                         pointNew = point;
                    }
                });

        if(pointNew != null) {
            for(FishPointRoom fishPointRoomNew : pointNew){
                Log.d(TAG, fishPointRoomNew.nameLake + " lat " + String.valueOf(fishPointRoomNew.latitude).toString()
                        + " lng " + String.valueOf(fishPointRoomNew.longitude).toString()
                        + " SIZE " + String.valueOf(String.valueOf(pointNew.size()).toString() + " ID " + String.valueOf(String.valueOf(fishPointRoomNew.id).toString())));
            }
        }*/
        addPointDialog.dismiss();
    }

    public void onInsetFishPoint( final FishPointRoom fishPointRoom) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                //User user = new User(firstName, lastName);
                db.fishPointDao().insert(fishPointRoom);
                //db.userDao().insertAll(user);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onComplete() {
                //databaseCallback.onUserAdded();
            }

            @Override
            public void onError(Throwable e) {
               // databaseCallback.onDataNotAvailable();
                Toast.makeText(addPointDialogBinding.editAddLake.getContext(),
                        "Please, input another name of lake", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void onCancell(){
        addPointDialog.dismiss();
    }
}
