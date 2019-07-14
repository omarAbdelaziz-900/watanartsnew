package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class LanguageRequestModel {

    String Language;

    int CurrentPage , RowsPerPage ;

    /*
    CurrentPage:1,
RowsPerPage:10

     */
    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public LanguageRequestModel(String language, int currentPage, int rowsPerPage) {
        Language = language;
        CurrentPage = currentPage;
        RowsPerPage = rowsPerPage;
    }

    public LanguageRequestModel(String language) {
        Language = language;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        CurrentPage = currentPage;
    }

    public int getRowsPerPage() {
        return RowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        RowsPerPage = rowsPerPage;
    }
}
