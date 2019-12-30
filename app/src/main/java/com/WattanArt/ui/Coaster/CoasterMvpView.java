package com.WattanArt.ui.Coaster;

import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.base.MvpView;

public interface CoasterMvpView extends MvpView {
    void returnUploadedImage(ImageUploadResponseModel imageUploadResponseModel);
    void returnSubCategory(CategoryMobileRsponseModel responseModel);
}
