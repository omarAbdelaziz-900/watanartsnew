package com.WattanArt.model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 5/7/2018.
 */

public class ImageUploadResponseModel {

    @SerializedName("result")
    @Expose
    private Boolean result;



    @SerializedName("State")
    @Expose
    private Integer state;



    @SerializedName("ISResultHasData")
    @Expose
    private Integer iSResultHasData;



    @SerializedName("fileName")
    @Expose
    private String fileName;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getISResultHasData() {
        return iSResultHasData;
    }

    public void setISResultHasData(Integer iSResultHasData) {
        this.iSResultHasData = iSResultHasData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
