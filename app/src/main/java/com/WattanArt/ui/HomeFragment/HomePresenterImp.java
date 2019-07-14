package com.WattanArt.ui.HomeFragment;

import com.WattanArt.model.Request.LanguageRequestModel;
import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenterImp <V extends HomeMvpView > extends BasePresenter<V> implements HomeMvpPresenter<V>{

    @Inject
    public HomePresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void getIntroData(int language) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getHomeIntro(language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeIntroResponseModel>() {
                               @Override
                               public void accept(HomeIntroResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult()!=null ) {
                                       getMvpView().returnIntroData(responseModel);
                                       //
                                   } else if (responseModel != null && responseModel.getResult() != null) {

                                       getMvpView().showMessage(null);
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
