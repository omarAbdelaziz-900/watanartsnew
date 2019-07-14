package com.WattanArt.ui.Shipping;

import android.content.Context;
import android.widget.EditText;

import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface ShippingPresenterMvp<V extends ShippingMvpView> extends MvpPresenter<V> {

    void uploadImage();
    void segmentImage(List<String> listPaths);
    void uploadAllRequest();
    void cropImages(Context context , List<ImageModel> imageModels , EditText address, EditText phone, int cityId , int countryid, EditText code , int buyType);
    void getShippingPrices();
    void showLoginPopup(Context context, List<ImageModel> imageModels, EditText address,
                        EditText phone, int city, int country, EditText code, int buyType);
    void saveUserData();
    void checkImageHasLowResolution(Context context,List<ImageModel> imageModels, EditText address , EditText phone, int city, int country, EditText code, int buyType);
    void showDeleteAllDialog(Context context);
    void addNewImagesToOrder(Context context , ArrayList<String> photosList);
    boolean checkOrderQuantity(List<ImageModel> imageModels);
    boolean checkCouponCode(CouponCodeModel couponCodeModel);
}
