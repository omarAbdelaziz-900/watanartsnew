package com.WattanArt.ui.ShippingForMobile;

import com.WattanArt.ui.base.MvpPresenter;

public interface ShippingMobileMvpPresenter <V extends ShippingMobileMvpView> extends MvpPresenter<V>{
    void getShippingPrices();
}
