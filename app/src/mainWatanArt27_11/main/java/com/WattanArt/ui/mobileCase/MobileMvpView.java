package com.WattanArt.ui.mobileCase;

import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface MobileMvpView extends MvpView {
   void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList);
    void afterLogin();
    void baackPress(int orderId);
    void onDeleteAllImages();
    void onAddNewImages();
    void logOrderToAnalytics(ShippingRequest shippingRequest);
}
