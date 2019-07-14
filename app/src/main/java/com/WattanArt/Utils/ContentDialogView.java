package com.WattanArt.Utils;

import android.app.Dialog;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;


/**
 * Created by khaled.badawy on 4/12/2018.
 */

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String mMessage = getArguments().getString(ARG);

        return new AlertDialog.Builder(getActivity())
                .setTitle("")
                .setMessage(mMessage)
                .setCancelable(true)
                .create();
    }
}