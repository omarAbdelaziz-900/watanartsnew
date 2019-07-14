package com.WattanArt.model.Response;

import java.util.List;

public class HomeIntroResponseModel {
    /**
     * result : {"ImageList":["20180227125243635961.png","20171019163446912963.jpg","20171019163551936965.jpg"],"IntroVideo":"https://www.youtube.com/watch?v=cfPh6oLBwn4","IntroText":"grgergerg","IntroTitle":"rfgdfdf","Language":1,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
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
         * ImageList : ["20180227125243635961.png","20171019163446912963.jpg","20171019163551936965.jpg"]
         * IntroVideo : https://www.youtube.com/watch?v=cfPh6oLBwn4
         * IntroText : grgergerg
         * IntroTitle : rfgdfdf
         * Language : 1
         * Base64 : null
         * Extension : null
         * SharedLink : null
         * PagesCount : 0
         * CurrentPage : 0
         * RowsPerPage : 10
         */

        private String IntroVideo;
        private String IntroText;
        private String IntroTitle;
        private int Language;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;
        private List<String> ImageList;

        public void setIntroVideo(String IntroVideo) {
            this.IntroVideo = IntroVideo;
        }

        public void setIntroText(String IntroText) {
            this.IntroText = IntroText;
        }

        public void setIntroTitle(String IntroTitle) {
            this.IntroTitle = IntroTitle;
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

        public void setImageList(List<String> ImageList) {
            this.ImageList = ImageList;
        }

        public String getIntroVideo() {
            return IntroVideo;
        }

        public String getIntroText() {
            return IntroText;
        }

        public String getIntroTitle() {
            return IntroTitle;
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

        public List<String> getImageList() {
            return ImageList;
        }
    }
}
