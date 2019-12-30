package com.WattanArt.ui.Coaster;

import com.WattanArt.ui.base.MvpPresenter;

import java.io.File;
import java.util.List;

public interface CoasterMvpPresenter <V extends CoasterMvpView> extends MvpPresenter<V> {
    void returnUploadedImage(List<String> list , File filePath);
    void getSubCategory(int language,int cateId);
}
