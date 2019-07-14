

package com.WattanArt.model.Response;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

/**
 * Created by Android Team on 1/21/2018.
 */


public class GeneralResponse {
    @SerializedName("result")
    @Expose
    private boolean Result;
    @SerializedName("ISResultHasData")
    @Expose
    private int ISResultHasData;

    public int getISResultHasData() {
        return ISResultHasData;
    }

    public boolean isResult() {
        return Result;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

}

