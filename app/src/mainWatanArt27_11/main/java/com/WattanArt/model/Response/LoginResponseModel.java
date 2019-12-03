package com.WattanArt.model.Response;

public class LoginResponseModel {

    /**
     * result : {"success":true,"obj":{"UserID":"548fbce4-ed0c-4493-92b9-8ac358dfd628","FullName":"AsiaAbdel Laateef","RegisterType":2,"Email":"test.nost@gmail.com","Phone":"0101852369","FB":"242961425179584610","Fcm_Token":null,"CountryID":1,"cityID":1,"Country":null,"City":null,"IsLockedOut":null,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},"Message":null,"State":1}
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
         * obj : {"UserID":"548fbce4-ed0c-4493-92b9-8ac358dfd628","FullName":"AsiaAbdel Laateef","RegisterType":2,"Email":"test.nost@gmail.com","Phone":"0101852369","FB":"242961425179584610","Fcm_Token":null,"CountryID":1,"cityID":1,"Country":null,"City":null,"IsLockedOut":null,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
         * Message : null
         * State : 1
         */

        private boolean success;
        private ObjEntity obj;
        private String Message;
        private int State;

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public void setObj(ObjEntity obj) {
            this.obj = obj;
        }

        public void setMessage(String Message) {
            this.Message = Message;
        }

        public void setState(int State) {
            this.State = State;
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

        public int getState() {
            return State;
        }

        public static class ObjEntity {
            /**
             * UserID : 548fbce4-ed0c-4493-92b9-8ac358dfd628
             * FullName : AsiaAbdel Laateef
             * RegisterType : 2
             * Email : test.nost@gmail.com
             * Phone : 0101852369
             * FB : 242961425179584610
             * Fcm_Token : null
             * CountryID : 1
             * cityID : 1
             * Country : null
             * City : null
             * IsLockedOut : null
             * Language : 0
             * UserIdValue : null
             * Base64 : null
             * Extension : null
             * SharedLink : null
             * PagesCount : 0
             * CurrentPage : 0
             * RowsPerPage : 10
             */

            private String UserID;
            private String FullName;
            private int RegisterType;
            private String Email;
            private String Phone;
            private String FB;
            private Object Fcm_Token;
            private int CountryID;
            private int cityID;
            private Object Country;
            private Object City;
            private Object IsLockedOut;
            private int Language;
            private Object UserIdValue;
            private Object Base64;
            private Object Extension;
            private Object SharedLink;
            private int PagesCount;
            private int CurrentPage;
            private int RowsPerPage;

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

            public void setCountry(Object Country) {
                this.Country = Country;
            }

            public void setCity(Object City) {
                this.City = City;
            }

            public void setIsLockedOut(Object IsLockedOut) {
                this.IsLockedOut = IsLockedOut;
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

            public Object getCountry() {
                return Country;
            }

            public Object getCity() {
                return City;
            }

            public Object getIsLockedOut() {
                return IsLockedOut;
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
}
