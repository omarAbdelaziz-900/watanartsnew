package com.WattanArt.model.Response;

import java.util.List;

public class AllOrdersHistoryResponseModel {
    /**
     * result : [{"ID":9,"UserID":"rsgsfgfsrrt","ItemsCost":484,"ChargePrice":0,"ItemCount":1,"CountryID":2,"CityID":2,"Address1":"dgdstdrst","Address2":null,"Image":null,"zipcode":null,"State":1,"CouponCode":null,"DeliverdDate":null,"Comment":null,"PhoneNumber":null,"CreatedDate":"2018-04-08T14:52:01.96","buytype":null,"Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10},{"ID":7,"UserID":"rsgsfgfsrrt","ItemsCost":0,"ChargePrice":0,"ItemCount":1,"CountryID":1,"CityID":2,"Address1":"dgdstdrst","Address2":null,"Image":null,"zipcode":null,"State":0,"CouponCode":null,"DeliverdDate":null,"Comment":null,"PhoneNumber":null,"CreatedDate":"2018-03-08T11:32:33.783","buytype":null,"Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10}]
     * ISResultHasData : 1
     */

    private int ISResultHasData;
    private List<ResultEntity> result;

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        /**
         * ID : 9
         * UserID : rsgsfgfsrrt
         * ItemsCost : 484.0
         * ChargePrice : 0.0
         * ItemCount : 1
         * CountryID : 2
         * CityID : 2
         * Address1 : dgdstdrst
         * Address2 : null
         * Image : null
         * zipcode : null
         * State : 1
         * StateValue : wait
         * CouponCode : null
         * DeliverdDate : null
         * Comment : null
         * PhoneNumber : null
         * CreatedDate : 2018-04-08T14:52:01.96
         * buytype : null
         * Language : 1
         * UserIdValue : null
         * Base64 : null
         * Extension : null
         * SharedLink : null
         * PagesCount : 1
         * CurrentPage : 0
         * RowsPerPage : 10
         */

        private int ID;
        private String UserID;
        private double ItemsCost;
        private double ChargePrice;
        private int ItemCount;
        private int CountryID;
        private int CityID;
        private String Address1;
        private Object Address2;
        private Object Image;
        private Object zipcode;
        private int State;


        private String StateValue;
        private Object CouponCode;
        private Object DeliverdDate;
        private Object Comment;
        private Object PhoneNumber;
        private String CreatedDate;
        private Object buytype;
        private int Language;
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;

        public void setID(int ID) {
            this.ID = ID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public void setItemsCost(double ItemsCost) {
            this.ItemsCost = ItemsCost;
        }

        public void setChargePrice(double ChargePrice) {
            this.ChargePrice = ChargePrice;
        }

        public void setItemCount(int ItemCount) {
            this.ItemCount = ItemCount;
        }

        public void setCountryID(int CountryID) {
            this.CountryID = CountryID;
        }

        public void setCityID(int CityID) {
            this.CityID = CityID;
        }

        public void setAddress1(String Address1) {
            this.Address1 = Address1;
        }

        public void setAddress2(Object Address2) {
            this.Address2 = Address2;
        }

        public void setImage(Object Image) {
            this.Image = Image;
        }

        public void setZipcode(Object zipcode) {
            this.zipcode = zipcode;
        }

        public void setState(int State) {
            this.State = State;
        }

        public void setCouponCode(Object CouponCode) {
            this.CouponCode = CouponCode;
        }

        public void setDeliverdDate(Object DeliverdDate) {
            this.DeliverdDate = DeliverdDate;
        }

        public void setComment(Object Comment) {
            this.Comment = Comment;
        }

        public void setPhoneNumber(Object PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public void setBuytype(Object buytype) {
            this.buytype = buytype;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public void setUserIdValue(Object UserIdValue) {
            this.UserIdValue = UserIdValue;
        }

        public void setBase64(Object Base64) {
            this.Base64 = Base64;
        }

        public void setExtension(Object Extension) {
            this.Extension = Extension;
        }

        public void setSharedLink(Object SharedLink) {
            this.SharedLink = SharedLink;
        }

        public void setPagesCount(int PagesCount) {
            this.PagesCount = PagesCount;
        }

        public void setCurrentPage(int CurrentPage) {
            this.CurrentPage = CurrentPage;
        }

        public void setRowsPerPage(int RowsPerPage) {
            this.RowsPerPage = RowsPerPage;
        }

        public int getID() {
            return ID;
        }

        public String getUserID() {
            return UserID;
        }

        public double getItemsCost() {
            return ItemsCost;
        }

        public double getChargePrice() {
            return ChargePrice;
        }

        public int getItemCount() {
            return ItemCount;
        }

        public int getCountryID() {
            return CountryID;
        }

        public int getCityID() {
            return CityID;
        }

        public String getAddress1() {
            return Address1;
        }

        public Object getAddress2() {
            return Address2;
        }

        public Object getImage() {
            return Image;
        }

        public Object getZipcode() {
            return zipcode;
        }

        public int getState() {
            return State;
        }

        public String getStateValue() {
            return StateValue;
        }

        public void setStateValue(String stateValue) {
            StateValue = stateValue;
        }

        public Object getCouponCode() {
            return CouponCode;
        }

        public Object getDeliverdDate() {
            return DeliverdDate;
        }

        public Object getComment() {
            return Comment;
        }

        public Object getPhoneNumber() {
            return PhoneNumber;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public Object getBuytype() {
            return buytype;
        }

        public int getLanguage() {
            return Language;
        }

        public Object getUserIdValue() {
            return UserIdValue;
        }

        public Object getBase64() {
            return Base64;
        }

        public Object getExtension() {
            return Extension;
        }

        public Object getSharedLink() {
            return SharedLink;
        }

        public int getPagesCount() {
            return PagesCount;
        }

        public int getCurrentPage() {
            return CurrentPage;
        }

        public int getRowsPerPage() {
            return RowsPerPage;
        }
    }
}
