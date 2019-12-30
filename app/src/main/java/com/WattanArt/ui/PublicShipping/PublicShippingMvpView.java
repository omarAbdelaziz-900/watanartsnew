package com.WattanArt.ui.PublicShipping;

import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderResponse;
import com.WattanArt.ui.base.MvpView;

public interface PublicShippingMvpView extends MvpView {
    void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList);
    void returnFromMakeOrder(MobileOrderResponse mobileOrderResponse);
    void applyCouponCode(boolean isValid);
    void applyAfterLogin();
    void returnUploadedImageForMobile(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForCover(ImageUploadResponseModel imageUploadResponseModel);
    void showLoadingInner();
    void hideLoadingInner();
}
