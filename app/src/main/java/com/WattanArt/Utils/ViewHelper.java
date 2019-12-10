package com.WattanArt.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.WattanArt.R;
import com.WattanArt.Utils.widgets.ContentDialogView;


/**
 * Created by Android Team on 1/31/2018.
 */

public class ViewHelper {

    private static Dialog mDialog;
    static Dialog dialogsLoading;
    static ProgressDialog progressDialog;
    static DialogFragment dialogFragment;
    /**
     * Show the Dialog in the context.
     *
     * @param context
     */


    @SuppressLint("HandlerLeak")
//    public static void showProgressDialog(Context context) {
//
//        if (context == null) {
//            Logger.error("null contect", "null context");
//        }
//
//        if (mDialog == null) {
//            mDialog = new Dialog(context);
//            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            mDialog.getWindow().setGravity(Gravity.CENTER);
//            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            mDialog.setContentView(R.layout.layout_dialog_loading);
//            mDialog.setCancelable(false);
//        }
//        if (mDialog != null)// && mDialog.getWindow() != null
//            mDialog.show();
//
//
//    }

    public static Dialog showProgressDialog(Context context) {
        /*dialogFragment = ContentDialogView.newInstance("test");
        dialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "mDialog");*/


        progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.layout_dialog_loading);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }


    public static void hideProgressDialog() {
        if (dialogFragment!=null){
            dialogFragment.dismiss();
        }
        if (progressDialog != null && progressDialog.isShowing() && progressDialog.getWindow() != null) {
            progressDialog.cancel();
        }
    }
    public static void showProgressFragment(Context context) {
        dialogFragment = ContentDialogView.newInstance("test");
        dialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), "mDialog");
    }
    public static void hideProgressFragment() {
        if (dialogFragment!=null){
            dialogFragment.dismiss();
        }

    }



    /**
     * Hide the Dialog by passing his reference ,
     */


    public static void hideKeyboard(Activity context) {
        if (context.getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void displayError(Activity activity, String error) {
        DialogFragment dialogFragment = ContentDialogView.newInstance(error);
        dialogFragment.show(((FragmentActivity) activity).getSupportFragmentManager(), "mDialog");


    }

    public static void showMessage(Context context,  int errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }

    public static void showMessage(Context context, String errorMessage) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
    }
}
