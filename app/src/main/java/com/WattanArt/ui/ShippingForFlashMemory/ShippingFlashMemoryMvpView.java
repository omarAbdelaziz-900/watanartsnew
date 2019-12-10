package com.WattanArt.ui.ShippingForFlashMemory;

import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.ShippingForMobile.MobileOrderResponse;
import com.WattanArt.ui.base.MvpView;

public interface ShippingFlashMemoryMvpView  extends MvpView {
    void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList);
    void returnFromMakeOrder(MobileOrderResponse mobileOrderResponse);
    void applyCouponCode(boolean isValid);
    void applyAfterLogin();

    void returnUploadedImageForFront(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForCoverFront(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForBack(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForCoverBack(ImageUploadResponseModel imageUploadResponseModel);

    void showLoadingInner();
    void hideLoadingInner();
}
