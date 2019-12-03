package com.WattanArt.model.Response;

public class RegisterResponseModel {
    /**
     * result : {"success":true,"obj":{"UserID":"623fe944-5144-4ebb-b68c-c6a27766a67a","FullName":"aaaa","RegisterType":2,"Email":"ss@as.ss","Phone":"01098989888","FB":"1256953222","Fcm_Token":null,"CountryID":1,"cityID":1,"Language":0,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},"Message":null}
     * ISResultHasData : 1
     */

    private ResultEntity result;
    private int ISResultHasData;

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public static class ResultEntity {
        /**
         * success : true
         * obj : {"UserID":"623fe944-5144-4ebb-b68c-c6a27766a67a","FullName":"aaaa","RegisterType":2,"Email":"ss@as.ss","Phone":"01098989888","FB":"1256953222","Fcm_Token":null,"CountryID":1,"cityID":1,"Language":0,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
         * Message : null
         */

        private boolean success;
        private ObjEntity obj;
        private String Message;
        private int State;

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setObj(ObjEntity obj) {
            this.obj = obj;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public boolean getSuccess() {
            return success;
        }

        public ObjEntity getObj() {
            return obj;
        }

        public String getMessage() {
            return Message;
        }

        public static class ObjEntity {
            /**
             * UserID : 623fe944-5144-4ebb-b68c-c6a27766a67a
             * FullName : aaaa
             * RegisterType : 2
             * Email : ss@as.ss
             * Phone : 01098989888
             * FB : 1256953222
             * Fcm_Token : null
             * CountryID : 1
             * cityID : 1
             * Language : 0
             * Base64 : null
             * Extension : null
             * SharedLink : null
             * PagesCount : 0
             * CurrentPage : 0
             * RowsPerPage : 10
             * AvailableTime : 10
             * DiscountRate : 1.0
             */

            private String UserID;
            private String FullName;
            private int RegisterType;
            private String Email;
            private String Phone;
            private String FB;
            private String Promocode;
            private Object Fcm_Token;
            private int CountryID;
            private int cityID;
            private int Language;
            private Object Base64;
            private Object Extension;
            private Object SharedLink;
            private int PagesCount;
            private int CurrentPage;
            private int RowsPerPage;

            private int AvailableTime;
            private double DiscountRate;

            public void setUserID(String UserID) {
                this.UserID = UserID;
            }

            public void setFullName(String FullName) {
                this.FullName = FullName;
            }

            public void setRegisterType(int RegisterType) {
                this.RegisterType = RegisterType;
            }

            public void setEmail(String Email) {
                this.Email = Email;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public void setFB(String FB) {
                this.FB = FB;
            }

            public void setFcm_Token(Object Fcm_Token) {
                this.Fcm_Token = Fcm_Token;
            }

            public void setCountryID(int CountryID) {
                this.CountryID = CountryID;
            }

            public void setCityID(int cityID) {
                this.cityID = cityID;
            }

            public void setLanguage(int Language) {
                this.Language = Language;
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

            public String getUserID() {
                return UserID;
            }

            public String getFullName() {
                return FullName;
            }

            public int getRegisterType() {
                return RegisterType;
            }

            public String getEmail() {
                return Email;
            }

            public String getPhone() {
                return Phone;
            }

            public String getFB() {
                return FB;
            }

            public Object getFcm_Token() {
                return Fcm_Token;
            }

            public int getCountryID() {
                return CountryID;
            }

            public int getCityID() {
                return cityID;
            }

            public int getLanguage() {
                return Language;
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

            public String getPromocode() {
                return Promocode;
            }

            public void setPromocode(String promocode) {
                Promocode = promocode;
            }

            public int getAvailableTime() {
                return AvailableTime;
            }

            public void setAvailableTime(int availableTime) {
                AvailableTime = availableTime;
            }

            public double getDiscountRate() {
                return DiscountRate;
            }

            public void setDiscountRate(double discountRate) {
                DiscountRate = discountRate;
            }
        }
    }
}
