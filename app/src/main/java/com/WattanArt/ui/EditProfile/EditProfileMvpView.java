package com.WattanArt.ui.EditProfile;

import com.WattanArt.model.Response.InstagramResponse;
import com.WattanArt.model.Response.RegisterResponseModel;
import com.WattanArt.model.Response.SelectCountryCitiyListsResponseModel;
import com.WattanArt.ui.base.MvpView;

public interface EditProfileMvpView extends MvpView {

//    void UpdateProfile(String fullName,String email,String password,String phone,int cityId,int countryId, int language);
    void returnProfileData(RegisterResponseModel responseModel);
    void returnCountryCityList(SelectCountryCitiyListsResponseModel.Result responseList);
    Boolean checkIfValid();
    void openHomeActivity();

}
