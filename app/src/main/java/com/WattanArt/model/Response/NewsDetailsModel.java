package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Team on 1/21/2018.
 */

public class    NewsDetailsModel {

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
        @SerializedName("NewsID")
        @Expose
        private Integer newsID;
        @SerializedName("NewsTypeID")
        @Expose
        private Integer newsTypeID;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("NewsImage")
        @Expose
        private String newsImage;
        @SerializedName("NewsDate")
        @Expose
        private String newsDate;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("TypeName")
        @Expose
        private String typeName;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
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
        @SerializedName("PagesCount")
        @Expose
        private Integer pagesCount;
        @SerializedName("CurrentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("RowsPerPage")
        @Expose
        private Integer rowsPerPage;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
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
