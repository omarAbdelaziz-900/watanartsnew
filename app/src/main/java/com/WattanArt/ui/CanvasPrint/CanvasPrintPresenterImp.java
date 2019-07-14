package com.WattanArt.ui.CanvasPrint;

import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CanvasPrintPresenterImp <V extends CanvasPrintMvpView > extends BasePresenter<V> implements CanvasPrintMvpPresnter<V> {
    @Inject
    public CanvasPrintPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getData(int Language) {


        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getConvasPrintData(Language)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CanvasPrintResponseModel>() {
                               @Override
                               public void accept(CanvasPrintResponseModel selectCountryCitiyListsResponseModel)
                                       throws Exception {
                                   if (selectCountryCitiyListsResponseModel != null &&
                                           selectCountryCitiyListsResponseModel.getISResultHasData() == 1 &&
                                           selectCountryCitiyListsResponseModel.getResult() != null) {

                                       getMvpView().returnData(selectCountryCitiyListsResponseModel);

                                   } else {
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
