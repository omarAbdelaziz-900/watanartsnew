package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class HomeIntroResponseModel {

    @SerializedName("result")
    @Expose
    private ResultBean result;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class ResultBean {

        @SerializedName("ImageList")
        @Expose
        private List<String> imageList = null;
        @SerializedName("IntroVideo")
        @Expose
        private String introVideo;
        @SerializedName("IntroText")
        @Expose
        private String introText;
        @SerializedName("IntroTitle")
        @Expose
        private String introTitle;
        @SerializedName("Category")
        @Expose
        private List<CategoryBean> category = null;
        @SerializedName("Language")
        @Expose
        private Integer language;
        @SerializedName("UserIdValue")
        @Expose
        private Object userIdValue;
        @SerializedName("Base64")
        @Expose
        private Object base64;
        @SerializedName("Extension")
        @Expose
        private Object extension;
        @SerializedName("SharedLink")
        @Expose
        private Object sharedLink;
        @SerializedName("PagesCount")
        @Expose
        private Integer pagesCount;
        @SerializedName("CurrentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("RowsPerPage")
        @Expose
        private Integer rowsPerPage;

        public List<String> getImageList() {
            return imageList;
        }

        public void setImageList(List<String> imageList) {
            this.imageList = imageList;
        }

        public String getIntroVideo() {
            return introVideo;
        }

        public void setIntroVideo(String introVideo) {
            this.introVideo = introVideo;
        }

        public String getIntroText() {
            return introText;
        }

        public void setIntroText(String introText) {
            this.introText = introText;
        }

        public String getIntroTitle() {
            return introTitle;
        }

        public void setIntroTitle(String introTitle) {
            this.introTitle = introTitle;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public Integer getLanguage() {
            return language;
        }

        public void setLanguage(Integer language) {
            this.language = language;
        }

        public Object getUserIdValue() {
            return userIdValue;
        }

        public void setUserIdValue(Object userIdValue) {
            this.userIdValue = userIdValue;
        }

        public Object getBase64() {
            return base64;
        }

        public void setBase64(Object base64) {
            this.base64 = base64;
        }

        public Object getExtension() {
            return extension;
        }

        public void setExtension(Object extension) {
            this.extension = extension;
        }

        public Object getSharedLink() {
            return sharedLink;
        }

        public void setSharedLink(Object sharedLink) {
            this.sharedLink = sharedLink;
        }

        public Integer getPagesCount() {
            return pagesCount;
        }

        public void setPagesCount(Integer pagesCount) {
            this.pagesCount = pagesCount;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getRowsPerPage() {
            return rowsPerPage;
        }

        public void setRowsPerPage(Integer rowsPerPage) {
            this.rowsPerPage = rowsPerPage;
        }


        public class CategoryBean {

            @SerializedName("Cat_ID")
            @Expose
            private Integer catID;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("Image")
            @Expose
            private String image;
            @SerializedName("Items")
            @Expose
            private List<Item> items = null;
            @SerializedName("General_Color")
            @Expose
            private List<String> generalColor = null;
            @SerializedName("General_Style")
            @Expose
            private List<String> generalStyle = null;
            @SerializedName("Language")
            @Expose
            private Integer language;

            public Integer getCatID() {
                return catID;
            }

            public void setCatID(Integer catID) {
                this.catID = catID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public List<Item> getItems() {
                return items;
            }

            public void setItems(List<Item> items) {
                this.items = items;
            }

            public List<String> getGeneralColor() {
                return generalColor;
            }

            public void setGeneralColor(List<String> generalColor) {
                this.generalColor = generalColor;
            }

            public List<String> getGeneralStyle() {
                return generalStyle;
            }

            public void setGeneralStyle(List<String> generalStyle) {
                this.generalStyle = generalStyle;
            }

            public Integer getLanguage() {
                return language;
            }

            public void setLanguage(Integer language) {
                this.language = language;
            }


            public class Item {

                @SerializedName("Product_Properties")
                @Expose
                private List<Object> productProperties = null;
                @SerializedName("Prod_ID")
                @Expose
                private Integer prodID;
                @SerializedName("Price")
                @Expose
                private Integer price;
                @SerializedName("OutPrice")
                @Expose
                private Integer outPrice;
                @SerializedName("Type")
                @Expose
                private String type;
                @SerializedName("Prod_Name")
                @Expose
                private String prodName;
                @SerializedName("Prod_image")
                @Expose
                private Object prodImage;
                @SerializedName("Language")
                @Expose
                private Integer language;

                public List<Object> getProductProperties() {
                    return productProperties;
                }

                public void setProductProperties(List<Object> productProperties) {
                    this.productProperties = productProperties;
                }

                public Integer getProdID() {
                    return prodID;
                }

                public void setProdID(Integer prodID) {
                    this.prodID = prodID;
                }

                public Integer getPrice() {
                    return price;
                }

                public void setPrice(Integer price) {
                    this.price = price;
                }

                public Integer getOutPrice() {
                    return outPrice;
                }

                public void setOutPrice(Integer outPrice) {
                    this.outPrice = outPrice;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getProdName() {
                    return prodName;
                }

                public void setProdName(String prodName) {
                    this.prodName = prodName;
                }

                public Object getProdImage() {
                    return prodImage;
                }

                public void setProdImage(Object prodImage) {
                    this.prodImage = prodImage;
                }

                public Integer getLanguage() {
                    return language;
                }

                public void setLanguage(Integer language) {
                    this.language = language;
                }

            }
        }
    }


}
