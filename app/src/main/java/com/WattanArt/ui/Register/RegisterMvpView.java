package com.WattanArt.ui.Register;

import android.content.Intent;

import com.WattanArt.Utils.social_network.FacebookIntegrationTool;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

/**
 * Created by khaled badawy on 4/12/2018.
 *
 */

public interface
RegisterMvpView extends MvpView {

//    void showErrorDialog(int error);


    void openHomeActivity(String userId);

    void returnList(SelectCountryCitiyListsResponseModel.Result responseList);

    void returnFacebookRegistrationData
            (String Name, String LastName, String faceId);

    Boolean checkIfValid();

}
