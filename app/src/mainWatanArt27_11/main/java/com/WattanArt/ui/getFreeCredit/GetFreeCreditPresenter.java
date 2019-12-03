package com.WattanArt.ui.getFreeCredit;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.Utils.config.Constants;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.About.AboutMvpView;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class GetFreeCreditPresenter<V extends IGetFreeCredit> extends BasePresenter<V>
        implements IGetFreeCreditPresenter<V> {

    private UserData userData;

    @Inject
    public GetFreeCreditPresenter(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getProfileData(AppCompatActivity context) {
        getMvpView().showLoading();
        userData = new UserData();
        String userId = userData.getUserID(context);
        Log.d("userId", userId + "");

        getCompositeDisposable().add(getDataManager().getProfileData(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseModel -> {
                            if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult() != null) {
                                getMvpView().returnProfileData(responseModel);
                            }
                            getMvpView().hideLoading();
                        }
                        , throwable -> {
                            if (!isViewAttached()) {
                                return;
                            }
                            getMvpView().hideLoading();
                            handleThrowableError(throwable);
                        }
                )

        );
    }
}
