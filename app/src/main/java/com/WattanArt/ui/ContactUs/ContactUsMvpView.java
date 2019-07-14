package com.WattanArt.ui.ContactUs;

import com.WattanArt.model.Response.ContactUsModel;
import com.WattanArt.ui.base.MvpView;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface ContactUsMvpView extends MvpView {
    void UpdateInfoUi(ContactUsModel contactUsResult);
    void flagSuccessfullySend();

}
