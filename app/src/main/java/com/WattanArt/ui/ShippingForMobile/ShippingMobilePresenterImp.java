package com.WattanArt.ui.ShippingForMobile;

import com.WattanArt.MyApplication;
import com.WattanArt.R;
import com.WattanArt.Utils.SharedPrefTool.UserData;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.Shipping.ShippingPresenter;
import com.WattanArt.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ShippingMobilePresenterImp <V extends ShippingMobileMvpView> extends BasePresenter<V> implements ShippingMobileMvpPresenter<V>  {

    List<SelectCountryCitiyListsResponseModel.Result.PatternTypeBean> patternTypeEntityList;
    UserData userData = new UserData();

    @Inject
    public ShippingMobilePresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


        @Override
        public void getShippingPrices() {
            getMvpView().showLoading();
            getCompositeDisposable().add(getDataManager().
                    getCountries_citiesList(userData.getLocalization(MyApplication.getAppContext()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(selectCountryCitiyListsResponseModel -> {
                        if (selectCountryCitiyListsResponseModel != null && selectCountryCitiyListsResponseModel.getISResultHasData() == 1) {
                            this.patternTypeEntityList = selectCountryCitiyListsResponseModel.getResult().getPatternType();
                            ShippingMobilePresenterImp.this.getMvpView().returnPatternList(selectCountryCitiyListsResponseModel.getResult());
                        } else {
                            ShippingMobilePresenterImp.this.getMvpView().showMessage(R.string.some_error);
                        }
                        getMvpView().hideLoading();
                    }, throwable -> {
                        if (!isViewAttached()) {
                            return;
                        }

                        getMvpView().hideLoading();

                        handleThrowableError(throwable);
                    }));
        }


}
