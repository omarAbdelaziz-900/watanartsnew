package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class AllAlbumsByCatIdRequestModel {

    String Language;
    int AlbumID;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

    public AllAlbumsByCatIdRequestModel(String language, int albumID) {
        Language = language;
        AlbumID = albumID;

    }
}
