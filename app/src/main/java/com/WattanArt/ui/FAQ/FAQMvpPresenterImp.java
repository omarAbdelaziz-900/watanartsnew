package com.WattanArt.ui.FAQ;

import com.WattanArt.model.Request.FAQrequestModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.FAQresponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FAQMvpPresenterImp<V extends FAQMvpView> extends BasePresenter<V> implements FAQMvpViewPresenter<V> {
  @Inject
    public FAQMvpPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getFAQ(int language) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getFaQ(new FAQrequestModel(language))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FAQresponseModel>() {
                               @Override
                               public void accept(FAQresponseModel faQresponseModel)
                                       throws Exception {
                                   if (faQresponseModel != null &&
                                           faQresponseModel.getISResultHasData() == 1 &&
                                           faQresponseModel.getResult() != null) {

                                       getMvpView().returnResponseData(faQresponseModel);

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


