package com.WattanArt.ui.CanvasPrint;

import com.WattanArt.model.Response.CanvasPrintResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface CanvasPrintMvpView extends MvpView {


    void returnData(CanvasPrintResponseModel responseModel);


}
