package com.WattanArt.ui.PublicShipping;

import android.content.Context;
import android.widget.EditText;

import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.base.MvpPresenter;

import java.io.File;
import java.util.List;

public interface PublicShippingMvpPresenter <V extends PublicShippingMvpView> extends MvpPresenter<V> {
    void getShippingPrices();
    void getOrderSubmit(MobileOrderRequest mobileOrderRequest);
    boolean checkCouponCode(CouponCodeModel couponCodeModel);
    void showLoginPopup(Context context, boolean code, EditText copenText);
    void returnUploadedImageForMobile(List<String>list , File filePath);
    void returnUploadedImageForCover(List<String>list , File filePath);
}

