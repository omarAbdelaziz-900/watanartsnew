package com.WattanArt.ui.Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.WattanArt.ui.base.MvpPresenter;

public interface RegisterMvpPresenter<V extends RegisterMvpView> extends MvpPresenter<V> {

    void UserRegister(AppCompatActivity context, String fullName, String email,
                      String password, String phone, int registerType,
                      int cityID, int countryID, String FB, String fcm_Token, String device_id, String deviceTypeID, int language);

    void getResponsOfCountryCityList(int Language);

    void getFacebookAccount(AppCompatActivity appCompatActivity);

    void loginWithFacebook(AppCompatActivity context, String socialId, String email, String fcm_token, String device_id,
                           String deviceTypeID);

    void onActivityResult(int requestCode, int resultCode, Intent data);


}
