package com.WattanArt.Utils.widgets;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.WattanArt.R;

public class ContentDialogView extends android.support.v4.app.DialogFragment {

    private final static String ARG = "message";

    public ContentDialogView() {
    }

    public static ContentDialogView newInstance(String message) {
        ContentDialogView fragment = new ContentDialogView();
        Bundle args = new Bundle();
        args.putString(ARG, message);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog_loading, container, false);
        return view;
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*String mMessage = getArguments().getString(ARG);
        return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage(mMessage)
                .setCancelable(true)
                .create();*/
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = this.getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.layout_dialog_loading, null);
//        dialogBuilder.setView(dialogView);
//
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.setCancelable(false);
//        alertDialog.show();
//        return alertDialog;

//    }
}