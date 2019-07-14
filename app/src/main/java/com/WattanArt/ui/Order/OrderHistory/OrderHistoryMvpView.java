package com.WattanArt.ui.Order.OrderHistory;

import com.WattanArt.model.Response.AllOrdersHistoryResponseModel;
import com.WattanArt.model.Response.HomeIntroResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface OrderHistoryMvpView extends MvpView {

    void returnResponseData(AllOrdersHistoryResponseModel responseModel);

}
