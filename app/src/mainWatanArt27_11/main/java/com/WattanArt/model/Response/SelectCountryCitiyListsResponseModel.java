package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SelectCountryCitiyListsResponseModel {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class Result {

        @SerializedName("CountryCitiesLst")
        @Expose
        private List<CountryCitiesLst> countryCitiesLst = null;
        @SerializedName("PaidTypes")
        @Expose
        private List<PaidType> paidTypes = null;
        @SerializedName("OrderState")
        @Expose
        private List<OrderState> orderState = null;
        @SerializedName("PatternType")
        @Expose
        private List<PatternType> patternType = null;
        @SerializedName("Prices")
        @Expose
        private Prices prices;

        public List<CountryCitiesLst> getCountryCitiesLst() {
            return countryCitiesLst;
        }

        public void setCountryCitiesLst(List<CountryCitiesLst> countryCitiesLst) {
            this.countryCitiesLst = countryCitiesLst;
        }

        public List<PaidType> getPaidTypes() {
            return paidTypes;
        }

        public void setPaidTypes(List<PaidType> paidTypes) {
            this.paidTypes = paidTypes;
        }

        public List<OrderState> getOrderState() {
            return orderState;
        }

        public void setOrderState(List<OrderState> orderState) {
            this.orderState = orderState;
        }

        public List<PatternType> getPatternType() {
            return patternType;
        }

        public void setPatternType(List<PatternType> patternType) {
            this.patternType = patternType;
        }

        public Prices getPrices() {
            return prices;
        }

        public void setPrices(Prices prices) {
            this.prices = prices;
        }



        public class CitiesLst {

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("CountryID")
            @Expose
            private Integer countryID;
            @SerializedName("CreatedDate")
            @Expose
            private String createdDate;
            @SerializedName("IsActive")
            @Expose
            private Boolean isActive;
            @SerializedName("Title")
            @Expose
            private String title;
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

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public Integer getCountryID() {
                return countryID;
            }

            public void setCountryID(Integer countryID) {
                this.countryID = countryID;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public Boolean getIsActive() {
                return isActive;
            }

            public void setIsActive(Boolean isActive) {
                this.isActive = isActive;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

        }

        public class CountryCitiesLst {

            @SerializedName("CountryObj")
            @Expose
            private CountryObj countryObj;
            @SerializedName("CitiesLst")
            @Expose
            private List<CitiesLst> citiesLst = null;

            public CountryObj getCountryObj() {
                return countryObj;
            }

            public void setCountryObj(CountryObj countryObj) {
                this.countryObj = countryObj;
            }

            public List<CitiesLst> getCitiesLst() {
                return citiesLst;
            }

            public void setCitiesLst(List<CitiesLst> citiesLst) {
                this.citiesLst = citiesLst;
            }

        }

        public class CountryObj {

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("Currencysign")
            @Expose
            private String currencysign;
            @SerializedName("ChargePrice")
            @Expose
            private Integer chargePrice;
            @SerializedName("CreatedDate")
            @Expose
            private String createdDate;
            @SerializedName("IsActive")
            @Expose
            private Boolean isActive;
            @SerializedName("Title")
            @Expose
            private String title;
            @SerializedName("ChargePeriod")
            @Expose
            private String chargePeriod;
            @SerializedName("Currency")
            @Expose
            private String currency;
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

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public String getCurrencysign() {
                return currencysign;
            }

            public void setCurrencysign(String currencysign) {
                this.currencysign = currencysign;
            }

            public Integer getChargePrice() {
                return chargePrice;
            }

            public void setChargePrice(Integer chargePrice) {
                this.chargePrice = chargePrice;
            }

            public String getCreatedDate() {
                return createdDate;
            }

            public void setCreatedDate(String createdDate) {
                this.createdDate = createdDate;
            }

            public Boolean getIsActive() {
                return isActive;
            }

            public void setIsActive(Boolean isActive) {
                this.isActive = isActive;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getChargePeriod() {
                return chargePeriod;
            }

            public void setChargePeriod(String chargePeriod) {
                this.chargePeriod = chargePeriod;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
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

        }

        public class OrderState {

            @SerializedName("Value")
            @Expose
            private Integer value;
            @SerializedName("Title")
            @Expose
            private String title;
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

            public Integer getValue() {
                return value;
            }

            public void setValue(Integer value) {
                this.value = value;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

        }

        public class PaidType {

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("IsActive")
            @Expose
            private Boolean isActive;
            @SerializedName("Icon")
            @Expose
            private String icon;
            @SerializedName("CreateDate")
            @Expose
            private String createDate;
            @SerializedName("Title")
            @Expose
            private String title;
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

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public Boolean getIsActive() {
                return isActive;
            }

            public void setIsActive(Boolean isActive) {
                this.isActive = isActive;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

        }

        public class PatternType {

            @SerializedName("ID")
            @Expose
            private Integer iD;
            @SerializedName("PatternValue")
            @Expose
            private Object patternValue;
            @SerializedName("Price")
            @Expose
            private Integer price;
            @SerializedName("OutPrice")
            @Expose
            private Integer outPrice;
            @SerializedName("patternIcon")
            @Expose
            private Object patternIcon;
            @SerializedName("PatternMobIcon")
            @Expose
            private Object patternMobIcon;
            @SerializedName("IsActive")
            @Expose
            private Boolean isActive;
            @SerializedName("PatternText")
            @Expose
            private String patternText;
            @SerializedName("piecesCount")
            @Expose
            private Integer piecesCount;
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

            public Integer getID() {
                return iD;
            }

            public void setID(Integer iD) {
                this.iD = iD;
            }

            public Object getPatternValue() {
                return patternValue;
            }

            public void setPatternValue(Object patternValue) {
                this.patternValue = patternValue;
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

            public Object getPatternIcon() {
                return patternIcon;
            }

            public void setPatternIcon(Object patternIcon) {
                this.patternIcon = patternIcon;
            }

            public Object getPatternMobIcon() {
                return patternMobIcon;
            }

            public void setPatternMobIcon(Object patternMobIcon) {
                this.patternMobIcon = patternMobIcon;
            }

            public Boolean getIsActive() {
                return isActive;
            }

            public void setIsActive(Boolean isActive) {
                this.isActive = isActive;
            }

            public String getPatternText() {
                return patternText;
            }

            public void setPatternText(String patternText) {
                this.patternText = patternText;
            }

            public Integer getPiecesCount() {
                return piecesCount;
            }

            public void setPiecesCount(Integer piecesCount) {
                this.piecesCount = piecesCount;
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

        }


        public class Prices {

            @SerializedName("DiscountRate")
            @Expose
            private Integer discountRate;
            @SerializedName("LocalPiecePrice")
            @Expose
            private Integer localPiecePrice;
            @SerializedName("WorldPiecePrice")
            @Expose
            private Integer worldPiecePrice;
            @SerializedName("Local3PiecePrice")
            @Expose
            private Integer local3PiecePrice;
            @SerializedName("World3PiecePrice")
            @Expose
            private Integer world3PiecePrice;
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

            public Integer getDiscountRate() {
                return discountRate;
            }

            public void setDiscountRate(Integer discountRate) {
                this.discountRate = discountRate;
            }

            public Integer getLocalPiecePrice() {
                return localPiecePrice;
            }

            public void setLocalPiecePrice(Integer localPiecePrice) {
                this.localPiecePrice = localPiecePrice;
            }

            public Integer getWorldPiecePrice() {
                return worldPiecePrice;
            }

            public void setWorldPiecePrice(Integer worldPiecePrice) {
                this.worldPiecePrice = worldPiecePrice;
            }

            public Integer getLocal3PiecePrice() {
                return local3PiecePrice;
            }

            public void setLocal3PiecePrice(Integer local3PiecePrice) {
                this.local3PiecePrice = local3PiecePrice;
            }

            public Integer getWorld3PiecePrice() {
                return world3PiecePrice;
            }

            public void setWorld3PiecePrice(Integer world3PiecePrice) {
                this.world3PiecePrice = world3PiecePrice;
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


        }
    }



}
