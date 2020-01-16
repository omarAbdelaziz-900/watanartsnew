package com.WattanArt.ui.FlashMemory;

import com.WattanArt.model.Response.ImageUploadResponseModel;
import com.WattanArt.ui.Category.CategoryMobileRsponseModel;
import com.WattanArt.ui.base.MvpView;

public interface FlashMemoryMvpView extends MvpView {
    void returnUploadedImage(ImageUploadResponseModel imageUploadResponseModel);
    void returnSubCategory(CategoryMobileRsponseModel responseModel);

    void returnUploadedImageForFront(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForCoverFront(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForBack(ImageUploadResponseModel imageUploadResponseModel);
    void returnUploadedImageForCoverBack(ImageUploadResponseModel imageUploadResponseModel);

    void showLoadingInner();
    void hideLoadingInner();
}
