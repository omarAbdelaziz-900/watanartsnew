package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android Team on 1/21/2018.
 */

public class NewsTicker {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class Result {

        @SerializedName("NewsID")
        @Expose
        private Integer newsID;
        @SerializedName("NewsTypeID")
        @Expose
        private Integer newsTypeID;
        @SerializedName("NewsTitleAR")
        @Expose
        private String newsTitleAR;
        @SerializedName("NewsTitleEN")
        @Expose
        private Object newsTitleEN;
        @SerializedName("NewsDescriptionAR")
        @Expose
        private String newsDescriptionAR;
        @SerializedName("NewsDescriptionEN")
        @Expose
        private Object newsDescriptionEN;
        @SerializedName("NewsImage")
        @Expose
        private String newsImage;
        @SerializedName("NewsDate")
        @Expose
        private String newsDate;
        @SerializedName("NewsDate_Str")
        @Expose
        private Object newsDateStr;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("NewsType")
        @Expose
        private NewsType newsType;
        @SerializedName("Language")
        @Expose
        private Integer language;
        @SerializedName("Base64")
        @Expose
        private Object base64;
        @SerializedName("Extension")
        @Expose
        private Object extension;
        @SerializedName("SharedLink")
        @Expose
        private Object sharedLink;
        @SerializedName("TotalRows")
        @Expose
        private Integer totalRows;

        public Integer getNewsID() {
            return newsID;
        }

        public void setNewsID(Integer newsID) {
            this.newsID = newsID;
        }

        public Integer getNewsTypeID() {
            return newsTypeID;
        }

        public void setNewsTypeID(Integer newsTypeID) {
            this.newsTypeID = newsTypeID;
        }

        public String getNewsTitleAR() {
            return newsTitleAR;
        }

        public void setNewsTitleAR(String newsTitleAR) {
            this.newsTitleAR = newsTitleAR;
        }

        public Object getNewsTitleEN() {
            return newsTitleEN;
        }

        public void setNewsTitleEN(Object newsTitleEN) {
            this.newsTitleEN = newsTitleEN;
        }

        public String getNewsDescriptionAR() {
            return newsDescriptionAR;
        }

        public void setNewsDescriptionAR(String newsDescriptionAR) {
            this.newsDescriptionAR = newsDescriptionAR;
        }

        public Object getNewsDescriptionEN() {
            return newsDescriptionEN;
        }

        public void setNewsDescriptionEN(Object newsDescriptionEN) {
            this.newsDescriptionEN = newsDescriptionEN;
        }

        public String getNewsImage() {
            return newsImage;
        }

        public void setNewsImage(String newsImage) {
            this.newsImage = newsImage;
        }

        public String getNewsDate() {
            return newsDate;
        }

        public void setNewsDate(String newsDate) {
            this.newsDate = newsDate;
        }

        public Object getNewsDateStr() {
            return newsDateStr;
        }

        public void setNewsDateStr(Object newsDateStr) {
            this.newsDateStr = newsDateStr;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public NewsType getNewsType() {
            return newsType;
        }

        public void setNewsType(NewsType newsType) {
            this.newsType = newsType;
        }

        public Integer getLanguage() {
            return language;
        }

        public void setLanguage(Integer language) {
            this.language = language;
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

        public Integer getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(Integer totalRows) {
            this.totalRows = totalRows;
        }

    }



    public class NewsType {

        @SerializedName("NewsTypeID")
        @Expose
        private Integer newsTypeID;
        @SerializedName("NameAR")
        @Expose
        private String nameAR;
        @SerializedName("NameEN")
        @Expose
        private Object nameEN;
        @SerializedName("Language")
        @Expose
        private Integer language;
        @SerializedName("Base64")
        @Expose
        private Object base64;
        @SerializedName("Extension")
        @Expose
        private Object extension;
        @SerializedName("SharedLink")
        @Expose
        private Object sharedLink;
        @SerializedName("TotalRows")
        @Expose
        private Integer totalRows;

        public Integer getNewsTypeID() {
            return newsTypeID;
        }

        public void setNewsTypeID(Integer newsTypeID) {
            this.newsTypeID = newsTypeID;
        }

        public String getNameAR() {
            return nameAR;
        }

        public void setNameAR(String nameAR) {
            this.nameAR = nameAR;
        }

        public Object getNameEN() {
            return nameEN;
        }

        public void setNameEN(Object nameEN) {
            this.nameEN = nameEN;
        }

        public Integer getLanguage() {
            return language;
        }

        public void setLanguage(Integer language) {
            this.language = language;
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

        public Integer getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(Integer totalRows) {
            this.totalRows = totalRows;
        }

    }

}


