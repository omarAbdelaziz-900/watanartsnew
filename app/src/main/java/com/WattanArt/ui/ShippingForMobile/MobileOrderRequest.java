package com.WattanArt.ui.ShippingForMobile;

import java.util.ArrayList;
import java.util.List;

public class MobileOrderRequest {

    private static  MobileOrderRequest instance=new MobileOrderRequest();

    public MobileOrderRequest(){

    }
    public static MobileOrderRequest getInstance(){
        if (instance==null)
            instance = new MobileOrderRequest();
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }

    /**
     * order : {"InEgypt":true,"OrderType":1,"PhoneNumber":"01047454788","ValidCouponCode":false,"address1":"adress","buytype":1,"cityID":1,"countryID":1,"couponCode":"","userID":"02a6b427-8647-40ba-8498-78db1f424792","zipcode":"tfyrtyr","address2":"adress"}
     * orderDetils : [{"ProductID":11,"images":[{"Color":"000000","DesignerFlag":0,"DesignerID":"0","Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Style":"","text":"Your Text","text_color":"ffffff","text_font":"Font Name","text_size":"large"},{"Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Color":"000000","Style":"","DesignerFlag":0,"DesignerID":"0","text":"Your Text","text_font":"Font Name","text_color":"ffffff","text_size":"large"}],"mainImage":"printscreenmes8.jpg","quantiy":3},{"ProductID":22,"mainImage":"printscreenmes8.jpg","quantiy":3,"images":[{"Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Color":"000000","Style":"","DesignerFlag":0,"DesignerID":"0","text":"Your Text","text_font":"Font Name","text_color":"ffffff","text_size":"large"},{"Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Color":"000000","Style":"","DesignerFlag":0,"DesignerID":"0","text":"Your Text","text_font":"Font Name","text_color":"ffffff","text_size":"large"}]}]
     */

    private OrderBean order;
    private List<OrderDetilsBean> orderDetils=new ArrayList<>();

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public List<OrderDetilsBean> getOrderDetils() {
        return orderDetils;
    }

    public void setOrderDetils(List<OrderDetilsBean> orderDetils) {
        this.orderDetils = orderDetils;
    }

    public static class OrderBean {
        /**
         * InEgypt : true
         * OrderType : 1
         * PhoneNumber : 01047454788
         * ValidCouponCode : false
         * address1 : adress
         * buytype : 1
         * cityID : 1
         * countryID : 1
         * couponCode :
         * userID : 02a6b427-8647-40ba-8498-78db1f424792
         * zipcode : tfyrtyr
         * address2 : adress
         */

        private boolean InEgypt;
        private int OrderType;
        private String PhoneNumber;
        private boolean ValidCouponCode;
        private String address1;
        private int buytype;
        private int cityID;
        private int countryID;
        private String couponCode;
        private String userID;
        private String zipcode;
        private String address2;

        public boolean isInEgypt() {
            return InEgypt;
        }

        public void setInEgypt(boolean InEgypt) {
            this.InEgypt = InEgypt;
        }

        public int getOrderType() {
            return OrderType;
        }

        public void setOrderType(int OrderType) {
            this.OrderType = OrderType;
        }

        public String getPhoneNumber() {
            return PhoneNumber;
        }

        public void setPhoneNumber(String PhoneNumber) {
            this.PhoneNumber = PhoneNumber;
        }

        public boolean isValidCouponCode() {
            return ValidCouponCode;
        }

        public void setValidCouponCode(boolean ValidCouponCode) {
            this.ValidCouponCode = ValidCouponCode;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public int getBuytype() {
            return buytype;
        }

        public void setBuytype(int buytype) {
            this.buytype = buytype;
        }

        public int getCityID() {
            return cityID;
        }

        public void setCityID(int cityID) {
            this.cityID = cityID;
        }

        public int getCountryID() {
            return countryID;
        }

        public void setCountryID(int countryID) {
            this.countryID = countryID;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }
    }

    public static class OrderDetilsBean {
        /**
         * ProductID : 11
         * images : [{"Color":"000000","DesignerFlag":0,"DesignerID":"0","Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Style":"","text":"Your Text","text_color":"ffffff","text_font":"Font Name","text_size":"large"},{"Image":"mes8.jpg","PrintscreenImg":"printscreenmes8.jpg","Color":"000000","Style":"","DesignerFlag":0,"DesignerID":"0","text":"Your Text","text_font":"Font Name","text_color":"ffffff","text_size":"large"}]
         * mainImage : printscreenmes8.jpg
         * quantiy : 3
         */

        private int ProductID;
        private String mainImage;
        private int quantiy;
        private List<ImagesBean> images=new ArrayList<>();

        public int getProductID() {
            return ProductID;
        }

        public void setProductID(int ProductID) {
            this.ProductID = ProductID;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public int getQuantiy() {
            return quantiy;
        }

        public void setQuantiy(int quantiy) {
            this.quantiy = quantiy;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * Color : 000000
             * DesignerFlag : 0
             * DesignerID : 0
             * Image : mes8.jpg
             * PrintscreenImg : printscreenmes8.jpg
             * Style :
             * text : Your Text
             * text_color : ffffff
             * text_font : Font Name
             * text_size : large
             */

            private String Color;
            private int DesignerFlag;
            private String DesignerID;
            private String Image;
            private String PrintscreenImg;
            private String Style;
            private String text;
            private String text_color;
            private String text_font;
            private String text_size;

            public String getColor() {
                return Color;
            }

            public void setColor(String Color) {
                this.Color = Color;
            }

            public int getDesignerFlag() {
                return DesignerFlag;
            }

            public void setDesignerFlag(int DesignerFlag) {
                this.DesignerFlag = DesignerFlag;
            }

            public String getDesignerID() {
                return DesignerID;
            }

            public void setDesignerID(String DesignerID) {
                this.DesignerID = DesignerID;
            }

            public String getImage() {
                return Image;
            }

            public void setImage(String Image) {
                this.Image = Image;
            }

            public String getPrintscreenImg() {
                return PrintscreenImg;
            }

            public void setPrintscreenImg(String PrintscreenImg) {
                this.PrintscreenImg = PrintscreenImg;
            }

            public String getStyle() {
                return Style;
            }

            public void setStyle(String Style) {
                this.Style = Style;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getText_color() {
                return text_color;
            }

            public void setText_color(String text_color) {
                this.text_color = text_color;
            }

            public String getText_font() {
                return text_font;
            }

            public void setText_font(String text_font) {
                this.text_font = text_font;
            }

            public String getText_size() {
                return text_size;
            }

            public void setText_size(String text_size) {
                this.text_size = text_size;
            }
        }
    }
}
