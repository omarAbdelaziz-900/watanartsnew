package com.WattanArt;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.WattanArt.Adapters.SampleMokupItemsAdapter;
import com.WattanArt.Utils.ViewHelper;
import com.WattanArt.model.SampleMokupItems;
import com.WattanArt.ui.mobileCase.ComponentActivity;

import java.util.List;
import java.util.Objects;




public class AndroidDialogTools {

    private static int userID;

    public static Dialog getTextOfUser(final Activity context, final VerifyDialogInterfaceForText dialogListener) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.CENTER);
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_for_text_write);

        final ImageView cancel_text_img = dialog.findViewById(R.id.cancel_text_img);
        final EditText text_for_user = dialog.findViewById(R.id.text_for_user);
        final Button ok_btn = dialog.findViewById(R.id.ok_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialogListener.onConfirmClick(text_for_user.getText().toString());
            }
        });

        cancel_text_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.hideKeyboard(context);
                dialog.dismiss();
                dialogListener.onCancelClick(text_for_user.getText().toString());
            }
        });

        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Dialog getMokupSamples(final Activity context, final VerifyDialogInterfaceForText dialogListener,List<SampleMokupItems> sampleMokupItems) {
        // Create custom dialog object
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialog.getWindow()).setGravity(Gravity.CENTER);
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.layout_for_mokup_sample);

        final RecyclerView recycler_mokup_img = dialog.findViewById(R.id.recycler_mokup_img);

        initRecyclerview(context,recycler_mokup_img,sampleMokupItems);

//        ok_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                dialogListener.onConfirmClick(text_for_user.getText().toString());
//            }
//        });

//        cancel_text_img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewHelper.hideKeyboard(context);
//                dialog.dismiss();
//                dialogListener.onCancelClick(text_for_user.getText().toString());
//            }
//        });

        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

    public static void initRecyclerview(Activity context , RecyclerView recyclerView , List<SampleMokupItems> sampleMokupItems){
        LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
        SampleMokupItemsAdapter sampleMokupItemsAdapter = new SampleMokupItemsAdapter(context, sampleMokupItems);
        recyclerView.setAdapter(sampleMokupItemsAdapter);
    }
}

