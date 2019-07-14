package com.WattanArt.ui.Order.OrderDetails;

import com.WattanArt.model.Response.OrderDetailsResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface OrderDetailsMvpView extends MvpView {

    void returnResponseToView(OrderDetailsResponseModel responseModel);
}
