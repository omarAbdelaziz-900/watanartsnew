package com.WattanArt.model.Request;

public class SelectCountryCitiyListsRequestModel {
    /**
     * Language : 1
     */

    private int Language;

    public SelectCountryCitiyListsRequestModel(int language) {
        Language = language;
    }

    public void setLanguage(int Language) {
        this.Language = Language;
    }

    public int getLanguage() {
        return Language;
    }
}
