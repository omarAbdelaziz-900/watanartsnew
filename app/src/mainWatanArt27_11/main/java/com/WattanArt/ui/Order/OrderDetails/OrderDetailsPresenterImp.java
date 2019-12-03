package com.WattanArt.ui.Order.OrderDetails;

import com.WattanArt.model.Request.LoginRequestModel;
import com.WattanArt.model.Response.LoginResponseModel;
import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.network.AppDataManager;
import com.WattanArt.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsPresenterImp<V extends OrderDetailsMvpView> extends BasePresenter<V> implements OrderDetailsMvpPresenter<V> {

    @Inject
    public OrderDetailsPresenterImp(AppDataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getResponse(int language, String id) {
        getMvpView().showLoading();

        getCompositeDisposable().add(getDataManager().getOrderDetailsData(language, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderDetailsResponseModel>() {
                               @Override
                               public void accept(OrderDetailsResponseModel responseModel) throws Exception {
                                   if (responseModel != null && responseModel.getISResultHasData() == 1 && responseModel.getResult()!=null) {

                                       getMvpView().returnResponseToView(responseModel);
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
