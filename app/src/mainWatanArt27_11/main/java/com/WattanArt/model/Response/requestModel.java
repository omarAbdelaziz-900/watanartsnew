package com.WattanArt.model.Response;

/**
 * Created by Android Team on 1/28/2018.
 */

public class requestModel {

    int Language , NewsID ;


    public requestModel(int language, int newsID) {
        Language = language;
        NewsID = newsID;
    }

    public int getLanguage() {
        return Language;
    }

    public void setLanguage(int language) {
        Language = language;
    }

    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int newsID) {
        NewsID = newsID;
    }
}
