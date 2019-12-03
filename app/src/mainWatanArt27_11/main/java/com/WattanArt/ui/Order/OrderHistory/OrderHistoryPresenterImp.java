package com.WattanArt.ui.Order.OrderHistory;

import com.WattanArt.model.Request.AllOrdersHistoryRequestModel;
import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderHistoryPresenterImp<V extends OrderHistoryMvpView> extends BasePresenter<V> implements OrderHistoryMvpPresenter<V> {

    @Inject
    public OrderHistoryPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getLListOfOrders(AllOrdersHistoryRequestModel allOrdersHistoryRequestModel) {
        getMvpView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getAllOrderHistory(allOrdersHistoryRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AllOrdersHistoryResponseModel>() {
                               @Override
                               public void accept(AllOrdersHistoryResponseModel allOrdersHistoryResponseModel)
                                       throws Exception {
                                   if (allOrdersHistoryResponseModel != null &&
                                           allOrdersHistoryResponseModel.getISResultHasData() == 1 &&
                                           allOrdersHistoryResponseModel.getResult() != null) {

                                       getMvpView().returnResponseData(allOrdersHistoryResponseModel);

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
