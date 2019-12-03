package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Android Team on 1/21/2018.
 */

public class GalleryItemModel {
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

        @SerializedName("MediaID")
        @Expose
        private Integer mediaID;
        @SerializedName("AlbumID")
        @Expose
        private Integer albumID;
        @SerializedName("MediaType")
        @Expose
        private Integer mediaType;
        @SerializedName("AttachedFile")
        @Expose
        private String attachedFile;
        @SerializedName("ThumbFile")
        @Expose
        private String thumbFile;
        @SerializedName("DescriptionAR")
        @Expose
        private String descriptionAR;
        @SerializedName("DescriptionEN")
        @Expose
        private Object descriptionEN;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("IsYoutube")
        @Expose
        private Boolean isYoutube;
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

        public Integer getMediaID() {
            return mediaID;
        }

        public void setMediaID(Integer mediaID) {
            this.mediaID = mediaID;
        }

        public Integer getAlbumID() {
            return albumID;
        }

        public void setAlbumID(Integer albumID) {
            this.albumID = albumID;
        }

        public Integer getMediaType() {
            return mediaType;
        }

        public void setMediaType(Integer mediaType) {
            this.mediaType = mediaType;
        }

        public String getAttachedFile() {
            return attachedFile;
        }

        public void setAttachedFile(String attachedFile) {
            this.attachedFile = attachedFile;
        }

        public String getThumbFile() {
            return thumbFile;
        }

        public void setThumbFile(String thumbFile) {
            this.thumbFile = thumbFile;
        }

        public String getDescriptionAR() {
            return descriptionAR;
        }

        public void setDescriptionAR(String descriptionAR) {
            this.descriptionAR = descriptionAR;
        }

        public Object getDescriptionEN() {
            return descriptionEN;
        }

        public void setDescriptionEN(Object descriptionEN) {
            this.descriptionEN = descriptionEN;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Boolean getIsYoutube() {
            return isYoutube;
        }

        public void setIsYoutube(Boolean isYoutube) {
            this.isYoutube = isYoutube;
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
