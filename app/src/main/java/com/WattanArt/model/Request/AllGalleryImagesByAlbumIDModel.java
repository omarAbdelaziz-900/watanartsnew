package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class AllGalleryImagesByAlbumIDModel {

    int AlbumID;
    String Language ;

    int RowsPerPage;
    int pageNumber;


    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {

        Language = language;
    }

    public AllGalleryImagesByAlbumIDModel(int albumID, String language, int RowsPerPage, int pageNumber) {
        AlbumID = albumID;
        Language = language;
        this.RowsPerPage = RowsPerPage;
        this.pageNumber = pageNumber;
    }

    public int getRowsPerPage() {
        return RowsPerPage;
    }

    public void setRowsPerPage(int RowsPerPage) {
        this.RowsPerPage = RowsPerPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
