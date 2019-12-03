package com.WattanArt.model.Request;

public class ChangePasswordRequestModel {
    /**
     * OldPassword : 123456a
     * NewPassword : 1234567
     * id : 20301ba9-ac61-4e18-b610-39d8251849ce
     */

    private String OldPassword;
    private String NewPassword;
    private String id;

    public ChangePasswordRequestModel(String oldPassword, String newPassword, String id) {
        OldPassword = oldPassword;
        NewPassword = newPassword;
        this.id = id;
    }

    public void setOldPassword(String OldPassword) {
        this.OldPassword = OldPassword;
    }

    public void setNewPassword(String NewPassword) {
        this.NewPassword = NewPassword;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getId() {
        return id;
    }
}
