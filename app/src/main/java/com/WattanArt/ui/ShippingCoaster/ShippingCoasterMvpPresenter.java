package com.WattanArt.ui.ShippingCoaster;

import android.content.Context;
import android.widget.EditText;

import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderRequest;
import com.WattanArt.ui.base.MvpPresenter;

import java.io.File;
import java.util.List;

public interface ShippingCoasterMvpPresenter <V extends ShippingCoasterMvpView> extends MvpPresenter<V> {
    void getShippingPrices();
    void getOrderSubmit(MobileOrderRequest mobileOrderRequest);
    boolean checkCouponCode(CouponCodeModel couponCodeModel);

    void showLoginPopup(Context context, boolean code, EditText copenText);
    void returnUploadedImageForFront(List<String> list , File filePath);
    void returnUploadedImageForCoverFront(List<String>list , File filePath);
    void returnUploadedImageForBack(List<String>list , File filePath);
    void returnUploadedImageForCoverBack(List<String>list , File filePath);
}
