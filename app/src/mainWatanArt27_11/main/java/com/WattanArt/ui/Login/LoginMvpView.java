package com.WattanArt.ui.Login;

import com.WattanArt.ui.base.MvpView;

public interface LoginMvpView extends MvpView{

    void openMainActivity(String userId);
    Boolean checkIfValid();
}
