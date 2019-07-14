package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class LoginRequestModel {

//    private int socialType;

    private String Name , Email,  DeviceToken ,DeviceID , DeviceTypeID  ,FB,password;
    int Language;

    public LoginRequestModel(String name, String email, String deviceToken
            ,String deviceID, String deviceTypeID, String FB, String password, int language) {
        Name = name;
        Email = email;
        DeviceToken = deviceToken;
        DeviceID = deviceID;
        DeviceTypeID = deviceTypeID;
        this.FB = FB;
        this.password = password;
        Language = language;
    }

    public LoginRequestModel(String FB, String email, String deviceToken
            ,String deviceID, String deviceTypeID) {

        Email = email;
        DeviceToken = deviceToken;
        DeviceID = deviceID;
        DeviceTypeID = deviceTypeID;
        this.FB = FB;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(String deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }

    public String getFB() {
        return FB;
    }

    public void setFB(String FB) {
        this.FB = FB;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLanguage() {
        return Language;
    }

    public void setLanguage(int language) {
        Language = language;
    }

/*public LoginRequestModel(int socialType, String name, String email, String fcm_Token, String socialId, String password) {
//        this.socialType = socialType;
        Name = name;
        Email = email;
        Fcm_Token = fcm_Token;
        this.FB = socialId;
        this.password=password;
    }*/

    /*public LoginRequestModel(String email, String password,int Language) {
        Email = email;
        this.password = password;
        this.Language = Language;
    }*/

//    public LoginRequestModel(String socialId) {
//        this.FB = socialId;
//    }

    /*public LoginRequestModel( String FB ,String email) {
        this.FB = FB;
        Email = email;
    }*/

    //    public int getSocialType() {
//        return socialType;
//    }
//
//    public void setSocialType(int socialType) {
//        this.socialType = socialType;
//    }

}
