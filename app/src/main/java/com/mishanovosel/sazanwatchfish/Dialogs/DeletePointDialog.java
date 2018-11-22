package com.mishanovosel.sazanwatchfish.Dialogs;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mishanovosel.sazanwatchfish.Events.DeleteClickInDialog;
import com.mishanovosel.sazanwatchfish.Events.MyClickInDialog;
import com.mishanovosel.sazanwatchfish.Fragments.MapFragment;
import com.mishanovosel.sazanwatchfish.MyLocation;
import com.mishanovosel.sazanwatchfish.R;
import com.mishanovosel.sazanwatchfish.databinding.DeletePointDialogBinding;

public class DeletePointDialog extends DialogFragment {

    private static final String TAG = "MapFragment";
    MapFragment mapFragment;
    public DeletePointDialog() {
    }

    DeletePointDialogBinding deletePointDialogBinding;
    AlertDialog.Builder alertDialog;
    String nameDeletePointString;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            nameDeletePointString = bundle.getString("delete_point");
            mapFragment = (MapFragment) bundle.getSerializable("map_fragment_context");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        alertDialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialogTheme);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        deletePointDialogBinding = DataBindingUtil.inflate(inflater, R.layout.delete_point_dialog, null, false);
        deletePointDialogBinding.editDelLake.setText(nameDeletePointString);

        View view = deletePointDialogBinding.getRoot();
        Log.d(TAG, "Lake : " + nameDeletePointString);
        alertDialog.setView(view);

        DeleteClickInDialog deleteClickInDialog = new DeleteClickInDialog(DeletePointDialog.this, nameDeletePointString, mapFragment);//, mapFragment, addPointDialogBinding);
        deletePointDialogBinding.setHandler(deleteClickInDialog);
        //addPointDialogBinding.setLocation(myLocation);

        //addPointDialogBinding.setLocation();
        return alertDialog.create();
    }
}
