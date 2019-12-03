package com.WattanArt.ui.ShippingForMobile;

import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface ShippingMobileMvpView  extends MvpView {
    void returnPatternList(SelectCountryCitiyListsResponseModel.Result patternTypeEntityList);
}
