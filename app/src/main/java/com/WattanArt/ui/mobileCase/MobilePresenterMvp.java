package com.WattanArt.ui.mobileCase;

import android.content.Context;
import android.widget.EditText;

import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.ui.base.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

public interface MobilePresenterMvp <V extends MobileMvpView> extends MvpPresenter<V> {

    void uploadImage();
    void segmentImage(List<String> listPaths);
    void uploadAllRequest();
    void cropImages(Context context , List<ImageModel> imageModels , int cityId , int countryid, EditText code , int buyType);
    void getShippingPrices();
    void showLoginPopup(Context context, List<ImageModel> imageModels, int city, int country, int buyType);
    void saveUserData();
    void checkImageHasLowResolution(Context context,List<ImageModel> imageModels, int city, int country,  int buyType);
    void showDeleteAllDialog(Context context);
    void addNewImagesToOrder(Context context , ArrayList<String> photosList);
}
