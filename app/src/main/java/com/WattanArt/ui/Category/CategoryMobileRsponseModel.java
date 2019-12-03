package com.WattanArt.ui.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryMobileRsponseModel implements Serializable {


    /**
     * result : [{"Cat_ID":6,"Name":"Android","Image":"2019112712225171967.jpg","Items":[{"Product_Properties":[],"Prod_ID":6,"Price":100,"Type":"1","Prod_Name":"Samsong ","Prod_image":"201911271212377968.png","Language":0},{"Product_Properties":[],"Prod_ID":8,"Price":200,"Type":"1","Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":0},{"Product_Properties":[],"Prod_ID":9,"Price":300,"Type":"2","Prod_Name":"Lenovo","Prod_image":"201911271226215239610.png","Language":0},{"Product_Properties":[],"Prod_ID":10,"Price":400,"Type":"2","Prod_Name":"Htc","Prod_image":"20191127122791249611.png","Language":0}],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":0},{"Cat_ID":7,"Name":"IPhone","Image":"2019112712035772966.jpg","Items":[],"General_Color":["C71585","ffffff","000000","4B0082","32CD32"],"General_Style":["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"],"Language":0}]
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
         * Items : [{"Product_Properties":[],"Prod_ID":6,"Price":100,"Type":"1","Prod_Name":"Samsong ","Prod_image":"201911271212377968.png","Language":0},{"Product_Properties":[],"Prod_ID":8,"Price":200,"Type":"1","Prod_Name":"Oppo","Prod_image":"20191127122211179969.png","Language":0},{"Product_Properties":[],"Prod_ID":9,"Price":300,"Type":"2","Prod_Name":"Lenovo","Prod_image":"201911271226215239610.png","Language":0},{"Product_Properties":[],"Prod_ID":10,"Price":400,"Type":"2","Prod_Name":"Htc","Prod_image":"20191127122791249611.png","Language":0}]
         * General_Color : ["C71585","ffffff","000000","4B0082","32CD32"]
         * General_Style : ["1.jpg","2.jpg","3.jpg","4.jpg","5.jpg","6.jpg","7.jpg","8.jpg"]
         * Language : 0
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
             * Type : 1
             * Prod_Name : Samsong
             * Prod_image : 201911271212377968.png
             * Language : 0
             */

            private int Prod_ID;
            private int Price;
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
