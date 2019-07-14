package com.WattanArt.model.Request;

public class RegisterRequestModel {
    /**
     * FullName : ahmed Ali
     * Email : ahmed.mo@700apps.com
     * Password : 123456
     * Phone : 012312312
     * RegisterType : 1
     * cityID : 1
     * CountryID : 2
     * FB :
     * DeviceToken :
     * Language : 1
     */

    private String FullName;
    private String Email;
    private String Password;
    private String Phone;
    private int RegisterType;
    private int cityID;
    private int CountryID;
    private String FB;
    private String DeviceToken;
    private String DeviceID;
    private String DeviceTypeID;
    private int Language;

    public RegisterRequestModel(String fullName, String email, String password, String phone, int registerType,
                                int cityID, int countryID, String FB, String DeviceToken, String DeviceID,
                                String DeviceTypeID, int language) {
        FullName = fullName;
        Email = email;
        Password = password;
        Phone = phone;
        RegisterType = registerType;
        this.cityID = cityID;
        CountryID = countryID;
        this.FB = FB;
        this.DeviceToken = DeviceToken;
        this.DeviceID = DeviceID;
        this.DeviceTypeID = DeviceTypeID;
        Language = language;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setRegisterType(int RegisterType) {
        this.RegisterType = RegisterType;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCountryID(int CountryID) {
        this.CountryID = CountryID;
    }

    public void setFB(String FB) {
        this.FB = FB;
    }

    public void setDeviceToken(String DeviceToken) {
        this.DeviceToken = DeviceToken;
    }

    public void setLanguage(int Language) {
        this.Language = Language;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }

    public String getPhone() {
        return Phone;
    }

    public int getRegisterType() {
        return RegisterType;
    }

    public int getCityID() {
        return cityID;
    }

    public int getCountryID() {
        return CountryID;
    }

    public String getFB() {
        return FB;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public int getLanguage() {
        return Language;
    }
}
