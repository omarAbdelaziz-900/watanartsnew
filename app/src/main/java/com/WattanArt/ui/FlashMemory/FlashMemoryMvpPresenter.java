package com.WattanArt.ui.FlashMemory;

import com.WattanArt.ui.base.MvpPresenter;

import java.io.File;
import java.util.List;

public interface FlashMemoryMvpPresenter <V extends FlashMemoryMvpView> extends MvpPresenter<V> {
    void returnUploadedImage(List<String> list , File filePath);
    void getSubCategory(int language,int cateId);
}
