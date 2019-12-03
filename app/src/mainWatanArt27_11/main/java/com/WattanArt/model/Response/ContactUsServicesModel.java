package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Android Team on 1/22/2018.
 */

public class ContactUsServicesModel {

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

        @SerializedName("ServiceID")
        @Expose
        private Integer serviceID;
        @SerializedName("ServiceNameAR")
        @Expose
        private String serviceNameAR;
        @SerializedName("ServiceNameEN")
        @Expose
        private Object serviceNameEN;
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

        public Integer getServiceID() {
            return serviceID;
        }

        public void setServiceID(Integer serviceID) {
            this.serviceID = serviceID;
        }

        public String getServiceNameAR() {
            return serviceNameAR;
        }

        public void setServiceNameAR(String serviceNameAR) {
            this.serviceNameAR = serviceNameAR;
        }

        public Object getServiceNameEN() {
            return serviceNameEN;
        }

        public void setServiceNameEN(Object serviceNameEN) {
            this.serviceNameEN = serviceNameEN;
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
