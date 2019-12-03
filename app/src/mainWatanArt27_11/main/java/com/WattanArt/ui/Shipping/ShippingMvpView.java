package com.WattanArt.ui.Shipping;

import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.AboutModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

import java.util.List;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface ShippingMvpView extends MvpView {
    void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList);
    void afterLogin();
    void baackPress(int orderId);
    void onDeleteAllImages();
    void onAddNewImages();
    void applyCouponCode(boolean isValid);
    void logOrderToAnalytics(ShippingRequest shippingRequest);
}
