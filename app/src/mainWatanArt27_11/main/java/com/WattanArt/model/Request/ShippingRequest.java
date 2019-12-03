package com.WattanArt.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by khaled.badawy on 5/8/2018.
 */

public class ShippingRequest {
    @SerializedName("order")
    @Expose
    private Order order;

    @SerializedName("orderDetils")
    @Expose
    private List<OrderDetailsItem> orderDetils = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDetailsItem> getOrderDetils() {
        return orderDetils;
    }

    public void setOrderDetils(List<OrderDetailsItem> orderDetils) {
        this.orderDetils = orderDetils;
    }

    public static class Order {

        @SerializedName("userID")
        @Expose
        private String userID;

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        @SerializedName("PhoneNumber")
        @Expose
        private String PhoneNumber;
        @SerializedName("countryID")
        @Expose
        private Integer countryID;
        @SerializedName("cityID")
        @Expose
        private Integer cityID;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("couponCode")
        @Expose
        private String couponCode;
        @SerializedName("buytype")
        @Expose
        private Integer buytype;

        @SerializedName("InEgypt")
        @Expose
        private boolean inEgypt;

        public boolean isInEgypt() {
            return inEgypt;
        }

        public void setInEgypt(boolean inEgypt) {
            this.inEgypt = inEgypt;
        }

        public boolean isValidCouponCode() {
            return ValidCouponCode;
        }

        public void setValidCouponCode(boolean validCouponCode) {
            ValidCouponCode = validCouponCode;
        }

        @SerializedName("ValidCouponCode")
        @Expose
        private boolean ValidCouponCode;

        public Order() {
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public Integer getCountryID() {
            return countryID;
        }

        public void setCountryID(Integer countryID) {
            this.countryID = countryID;
        }

        public Integer getCityID() {
            return cityID;
        }

        public void setCityID(Integer cityID) {
            this.cityID = cityID;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public Integer getBuytype() {
            return buytype;
        }

        public void setBuytype(Integer buytype) {
            this.buytype = buytype;
        }

    }
}


