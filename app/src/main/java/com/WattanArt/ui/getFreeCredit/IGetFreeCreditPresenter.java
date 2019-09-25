package com.WattanArt.ui.getFreeCredit;

import android.support.v7.app.AppCompatActivity;

import com.WattanArt.ui.About.AboutMvpView;
import com.WattanArt.ui.base.MvpPresenter;

public interface IGetFreeCreditPresenter<V extends IGetFreeCredit> extends MvpPresenter<V> {
    void getProfileData(AppCompatActivity context);

}
