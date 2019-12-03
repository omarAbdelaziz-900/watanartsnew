package com.WattanArt.ui.Order.OrderDetails;

import com.WattanArt.ui.base.BasePresenter;
import com.WattanArt.ui.base.MvpPresenter;

public interface OrderDetailsMvpPresenter <V extends OrderDetailsMvpView > extends MvpPresenter<V> {


    void getResponse(int language, String id);
}
