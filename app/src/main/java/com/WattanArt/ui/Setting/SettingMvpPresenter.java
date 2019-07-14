package com.WattanArt.ui.Setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.WattanArt.ui.base.MvpPresenter;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public interface SettingMvpPresenter<V extends SettingMvpView> extends MvpPresenter<V> {

    void onLogout(JsonObject userID);


}
