package com.WattanArt.ui.mobileCase;

import com.WattanArt.model.Request.ShippingRequest;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface MobileMvpView extends MvpView {
   void returnUploadedImage(ImageUploadResponseModel imageUploadResponseModel);
}
