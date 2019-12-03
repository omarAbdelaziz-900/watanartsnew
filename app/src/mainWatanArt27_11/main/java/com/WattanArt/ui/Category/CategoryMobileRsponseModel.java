package com.WattanArt.ui.Category;

import java.util.List;

public class CategoryMobileRsponseModel {

    /**
     * result : [{"Cat_ID":6,"Name":"Android","Image":"2019112712225171967.jpg","Items":[{"Prod_ID":6,"Prod_Name":"Samsong ","Prod_image":"201911271212377968.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":8,"Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":9,"Prod_Name":"Lenovo","Prod_image":"201911271226215239610.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":10,"Prod_Name":"Htc","Prod_image":"20191127122791249611.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Cat_ID":7,"Name":"IPhone","Image":"2019112712035772966.jpg","Items":[],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}]
     * ISResultHasData : 1
     */

    private int ISResultHasData;
    private List<ResultBean> result;

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * Cat_ID : 6
         * Name : Android
         * Image : 2019112712225171967.jpg
         * Items : [{"Prod_ID":6,"Prod_Name":"Samsong ","Prod_image":"201911271212377968.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":8,"Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":9,"Prod_Name":"Lenovo","Prod_image":"201911271226215239610.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"Prod_ID":10,"Prod_Name":"Htc","Prod_image":"20191127122791249611.png","Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}]
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
        private List<ItemsBean> Items;

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

        public List<ItemsBean> getItems() {
            return Items;
        }

        public void setItems(List<ItemsBean> Items) {
            this.Items = Items;
        }

        public static class ItemsBean {
            /**
             * Prod_ID : 6
             * Prod_Name : Samsong
             * Prod_image : 201911271212377968.png
             * Language : 2
             * UserIdValue : null
             * Base64 : null
             * Extension : null
             * SharedLink : null
             * PagesCount : 0
             * CurrentPage : 0
             * RowsPerPage : 10
             */

            private int Prod_ID;
            private String Prod_Name;
            private String Prod_image;
            private int Language;
            private Object UserIdValue;
            private Object Base64;
            private Object Extension;
            private Object SharedLink;
            private int PagesCount;
            private int CurrentPage;
            private int RowsPerPage;

            public int getProd_ID() {
                return Prod_ID;
            }

            public void setProd_ID(int Prod_ID) {
                this.Prod_ID = Prod_ID;
            }

            public String getProd_Name() {
                return Prod_Name;
            }

            public void setProd_Name(String Prod_Name) {
                this.Prod_Name = Prod_Name;
            }

            public String getProd_image() {
                return Prod_image;
            }

            public void setProd_image(String Prod_image) {
                this.Prod_image = Prod_image;
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
        }
    }
}
