package com.mishanovosel.sazanwatchfish.Events;

import android.util.Log;
import android.widget.Toast;

import com.mishanovosel.sazanwatchfish.App;
import com.mishanovosel.sazanwatchfish.AppDatabase;
import com.mishanovosel.sazanwatchfish.Dialogs.AddPointDialog;
import com.mishanovosel.sazanwatchfish.Dialogs.DeletePointDialog;
import com.mishanovosel.sazanwatchfish.FishPointRoom;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.InterfaceMap.OnResizeMap;
import com.mishanovosel.sazanwatchfish.databinding.AddPointDialogBinding;

import java.security.PublicKey;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class DeleteClickInDialog {

    @Inject
    AppDatabase db;

    private static final String TAG = "MapFragment";

    private DeletePointDialog deletePointDialog;
    private String nameDeletePointString;
    private MapFragment mapFragment;


    public DeleteClickInDialog(DeletePointDialog deletePointDialog, String nameDeletePointString, MapFragment mapFragment){ //, AddPointDialogBinding addPointDialogBinding) {
        this.deletePointDialog = deletePointDialog;
        this.nameDeletePointString = nameDeletePointString;
        this.mapFragment = mapFragment;

       // this.mapFragment = mapFragment;
       // this.addPointDialogBinding = addPointDialogBinding;;
    }


   public void onDeletePointClick(){
     // Log.d(TAG, nameDeletePointString + " XXXXX");
       App.getMyComponent().inject(this);
           Completable.fromAction(new Action() {
               @Override
               public void run() throws Exception {
                   db.fishPointDao().deleteByNameLake(nameDeletePointString);
               }
           }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
               @Override
               public void onSubscribe(Disposable d) {
               }

               @Override
               public void onComplete() {
                   Toast.makeText(deletePointDialog.getActivity(),
                           "Delete done", Toast.LENGTH_LONG).show();
                   OnResizeMap onResizeMap = mapFragment;
                   onResizeMap.onResize();
                   deletePointDialog.dismiss();
               }

               @Override
               public void onError(Throwable e) {
                   Toast.makeText(deletePointDialog.getActivity(),
                           "Try an anytime ", Toast.LENGTH_LONG).show();
                   deletePointDialog.dismiss();
               }

           });


     ///  @Query("DELETE FROM Orders_table WHERE quote_no LIKE  :quote_no")
      // void deleteOrderById(String quote_no);
    }


    public void onCancell(){
        deletePointDialog.dismiss();
    }
}
