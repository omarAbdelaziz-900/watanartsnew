package com.WattanArt.ui.Category;

import com.WattanArt.ui.base.MvpPresenter;

public interface CategoryMvpPresenter <V extends CategoryMvpView> extends MvpPresenter<V> {


    void getSubCategory(int language,int cateId);
}
