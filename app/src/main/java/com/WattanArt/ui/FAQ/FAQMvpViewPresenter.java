package com.WattanArt.ui.FAQ;

import com.WattanArt.ui.base.MvpPresenter;

public interface FAQMvpViewPresenter <V extends FAQMvpView> extends MvpPresenter<V> {

    void getFAQ(int language);
}
