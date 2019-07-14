package com.WattanArt.model.Response;

import java.util.List;

public class CanvasPrintResponseModel {
    /**
     * result : [{"ImageList":["gghfg","gfghgh","fghgfh"],"Order":1,"IntroText":"ghgfh","IntroTitle":"ghfg","Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10},{"ImageList":["sefasdf","dfdsfsd","dsfasfa"],"Order":2,"IntroText":"efsdfas","IntroTitle":"dsfsa","Language":1,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":1,"CurrentPage":0,"RowsPerPage":10}]
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
         * ImageList : ["gghfg","gfghgh","fghgfh"]
         * Order : 1
         * IntroText : ghgfh
         * IntroTitle : ghfg
         * Language : 1
         * UserIdValue : null
         * Base64 : null
         * Extension : null
         * SharedLink : null
         * PagesCount : 1
         * CurrentPage : 0
         * RowsPerPage : 10
         */

        private int Order;
        private String IntroText;
        private String IntroTitle;
        private int Language;
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;
        private List<String> ImageList;

        public void setOrder(int Order) {
            this.Order = Order;
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

        public void setImageList(List<String> ImageList) {
            this.ImageList = ImageList;
        }

        public int getOrder() {
            return Order;
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

        public List<String> getImageList() {
            return ImageList;
        }
    }
}
