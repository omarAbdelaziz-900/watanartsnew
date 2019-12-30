package com.WattanArt.ui.mobileCase;

import android.content.Context;
import android.widget.EditText;

import com.WattanArt.model.AppModels.ImageModel;
import com.WattanArt.model.CouponCodeModel;
import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.base.MvpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface MobileMvpPresenter<V extends MobileMvpView> extends MvpPresenter<V> {
    void returnUploadedImageForMobile(List<String>list , File filePath);
    void returnUploadedImageForCover(List<String>list , File filePath);

}
