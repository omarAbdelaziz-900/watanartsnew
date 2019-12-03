package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Team on 1/14/2018.
 */

public class AboutModel {
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
        @SerializedName("MissionAR")
        @Expose
        private String missionAR;
        @SerializedName("MissionEN")
        @Expose
        private Object missionEN;
        @SerializedName("VisionAR")
        @Expose
        private String visionAR;
        @SerializedName("VisionEN")
        @Expose
        private Object visionEN;
        @SerializedName("DescriptionAR")
        @Expose
        private String descriptionAR;
        @SerializedName("DescriptionEN")
        @Expose
        private Object descriptionEN;
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

        public String getMissionAR() {
            return missionAR;
        }

        public void setMissionAR(String missionAR) {
            this.missionAR = missionAR;
        }

        public Object getMissionEN() {
            return missionEN;
        }

        public void setMissionEN(Object missionEN) {
            this.missionEN = missionEN;
        }

        public String getVisionAR() {
            return visionAR;
        }

        public void setVisionAR(String visionAR) {
            this.visionAR = visionAR;
        }

        public Object getVisionEN() {
            return visionEN;
        }

        public void setVisionEN(Object visionEN) {
            this.visionEN = visionEN;
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
