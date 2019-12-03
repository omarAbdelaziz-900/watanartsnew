package com.WattanArt.model.Request;

/**
 * Created by Android Team on 1/28/2018.
 */

public class EventDetailsRequestModel {

    int  EventID ;


    String Language;

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int eventID) {
        EventID = eventID;
    }

    public EventDetailsRequestModel(String language, int eventID) {

        Language = language;
        EventID = eventID;
    }
}
