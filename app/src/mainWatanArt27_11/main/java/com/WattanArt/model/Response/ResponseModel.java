package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Team on 1/21/2018.
 */


public class ResponseModel {
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("ISResultHasData")
    @Expose
    private int ISResultHasData;

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Success")
        @Expose
        private boolean success;

        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("ISResultHasData")
        @Expose
        private Integer iSResultHasData;

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Integer getISResultHasData() {
            return iSResultHasData;
        }

        public void setISResultHasData(Integer iSResultHasData) {
            this.iSResultHasData = iSResultHasData;
        }

    }
}
