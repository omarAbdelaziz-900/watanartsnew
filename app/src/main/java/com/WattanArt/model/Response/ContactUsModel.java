package com.WattanArt.model.Response;

/**
 * Created by Android Team on 1/18/2018.
 */

public class ContactUsModel {

    /**
     * result : {"WebsiteLink":"ytjtyjytjty","Latitude":"14.5565","Longtiude":"22.656","EMail":"FGDGDFG","Mobile":null,"Fax":null,"PhoneNumber":"ryuryutyuru","FacebookUrl":"RRERT","TwitterUrl":"ETERYTEYE","LinkedInUrl":"rgrgrg","YouTubeUrl":"dffsdfsdf","AndriodAppUrl":"tjtjtjtyj","IosAppUrl":"ytjtyjtyjty","InstagramUrl":"ERTYERYRTRE","Description":"rerert","Address":"fgfgf","Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
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
         * WebsiteLink : ytjtyjytjty
         * Latitude : 14.5565
         * Longtiude : 22.656
         * EMail : FGDGDFG
         * Mobile : null
         * Fax : null
         * PhoneNumber : ryuryutyuru
         * FacebookUrl : RRERT
         * TwitterUrl : ETERYTEYE
         * LinkedInUrl : rgrgrg
         * YouTubeUrl : dffsdfsdf
         * AndriodAppUrl : tjtjtjtyj
         * IosAppUrl : ytjtyjtyjty
         * InstagramUrl : ERTYERYRTRE
         * Description : rerert
         * Address : fgfgf
         * Language : 1
         * UserIdValue : null
         * Base64 : null
         * Extension : null
         * SharedLink : null
         * PagesCount : 0
         * CurrentPage : 0
         * RowsPerPage : 10
         */

        private String WebsiteLink;
        private String Latitude;
        private String Longtiude;
        private String EMail;
        private Object Mobile;
        private Object Fax;
        private String PhoneNumber;
        private String FacebookUrl;
        private String TwitterUrl;
        private String LinkedInUrl;
        private String YouTubeUrl;
        private String AndriodAppUrl;
        private String IosAppUrl;
        private String InstagramUrl;
        private String Description;
        private String Address;
        private int Language;
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;

        public void setWebsiteLink(String WebsiteLink) {
            this.WebsiteLink = WebsiteLink;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public void setLongtiude(String Longtiude) {
            this.Longtiude = Longtiude;
        }

        public void setEMail(String EMail) {
            this.EMail = EMail;
        }

        public void setMobile(Object Mobile) {
            this.Mobile = Mobile;
        }

        public void setFax(Object Fax) {
            this.Fax = Fax;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public void setFacebookUrl(String FacebookUrl) {
            this.FacebookUrl = FacebookUrl;
        }

        public void setTwitterUrl(String TwitterUrl) {
            this.TwitterUrl = TwitterUrl;
        }

        public void setLinkedInUrl(String LinkedInUrl) {
            this.LinkedInUrl = LinkedInUrl;
        }

        public void setYouTubeUrl(String YouTubeUrl) {
            this.YouTubeUrl = YouTubeUrl;
        }

        public void setAndriodAppUrl(String AndriodAppUrl) {
            this.AndriodAppUrl = AndriodAppUrl;
        }

        public void setIosAppUrl(String IosAppUrl) {
            this.IosAppUrl = IosAppUrl;
        }

        public void setInstagramUrl(String InstagramUrl) {
            this.InstagramUrl = InstagramUrl;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public void setAddress(String Address) {
            this.Address = Address;
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

        public String getWebsiteLink() {
            return WebsiteLink;
        }

        public String getLatitude() {
            return Latitude;
        }

        public String getLongtiude() {
            return Longtiude;
        }

        public String getEMail() {
            return EMail;
        }

        public Object getMobile() {
            return Mobile;
        }

        public Object getFax() {
            return Fax;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public String getFacebookUrl() {
            return FacebookUrl;
        }

        public String getTwitterUrl() {
            return TwitterUrl;
        }

        public String getLinkedInUrl() {
            return LinkedInUrl;
        }

        public String getYouTubeUrl() {
            return YouTubeUrl;
        }

        public String getAndriodAppUrl() {
            return AndriodAppUrl;
        }

        public String getIosAppUrl() {
            return IosAppUrl;
        }

        public String getInstagramUrl() {
            return InstagramUrl;
        }

        public String getDescription() {
            return Description;
        }

        public String getAddress() {
            return Address;
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
