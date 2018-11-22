package com.mishanovosel.sazanwatchfish.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.mishanovosel.sazanwatchfish.Events.MyClickInDialog;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.MyClickAdd;
import com.mishanovosel.sazanwatchfish.MyLocation;
import com.mishanovosel.sazanwatchfish.R;
import com.mishanovosel.sazanwatchfish.databinding.AddPointDialogBinding;

public class AddPointDialog extends DialogFragment {
    AddPointDialogBinding addPointDialogBinding;
    MyLocation myLocation;
    MapFragment mapFragment;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            myLocation = (MyLocation) bundle.getSerializable("my_location");
            mapFragment = (MapFragment) bundle.getSerializable("map_fragment");

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        addPointDialogBinding = DataBindingUtil.inflate(inflater, R.layout.add_point_dialog, null, false);
        View view = addPointDialogBinding.getRoot();

        alertDialog.setView(view);

        MyClickInDialog myClickAdd = new MyClickInDialog(AddPointDialog.this, mapFragment, addPointDialogBinding);
        addPointDialogBinding.setHandler(myClickAdd);
        addPointDialogBinding.setLocation(myLocation);

        //addPointDialogBinding.setLocation();
        return alertDialog.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
    @Override
    public void onResume() {
        super.onResume();

       // Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

       // Log.d(LOG_TAG, "onPause");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
