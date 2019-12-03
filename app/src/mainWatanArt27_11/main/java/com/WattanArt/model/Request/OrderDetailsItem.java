package com.WattanArt.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by khaled.badawy on 5/8/2018.
 */

public class OrderDetailsItem {
    @SerializedName("mainImage")
    @Expose
    private String mainImage;
    @SerializedName("quantiy")
    @Expose
    private Integer quantiy;
    @SerializedName("patternID")
    @Expose
    private Integer patternID;

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(Integer quantiy) {
        this.quantiy = quantiy;
    }

    public Integer getPatternID() {
        return patternID;
    }

    public void setPatternID(Integer patternID) {
        this.patternID = patternID;
    }

}
