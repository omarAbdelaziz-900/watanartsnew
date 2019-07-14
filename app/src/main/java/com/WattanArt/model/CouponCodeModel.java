package com.WattanArt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 7/9/2018.
 */

public class CouponCodeModel {
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("CouponCode")
    @Expose
    private String couponCode;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
}
