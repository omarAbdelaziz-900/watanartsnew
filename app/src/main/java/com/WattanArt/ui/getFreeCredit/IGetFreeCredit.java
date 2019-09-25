package com.WattanArt.ui.getFreeCredit;

import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface IGetFreeCredit extends MvpView {
    void returnProfileData(RegisterResponseModel responseModel);
}
