package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Team on 1/21/2018.
 */

public class EventDetailsModel {

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

        @SerializedName("EventID")
        @Expose
        private Integer eventID;
        @SerializedName("EventNameAR")
        @Expose
        private String eventNameAR;
        @SerializedName("EventNameEN")
        @Expose
        private Object eventNameEN;
        @SerializedName("EventDescriptionAR")
        @Expose
        private String eventDescriptionAR;
        @SerializedName("EventDescriptionEN")
        @Expose
        private Object eventDescriptionEN;
        @SerializedName("EventPlaceAR")
        @Expose
        private String eventPlaceAR;
        @SerializedName("EventPlaceEN")
        @Expose
        private Object eventPlaceEN;
        @SerializedName("EventImage")
        @Expose
        private String eventImage;
        @SerializedName("EventStartDate")
        @Expose
        private String eventStartDate;
        @SerializedName("EventStartDate_Str")
        @Expose
        private String eventStartDateStr;
        @SerializedName("EventEndDate")
        @Expose
        private String eventEndDate;
        @SerializedName("EventEndDate_Str")
        @Expose
        private String eventEndDateStr;
        @SerializedName("CreateDate")
        @Expose
        private String createDate;
        @SerializedName("Longtiude")
        @Expose
        private String longtiude;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("EventStartTime")
        @Expose
        private String eventStartTime;
        @SerializedName("EventStartTime_Str")
        @Expose
        private String eventStartTimeStr;
        @SerializedName("EventEndTime")
        @Expose
        private String eventEndTime;
        @SerializedName("EventEndTime_Str")
        @Expose
        private String eventEndTimeStr;
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
        @SerializedName("TotalRows")
        @Expose
        private Integer totalRows;

        public Integer getEventID() {
            return eventID;
        }

        public void setEventID(Integer eventID) {
            this.eventID = eventID;
        }

        public String getEventNameAR() {
            return eventNameAR;
        }

        public void setEventNameAR(String eventNameAR) {
            this.eventNameAR = eventNameAR;
        }

        public Object getEventNameEN() {
            return eventNameEN;
        }

        public void setEventNameEN(Object eventNameEN) {
            this.eventNameEN = eventNameEN;
        }

        public String getEventDescriptionAR() {
            return eventDescriptionAR;
        }

        public void setEventDescriptionAR(String eventDescriptionAR) {
            this.eventDescriptionAR = eventDescriptionAR;
        }

        public Object getEventDescriptionEN() {
            return eventDescriptionEN;
        }

        public void setEventDescriptionEN(Object eventDescriptionEN) {
            this.eventDescriptionEN = eventDescriptionEN;
        }

        public String getEventPlaceAR() {
            return eventPlaceAR;
        }

        public void setEventPlaceAR(String eventPlaceAR) {
            this.eventPlaceAR = eventPlaceAR;
        }

        public Object getEventPlaceEN() {
            return eventPlaceEN;
        }

        public void setEventPlaceEN(Object eventPlaceEN) {
            this.eventPlaceEN = eventPlaceEN;
        }

        public String getEventImage() {
            return eventImage;
        }

        public void setEventImage(String eventImage) {
            this.eventImage = eventImage;
        }

        public String getEventStartDate() {
            return eventStartDate;
        }

        public void setEventStartDate(String eventStartDate) {
            this.eventStartDate = eventStartDate;
        }

        public String getEventStartDateStr() {
            return eventStartDateStr;
        }

        public void setEventStartDateStr(String eventStartDateStr) {
            this.eventStartDateStr = eventStartDateStr;
        }

        public String getEventEndDate() {
            return eventEndDate;
        }

        public void setEventEndDate(String eventEndDate) {
            this.eventEndDate = eventEndDate;
        }

        public String getEventEndDateStr() {
            return eventEndDateStr;
        }

        public void setEventEndDateStr(String eventEndDateStr) {
            this.eventEndDateStr = eventEndDateStr;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getLongtiude() {
            return longtiude;
        }

        public void setLongtiude(String longtiude) {
            this.longtiude = longtiude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getEventStartTime() {
            return eventStartTime;
        }

        public void setEventStartTime(String eventStartTime) {
            this.eventStartTime = eventStartTime;
        }

        public String getEventStartTimeStr() {
            return eventStartTimeStr;
        }

        public void setEventStartTimeStr(String eventStartTimeStr) {
            this.eventStartTimeStr = eventStartTimeStr;
        }

        public String getEventEndTime() {
            return eventEndTime;
        }

        public void setEventEndTime(String eventEndTime) {
            this.eventEndTime = eventEndTime;
        }

        public String getEventEndTimeStr() {
            return eventEndTimeStr;
        }

        public void setEventEndTimeStr(String eventEndTimeStr) {
            this.eventEndTimeStr = eventEndTimeStr;
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

        public Integer getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(Integer totalRows) {
            this.totalRows = totalRows;
        }

    }

}
