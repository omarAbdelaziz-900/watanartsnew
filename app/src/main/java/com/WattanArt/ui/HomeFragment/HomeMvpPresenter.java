package com.WattanArt.ui.HomeFragment;

import com.WattanArt.ui.base.MvpPresenter;

public interface HomeMvpPresenter<V extends HomeMvpView> extends MvpPresenter<V> {


    void getIntroData(int language);
}
