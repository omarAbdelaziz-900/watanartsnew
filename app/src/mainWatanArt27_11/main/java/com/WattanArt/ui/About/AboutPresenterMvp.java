package com.WattanArt.ui.About;

import com.WattanArt.ui.base.MvpPresenter;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface AboutPresenterMvp<V extends AboutMvpView> extends MvpPresenter<V> {

    void getAboutUsData(int lang);
    void getTermsAndConditions(int lang);
}
