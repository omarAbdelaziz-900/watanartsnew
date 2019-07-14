package com.WattanArt.model.Response;

public class PasswordResponseModel {
    /**
     * result : true
     * ISResultHasData : 1
     */

    private boolean result;
    private int ISResultHasData;

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setISResultHasData(int ISResultHasData) {
        this.ISResultHasData = ISResultHasData;
    }

    public boolean getResult() {
        return result;
    }

    public int getISResultHasData() {
        return ISResultHasData;
    }
}
