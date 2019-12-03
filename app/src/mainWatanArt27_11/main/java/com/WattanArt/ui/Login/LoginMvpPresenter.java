package com.WattanArt.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.WattanArt.ui.base.MvpPresenter;
import com.WattanArt.ui.base.MvpView;

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void getLoginData(AppCompatActivity context, String name, String email, String fcm_token,
                      String password, String deviceID , String deviceTypeID , boolean isRemmember);

    void getLoginDataFromSocial(AppCompatActivity appCompatActivity);

    void loginWithFacebook(AppCompatActivity context, String socialId, String email, String fcm_token
            , String device_id , String deviceTypeId);

    void sendforgetPasswordToEmail(String email);

    void onActivityResult(int requestCode, int resultCode, Intent data);

}
