package com.WattanArt.Utils.SharedPrefTool;

import android.content.Context;


public class UserData {


    public UserData() {
    }

    public String TAG_SAVE_LOGIN_DATA = "login_data";
    public String TAG_SAVE_USER_TYPE = "user_type";
    public String TAG_SAVE_USER_ID = "user_id";
    public String TAG_USER_MOBILE = "user_phone";
    public String TAG_LOGIN = "login";
    public String TAG_LANGUAGE_SCREEN = "language";
    public String TAG_LOCALIZATION = "localization";
    public String TAG_KEY_NAME = "key_name";
    public String TAG_VALUE_NAME = "value_name";
    public String TAG_REMEMBER_ME = "remmeber_me";
    public String TAG_SAVE_PROFILE_INFO = "profile_info";



    public void saveUserObject(Context context, Object myObject, boolean rememberMe) {
        SharedPreferencesTool.saveObject(context, TAG_SAVE_LOGIN_DATA, myObject);
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }


    public boolean getRemmemberMe(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_REMEMBER_ME);
    }

    public void setRemmemberMe(Context context, boolean rememberMe) {
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

    public void saveProfileObject(Context context, Object myObject, boolean rememberMe) {
        SharedPreferencesTool.saveObject(context, TAG_SAVE_PROFILE_INFO, myObject);
        SharedPreferencesTool.setBoolean(context, rememberMe, TAG_REMEMBER_ME);
    }

    public void saveLoginObject(Context context, Object myObject) {
        SharedPreferencesTool.saveObject(context, TAG_LOGIN, myObject);
    }

//    public UserLoggedModel getLoginObject(Context context) {
//        return SharedPreferencesTool.getObject(context, TAG_LOGIN, UserLoggedModel.class);
//    }


    public void saveLocalization(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_LOCALIZATION, value);
    }


    public int getLocalization(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_LOCALIZATION);
    }

    public void savePhone(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_USER_MOBILE, value);
    }


    public String getPhone(Context context) {
        return SharedPreferencesTool.getString(context, TAG_USER_MOBILE);
    }


    public void saveKeyName(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_KEY_NAME, value);
    }


    public String getKeyName(Context context) {
        return SharedPreferencesTool.getString(context, TAG_KEY_NAME);
    }

    public void saveValueName(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_VALUE_NAME, value);
    }



    public String getValueName(Context context) {
        return SharedPreferencesTool.getString(context, TAG_VALUE_NAME);
    }

    //1 for useual user
    //2 for faceRegister
    public void saveUserType(Context context, int value) {
        SharedPreferencesTool.setInt(context, TAG_SAVE_USER_TYPE, value);
    }

    public int getUserType(Context context) {
        return SharedPreferencesTool.getInt(context, TAG_SAVE_USER_TYPE);
    }

    public void saveUserID(Context context, String value) {
        SharedPreferencesTool.setString(context, TAG_SAVE_USER_ID, value);
    }

    public String getUserID(Context context) {
        return SharedPreferencesTool.getString(context, TAG_SAVE_USER_ID);
    }

    public void clearShared(Context context) {
//        String sharedName = context.getPackageName();
        SharedPreferencesTool.clearObject(context);
    }

    public void saveLanguageScreen(Context context, boolean isShown) {
        SharedPreferencesTool.setBoolean(context, isShown, TAG_LANGUAGE_SCREEN);
    }

    public boolean getLanguageScreen(Context context) {
        return SharedPreferencesTool.getBoolean(context, TAG_LANGUAGE_SCREEN);
    }
}
