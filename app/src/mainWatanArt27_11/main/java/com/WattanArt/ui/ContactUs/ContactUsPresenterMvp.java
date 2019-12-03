package com.WattanArt.ui.ContactUs;

import com.WattanArt.ui.base.MvpPresenter;

/**
 * Created by Android Team on 1/18/2018.
 */

public interface ContactUsPresenterMvp<V extends ContactUsMvpView> extends MvpPresenter<V> {

    void getContactUsData(int lang);
    void sendFeedBack(String email,String subject,String message);

}
