package com.WattanArt.model.Response;

import java.util.List;

public class HomeIntroResponseModel {

    /**
     * result : {"ImageList":["20181204121643559610.jpg","20181204121695959611.jpg","201812041216152179612.jpg"],"IntroVideo":"https://www.youtube.com/watch?v=djh0mkjoaUY","IntroText":"A wonderful way to bring your memories to life and decorate your wall with different sizes self-adhesive photo boards .\n\n\n","IntroTitle":"Watan Arts","Category":[{"Cat_ID":1,"Name":"Phone Cover","Image":"20191029111557207968.jpeg","Items":[],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Cat_ID":5,"Name":"Mug","Image":"20191029111627636969.jpg","Items":[],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
     * ISResultHasData : 1
     */

    private ResultBean result;
    private int ISResultHasData;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public static class ResultBean {
        /**
         * ImageList : ["20181204121643559610.jpg","20181204121695959611.jpg","201812041216152179612.jpg"]
         * IntroVideo : https://www.youtube.com/watch?v=djh0mkjoaUY
         * IntroText : A wonderful way to bring your memories to life and decorate your wall with different sizes self-adhesive photo boards .
         * IntroTitle : Watan Arts
         * Category : [{"Cat_ID":1,"Name":"Phone Cover","Image":"20191029111557207968.jpeg","Items":[],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Cat_ID":5,"Name":"Mug","Image":"20191029111627636969.jpg","Items":[],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}]
         * Language : 2
         * UserIdValue : null
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
        private Object UserIdValue;
        private Object Base64;
        private Object Extension;
        private Object SharedLink;
        private int PagesCount;
        private int CurrentPage;
        private int RowsPerPage;
        private List<String> ImageList;
        private List<CategoryBean> Category;

        public String getIntroVideo() {
            return IntroVideo;
        }

        public void setIntroVideo(String IntroVideo) {
            this.IntroVideo = IntroVideo;
        }

        public String getIntroText() {
            return IntroText;
        }

        public void setIntroText(String IntroText) {
            this.IntroText = IntroText;
        }

        public String getIntroTitle() {
            return IntroTitle;
        }

        public void setIntroTitle(String IntroTitle) {
            this.IntroTitle = IntroTitle;
        }

        public int getLanguage() {
            return Language;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public Object getUserIdValue() {
            return UserIdValue;
        }

        public void setUserIdValue(Object UserIdValue) {
            this.UserIdValue = UserIdValue;
        }

        public Object getBase64() {
            return Base64;
        }

        public void setBase64(Object Base64) {
            this.Base64 = Base64;
        }

        public Object getExtension() {
            return Extension;
        }

        public void setExtension(Object Extension) {
            this.Extension = Extension;
        }

        public Object getSharedLink() {
            return SharedLink;
        }

        public void setSharedLink(Object SharedLink) {
            this.SharedLink = SharedLink;
        }

        public int getPagesCount() {
            return PagesCount;
        }

        public void setPagesCount(int PagesCount) {
            this.PagesCount = PagesCount;
        }

        public int getCurrentPage() {
            return CurrentPage;
        }

        public void setCurrentPage(int CurrentPage) {
            this.CurrentPage = CurrentPage;
        }

        public int getRowsPerPage() {
            return RowsPerPage;
        }

        public void setRowsPerPage(int RowsPerPage) {
            this.RowsPerPage = RowsPerPage;
        }

        public List<String> getImageList() {
            return ImageList;
        }

        public void setImageList(List<String> ImageList) {
            this.ImageList = ImageList;
        }

        public List<CategoryBean> getCategory() {
            return Category;
        }

        public void setCategory(List<CategoryBean> Category) {
            this.Category = Category;
        }

        public static class CategoryBean {
            /**
             * Cat_ID : 1
             * Name : Phone Cover
             * Image : 20191029111557207968.jpeg
             * Items : []
             * Language : 2
             * UserIdValue : null
             * Base64 : null
             * Extension : null
             * SharedLink : null
             * PagesCount : 0
             * CurrentPage : 0
             * RowsPerPage : 10
             */

            private int Cat_ID;
            private String Name;
            private String Image;
            private int Language;
            private Object UserIdValue;
            private Object Base64;
            private Object Extension;
            private Object SharedLink;
            private int PagesCount;
            private int CurrentPage;
            private int RowsPerPage;
            private List<?> Items;

            public int getCat_ID() {
                return Cat_ID;
            }

            public void setCat_ID(int Cat_ID) {
                this.Cat_ID = Cat_ID;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getImage() {
                return Image;
            }

            public void setImage(String Image) {
                this.Image = Image;
            }

            public int getLanguage() {
                return Language;
            }

            public void setLanguage(int Language) {
                this.Language = Language;
            }

            public Object getUserIdValue() {
                return UserIdValue;
            }

            public void setUserIdValue(Object UserIdValue) {
                this.UserIdValue = UserIdValue;
            }

            public Object getBase64() {
                return Base64;
            }

            public void setBase64(Object Base64) {
                this.Base64 = Base64;
            }

            public Object getExtension() {
                return Extension;
            }

            public void setExtension(Object Extension) {
                this.Extension = Extension;
            }

            public Object getSharedLink() {
                return SharedLink;
            }

            public void setSharedLink(Object SharedLink) {
                this.SharedLink = SharedLink;
            }

            public int getPagesCount() {
                return PagesCount;
            }

            public void setPagesCount(int PagesCount) {
                this.PagesCount = PagesCount;
            }

            public int getCurrentPage() {
                return CurrentPage;
            }

            public void setCurrentPage(int CurrentPage) {
                this.CurrentPage = CurrentPage;
            }

            public int getRowsPerPage() {
                return RowsPerPage;
            }

            public void setRowsPerPage(int RowsPerPage) {
                this.RowsPerPage = RowsPerPage;
            }

            public List<?> getItems() {
                return Items;
            }

            public void setItems(List<?> Items) {
                this.Items = Items;
            }
        }
    }
}
