package com.WattanArt.ui.FAQ;

import com.WattanArt.model.Response.FAQresponseModel;
import com.WattanArt.ui.base.MvpView;

public interface FAQMvpView extends MvpView {

    void returnResponseData(FAQresponseModel faQresponseModel);
}
