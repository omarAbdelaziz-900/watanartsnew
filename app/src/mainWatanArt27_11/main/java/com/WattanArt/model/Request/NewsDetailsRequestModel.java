package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class NewsDetailsRequestModel
{

    String Language;
    int   NewsID;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getNewsID() {
        return NewsID;
    }

    public void setNewsID(int newsID) {
        NewsID = newsID;
    }

    public NewsDetailsRequestModel(String language, int newsID) {
        Language = language;
        NewsID = newsID;

    }
}
