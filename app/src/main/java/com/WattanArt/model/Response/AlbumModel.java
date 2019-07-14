package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android Team on 1/21/2018.
 */

public class AlbumModel {
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("otherResult")
    @Expose
    private List<Result> otherResult = null;
    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;

    public List<Result> getResult() {
//        result.addAll(getOtherResult());
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public List<Result> getOtherResult() {
        return otherResult;
    }

    public void setOtherResult(List<Result> otherResult) {
        this.otherResult = otherResult;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public class Result {

        @SerializedName("AlbumID")
        @Expose
        private Integer albumID;
        @SerializedName("AlbumNameAR")
        @Expose
        private String albumNameAR;
        @SerializedName("AlbumNameEN")
        @Expose
        private Object albumNameEN;
        @SerializedName("AlbumDescriptionAR")
        @Expose
        private String albumDescriptionAR;
        @SerializedName("AlbumDescriptionEN")
        @Expose
        private Object albumDescriptionEN;
        @SerializedName("AlbumImage")
        @Expose
        private String albumImage;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
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

        public Integer getAlbumID() {
            return albumID;
        }

        public void setAlbumID(Integer albumID) {
            this.albumID = albumID;
        }

        public String getAlbumNameAR() {
            return albumNameAR;
        }

        public void setAlbumNameAR(String albumNameAR) {
            this.albumNameAR = albumNameAR;
        }

        public Object getAlbumNameEN() {
            return albumNameEN;
        }

        public void setAlbumNameEN(Object albumNameEN) {
            this.albumNameEN = albumNameEN;
        }

        public String getAlbumDescriptionAR() {
            return albumDescriptionAR;
        }

        public void setAlbumDescriptionAR(String albumDescriptionAR) {
            this.albumDescriptionAR = albumDescriptionAR;
        }

        public Object getAlbumDescriptionEN() {
            return albumDescriptionEN;
        }

        public void setAlbumDescriptionEN(Object albumDescriptionEN) {
            this.albumDescriptionEN = albumDescriptionEN;
        }

        public String getAlbumImage() {
            return albumImage;
        }

        public void setAlbumImage(String albumImage) {
            this.albumImage = albumImage;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
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
