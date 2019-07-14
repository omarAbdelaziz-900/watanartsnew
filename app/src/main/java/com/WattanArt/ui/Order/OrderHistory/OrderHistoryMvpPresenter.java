package com.WattanArt.ui.Order.OrderHistory;

import com.WattanArt.model.Request.AllOrdersHistoryRequestModel;
import com.WattanArt.ui.base.MvpPresenter;

public interface OrderHistoryMvpPresenter <V extends OrderHistoryMvpView> extends MvpPresenter<V> {


    void getLListOfOrders(AllOrdersHistoryRequestModel allOrdersHistoryRequestModel);
}
