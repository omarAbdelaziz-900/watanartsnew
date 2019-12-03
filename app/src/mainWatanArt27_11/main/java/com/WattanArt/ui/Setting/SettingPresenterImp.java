package com.WattanArt.ui.Setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.UtilitiesManager;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.Utils.social_network.FacebookIntegrationTool;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Request.RegisterRequestModel;
import com.WattanArt.model.Response.GeneralResponse;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.model.Response.SocialUserModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.Register.RegisterMvpPresenter;
import com.WattanArt.ui.Register.RegisterMvpView;
import com.WattanArt.ui.base.BasePresenter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SettingPresenterImp<V extends SettingMvpView> extends BasePresenter<V>
        implements SettingMvpPresenter<V> {


    @Inject
    public SettingPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void onLogout(JsonObject userID) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager()
                .Logout(userID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GeneralResponse>() {
                               @Override
                               public void accept(GeneralResponse responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 &&
                                           responseModel.isResult()) {

                                       getMvpView().openHomeActivity();
                                   }


                                   getMvpView().hideLoading();
                               }
                           }
                        , new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                if (!isViewAttached()) {
                                    return;
                                }
                                getMvpView().hideLoading();
                                handleThrowableError(throwable);

                            }
                        }
                )

        );
    }
}
