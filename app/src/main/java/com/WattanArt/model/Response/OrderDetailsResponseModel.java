package com.WattanArt.model.Response;

import java.util.List;

public class OrderDetailsResponseModel {


    /**
     * result : [{"ID":21845,"OrderID":21103,"MainImage":"201912081455266096.jpg","LowResultionImage":"lOW201912081455266096.jpg","Quantiy":3,"Set":null,"PiecePrice":500,"GroupKey":null,"PatternID":0,"Pattern":"Flash Memory","CreatedDate":"2019-12-08T14:55:31.07","OrderType":1,"Cat_ID":9,"images":[{"OrderID":0,"Image":null,"PrintscreenImg":"201912081455266096.jpg","lowPrintscreenImg":"lOW201912081455266096.jpg","Color":"null","Style":"null","DesignerFlag":0,"DesignerID":"0","OrderDatailsID":0,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"OrderID":0,"Image":null,"PrintscreenImg":"2019120814552895296.jpg","lowPrintscreenImg":"lOW2019120814552895296.jpg","Color":"null","Style":"null","DesignerFlag":1,"DesignerID":"1","OrderDatailsID":0,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}],"Language":0}]
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
         * ID : 21845
         * OrderID : 21103
         * MainImage : 201912081455266096.jpg
         * LowResultionImage : lOW201912081455266096.jpg
         * Quantiy : 3
         * Set : null
         * PiecePrice : 500
         * GroupKey : null
         * PatternID : 0
         * Pattern : Flash Memory
         * CreatedDate : 2019-12-08T14:55:31.07
         * OrderType : 1
         * Cat_ID : 9
         * images : [{"OrderID":0,"Image":null,"PrintscreenImg":"201912081455266096.jpg","lowPrintscreenImg":"lOW201912081455266096.jpg","Color":"null","Style":"null","DesignerFlag":0,"DesignerID":"0","OrderDatailsID":0,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10},{"OrderID":0,"Image":null,"PrintscreenImg":"2019120814552895296.jpg","lowPrintscreenImg":"lOW2019120814552895296.jpg","Color":"null","Style":"null","DesignerFlag":1,"DesignerID":"1","OrderDatailsID":0,"Language":0,"UserIdValue":null,"Base64":null,"Extension":null,"SharedLink":null,"PagesCount":0,"CurrentPage":0,"RowsPerPage":10}]
         * Language : 0
         */

        private int ID;
        private int OrderID;
        private String MainImage;
        private String LowResultionImage;
        private int Quantiy;
        private Object Set;
        private int PiecePrice;
        private Object GroupKey;
        private int PatternID;
        private String Pattern;
        private String CreatedDate;
        private int OrderType;
        private int Cat_ID;
        private int Language;
        private List<ImagesBean> images;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getOrderID() {
            return OrderID;
        }

        public void setOrderID(int OrderID) {
            this.OrderID = OrderID;
        }

        public String getMainImage() {
            return MainImage;
        }

        public void setMainImage(String MainImage) {
            this.MainImage = MainImage;
        }

        public String getLowResultionImage() {
            return LowResultionImage;
        }

        public void setLowResultionImage(String LowResultionImage) {
            this.LowResultionImage = LowResultionImage;
        }

        public int getQuantiy() {
            return Quantiy;
        }

        public void setQuantiy(int Quantiy) {
            this.Quantiy = Quantiy;
        }

        public Object getSet() {
            return Set;
        }

        public void setSet(Object Set) {
            this.Set = Set;
        }

        public int getPiecePrice() {
            return PiecePrice;
        }

        public void setPiecePrice(int PiecePrice) {
            this.PiecePrice = PiecePrice;
        }

        public Object getGroupKey() {
            return GroupKey;
        }

        public void setGroupKey(Object GroupKey) {
            this.GroupKey = GroupKey;
        }

        public int getPatternID() {
            return PatternID;
        }

        public void setPatternID(int PatternID) {
            this.PatternID = PatternID;
        }

        public String getPattern() {
            return Pattern;
        }

        public void setPattern(String Pattern) {
            this.Pattern = Pattern;
        }

        public String getCreatedDate() {
            return CreatedDate;
        }

        public void setCreatedDate(String CreatedDate) {
            this.CreatedDate = CreatedDate;
        }

        public int getOrderType() {
            return OrderType;
        }

        public void setOrderType(int OrderType) {
            this.OrderType = OrderType;
        }

        public int getCat_ID() {
            return Cat_ID;
        }

        public void setCat_ID(int Cat_ID) {
            this.Cat_ID = Cat_ID;
        }

        public int getLanguage() {
            return Language;
        }

        public void setLanguage(int Language) {
            this.Language = Language;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * OrderID : 0
             * Image : null
             * PrintscreenImg : 201912081455266096.jpg
             * lowPrintscreenImg : lOW201912081455266096.jpg
             * Color : null
             * Style : null
             * DesignerFlag : 0
             * DesignerID : 0
             * OrderDatailsID : 0
             * Language : 0
             * UserIdValue : null
             * Base64 : null
             * Extension : null
             * SharedLink : null
             * PagesCount : 0
             * CurrentPage : 0
             * RowsPerPage : 10
             */

            private int OrderID;
            private Object Image;
            private String PrintscreenImg;
            private String lowPrintscreenImg;
            private String Color;
            private String Style;
            private int DesignerFlag;
            private String DesignerID;
            private int OrderDatailsID;
            private int Language;
            private Object UserIdValue;
            private Object Base64;
            private Object Extension;
            private Object SharedLink;
            private int PagesCount;
            private int CurrentPage;
            private int RowsPerPage;

            public int getOrderID() {
                return OrderID;
            }

            public void setOrderID(int OrderID) {
                this.OrderID = OrderID;
            }

            public Object getImage() {
                return Image;
            }

            public void setImage(Object Image) {
                this.Image = Image;
            }

            public String getPrintscreenImg() {
                return PrintscreenImg;
            }

            public void setPrintscreenImg(String PrintscreenImg) {
                this.PrintscreenImg = PrintscreenImg;
            }

            public String getLowPrintscreenImg() {
                return lowPrintscreenImg;
            }

            public void setLowPrintscreenImg(String lowPrintscreenImg) {
                this.lowPrintscreenImg = lowPrintscreenImg;
            }

            public String getColor() {
                return Color;
            }

            public void setColor(String Color) {
                this.Color = Color;
            }

            public String getStyle() {
                return Style;
            }

            public void setStyle(String Style) {
                this.Style = Style;
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

            public int getOrderDatailsID() {
                return OrderDatailsID;
            }

            public void setOrderDatailsID(int OrderDatailsID) {
                this.OrderDatailsID = OrderDatailsID;
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
