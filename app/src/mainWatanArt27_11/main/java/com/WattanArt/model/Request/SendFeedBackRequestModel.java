package com.WattanArt.model.Request;

public class SendFeedBackRequestModel {
    /**
     * FullName : FullName
     * Email : Email
     * Subject : Subject
     * Message : Message
     * ServiceID : 1
     */

    private String FullName;
    private String Email;
    private String Subject;
    private String Message;
    private int ServiceID;

    public SendFeedBackRequestModel( String email, String subject, String message) {
        Email = email;
        Subject = subject;
        Message = message;
        ServiceID = 1;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setSubject(String Subject) {
        this.Subject = Subject;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setServiceID(int ServiceID) {
        this.ServiceID = ServiceID;
    }

    public String getFullName() {
        return FullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getSubject() {
        return Subject;
    }

    public String getMessage() {
        return Message;
    }

    public int getServiceID() {
        return ServiceID;
    }
}
