package com.WattanArt.model.Response;

import java.util.List;


public class HomeIntroResponseModel {

    /**
     * result : {"ImageList":["20181204121643559610.jpg","20181204121695959611.jpg","201812041216152179612.jpg"],"IntroVideo":"https://www.youtube.com/watch?v=djh0mkjoaUY","IntroText":"A wonderful way to bring your memories to life and decorate your wall with different sizes self-adhesive photo boards .\n\n\n","IntroTitle":"Watan Arts","Category":[{"Cat_ID":1,"Name":"Mobile Covers","Image":"20191127115425150962.jpg","Items":[{"Product_Properties":[],"Prod_ID":6,"Price":100,"OutPrice":80,"Type":"1","Prod_Name":"Samsung ","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":8,"Price":200,"OutPrice":180,"Type":"1","Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":9,"Price":300,"OutPrice":280,"Type":"2","Prod_Name":"lenovo","Prod_image":"20191127122791249611.png","Language":2},{"Product_Properties":[],"Prod_ID":10,"Price":400,"OutPrice":380,"Type":"2","Prod_Name":"Htc","Prod_image":"20191211142534912965.png","Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":9,"Name":"Flash Memory","Image":"20191127115556995963.jpg","Items":[{"Product_Properties":[],"Prod_ID":11,"Price":500,"OutPrice":300,"Type":"0","Prod_Name":"4G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":22,"Price":550,"OutPrice":350,"Type":"0","Prod_Name":"8G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":23,"Price":600,"OutPrice":400,"Type":"0","Prod_Name":"16G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":24,"Price":700,"OutPrice":500,"Type":"0","Prod_Name":"32G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":25,"Price":800,"OutPrice":600,"Type":"0","Prod_Name":"64G","Prod_image":null,"Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":10,"Name":"T-Shirt","Image":"2019112711573146964.jpg","Items":[],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":11,"Name":"Coaster","Image":"20191127115851367965.jpg","Items":[{"Product_Properties":[],"Prod_ID":16,"Price":600,"OutPrice":400,"Type":"0","Prod_Name":"Round Coaster","Prod_image":"Coaster_circle.png","Language":2},{"Product_Properties":[],"Prod_ID":17,"Price":700,"OutPrice":500,"Type":"0","Prod_Name":"Square Coaster","Prod_image":"Coaster_square.png","Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2}],"Language":2,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}
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
         * Category : [{"Cat_ID":1,"Name":"Mobile Covers","Image":"20191127115425150962.jpg","Items":[{"Product_Properties":[],"Prod_ID":6,"Price":100,"OutPrice":80,"Type":"1","Prod_Name":"Samsung ","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":8,"Price":200,"OutPrice":180,"Type":"1","Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":9,"Price":300,"OutPrice":280,"Type":"2","Prod_Name":"lenovo","Prod_image":"20191127122791249611.png","Language":2},{"Product_Properties":[],"Prod_ID":10,"Price":400,"OutPrice":380,"Type":"2","Prod_Name":"Htc","Prod_image":"20191211142534912965.png","Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":9,"Name":"Flash Memory","Image":"20191127115556995963.jpg","Items":[{"Product_Properties":[],"Prod_ID":11,"Price":500,"OutPrice":300,"Type":"0","Prod_Name":"4G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":22,"Price":550,"OutPrice":350,"Type":"0","Prod_Name":"8G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":23,"Price":600,"OutPrice":400,"Type":"0","Prod_Name":"16G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":24,"Price":700,"OutPrice":500,"Type":"0","Prod_Name":"32G","Prod_image":null,"Language":2},{"Product_Properties":[],"Prod_ID":25,"Price":800,"OutPrice":600,"Type":"0","Prod_Name":"64G","Prod_image":null,"Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":10,"Name":"T-Shirt","Image":"2019112711573146964.jpg","Items":[],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2},{"Cat_ID":11,"Name":"Coaster","Image":"20191127115851367965.jpg","Items":[{"Product_Properties":[],"Prod_ID":16,"Price":600,"OutPrice":400,"Type":"0","Prod_Name":"Round Coaster","Prod_image":"Coaster_circle.png","Language":2},{"Product_Properties":[],"Prod_ID":17,"Price":700,"OutPrice":500,"Type":"0","Prod_Name":"Square Coaster","Prod_image":"Coaster_square.png","Language":2}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":2}]
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
             * Name : Mobile Covers
             * Image : 20191127115425150962.jpg
             * Items : [{"Product_Properties":[],"Prod_ID":6,"Price":100,"OutPrice":80,"Type":"1","Prod_Name":"Samsung ","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":8,"Price":200,"OutPrice":180,"Type":"1","Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":2},{"Product_Properties":[],"Prod_ID":9,"Price":300,"OutPrice":280,"Type":"2","Prod_Name":"lenovo","Prod_image":"20191127122791249611.png","Language":2},{"Product_Properties":[],"Prod_ID":10,"Price":400,"OutPrice":380,"Type":"2","Prod_Name":"Htc","Prod_image":"20191211142534912965.png","Language":2}]
             * General_Color : ["C71585","ffffff","000000","4B0082","32CD32"]
             * General_Style : ["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"]
             * Language : 2
             */

            private int Cat_ID;
            private String Name;
            private String Image;
            private int Language;
            private List<ItemsBean> Items;
            private List<String> General_Color;
            private List<String> General_Style;

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

            public List<ItemsBean> getItems() {
                return Items;
            }

            public void setItems(List<ItemsBean> Items) {
                this.Items = Items;
            }

            public List<String> getGeneral_Color() {
                return General_Color;
            }

            public void setGeneral_Color(List<String> General_Color) {
                this.General_Color = General_Color;
            }

            public List<String> getGeneral_Style() {
                return General_Style;
            }

            public void setGeneral_Style(List<String> General_Style) {
                this.General_Style = General_Style;
            }

            public static class ItemsBean {
                /**
                 * Product_Properties : []
                 * Prod_ID : 6
                 * Price : 100
                 * OutPrice : 80
                 * Type : 1
                 * Prod_Name : Samsung
                 * Prod_image : 20191127122211179969.png
                 * Language : 2
                 */

                private int Prod_ID;
                private int Price;
                private int OutPrice;
                private String Type;
                private String Prod_Name;
                private String Prod_image;
                private int Language;
                private List<?> Product_Properties;

                public int getProd_ID() {
                    return Prod_ID;
                }

                public void setProd_ID(int Prod_ID) {
                    this.Prod_ID = Prod_ID;
                }

                public int getPrice() {
                    return Price;
                }

                public void setPrice(int Price) {
                    this.Price = Price;
                }

                public int getOutPrice() {
                    return OutPrice;
                }

                public void setOutPrice(int OutPrice) {
                    this.OutPrice = OutPrice;
                }

                public String getType() {
                    return Type;
                }

                public void setType(String Type) {
                    this.Type = Type;
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

                public List<?> getProduct_Properties() {
                    return Product_Properties;
                }

                public void setProduct_Properties(List<?> Product_Properties) {
                    this.Product_Properties = Product_Properties;
                }
            }
        }
    }
}
