package com.WattanArt.ui.EditProfile;

import android.support.v7.app.AppCompatActivity;

import com.WattanArt.ui.EditProfile.EditProfileMvpView;
import com.WattanArt.ui.base.MvpPresenter;

public interface EditProfileMvpPresenter <V extends EditProfileMvpView> extends MvpPresenter <V> {

    void getProfileData(AppCompatActivity context);
    void sendUpdatedProfileData(String fullName,String email,String phone,
                                String UserID,int cityId,int countryId, int language);
    void getResponsOfCountryCityList(int Language);

    void changePassword(String oldPass,String newPass ,String userId);

}
