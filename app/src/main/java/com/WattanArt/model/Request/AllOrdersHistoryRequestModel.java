package com.WattanArt.model.Request;

public class AllOrdersHistoryRequestModel {
    public AllOrdersHistoryRequestModel(int language, String userIdValue) {
        Language = language;
        CurrentPage = 1;
        UserIdValue = userIdValue;
        RowsPerPage = 50;
    }

    /**
     * Language : 1
     * CurrentPage : 1
     * UserIdValue : rsgsfgfsrrt
     * RowsPerPage : 50
     */

    private int Language;
    private int CurrentPage;
    private String UserIdValue;
    private int RowsPerPage;

    public void setLanguage(int Language) {
        this.Language = Language;
    }

    public void setCurrentPage(int CurrentPage) {
        this.CurrentPage = CurrentPage;
    }

    public void setUserIdValue(String UserIdValue) {
        this.UserIdValue = UserIdValue;
    }

    public void setRowsPerPage(int RowsPerPage) {
        this.RowsPerPage = RowsPerPage;
    }

    public int getLanguage() {
        return Language;
    }

    public int getCurrentPage() {
        return CurrentPage;
    }

    public String getUserIdValue() {
        return UserIdValue;
    }

    public int getRowsPerPage() {
        return RowsPerPage;
    }
}
