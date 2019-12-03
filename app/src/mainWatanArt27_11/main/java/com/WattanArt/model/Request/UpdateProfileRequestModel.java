package com.WattanArt.model.Request;

public class UpdateProfileRequestModel {
    /**
     * FullName : ahmed_Alioo
     * UserID : 6468f83d-c5f6-4009-9a29-3f0cdce91982
     * Email : ahmed112@700apps.com
     * Phone : 255552
     * cityID : 1
     * CountryID : 2
     * Language : 1
     */

    private String FullName;
    private String UserID;
    private String Email;
    private String Phone;
    private int cityID;
    private int CountryID;
    private int Language;

    public UpdateProfileRequestModel(String fullName,  String email,
                                     String phone,String userID, int cityID, int countryID, int language) {
        FullName = fullName;
        UserID = userID;
        Email = email;
        Phone = phone;
        this.cityID = cityID;
        CountryID = countryID;
        Language = language;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setCountryID(int CountryID) {
        this.CountryID = CountryID;
    }

    public void setLanguage(int Language) {
        this.Language = Language;
    }

    public String getFullName() {
        return FullName;
    }

    public String getUserID() {
        return UserID;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public int getCityID() {
        return cityID;
    }

    public int getCountryID() {
        return CountryID;
    }

    public int getLanguage() {
        return Language;
    }
}
