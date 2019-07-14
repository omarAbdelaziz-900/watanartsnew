package com.WattanArt.model.Response;

/**
 * Created by Android Team on 1/21/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManegerWords {

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

        @SerializedName("ContentID")
        @Expose
        private Integer contentID;
        @SerializedName("ContentName")
        @Expose
        private String contentName;
        @SerializedName("MangerNameAR")
        @Expose
        private String mangerNameAR;
        @SerializedName("MangerNameEN")
        @Expose
        private Object mangerNameEN;
        @SerializedName("ManagerPhoto")
        @Expose
        private String managerPhoto;
        @SerializedName("ManagerWordAR")
        @Expose
        private String managerWordAR;
        @SerializedName("ManagerWordEN")
        @Expose
        private Object managerWordEN;
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

        public Integer getContentID() {
            return contentID;
        }

        public void setContentID(Integer contentID) {
            this.contentID = contentID;
        }

        public String getContentName() {
            return contentName;
        }

        public void setContentName(String contentName) {
            this.contentName = contentName;
        }

        public String getMangerNameAR() {
            return mangerNameAR;
        }

        public void setMangerNameAR(String mangerNameAR) {
            this.mangerNameAR = mangerNameAR;
        }

        public Object getMangerNameEN() {
            return mangerNameEN;
        }

        public void setMangerNameEN(Object mangerNameEN) {
            this.mangerNameEN = mangerNameEN;
        }

        public String getManagerPhoto() {
            return managerPhoto;
        }

        public void setManagerPhoto(String managerPhoto) {
            this.managerPhoto = managerPhoto;
        }

        public String getManagerWordAR() {
            return managerWordAR;
        }

        public void setManagerWordAR(String managerWordAR) {
            this.managerWordAR = managerWordAR;
        }

        public Object getManagerWordEN() {
            return managerWordEN;
        }

        public void setManagerWordEN(Object managerWordEN) {
            this.managerWordEN = managerWordEN;
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
