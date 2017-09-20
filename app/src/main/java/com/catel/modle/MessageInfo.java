package com.catel.modle;

/**
 * Created by Administrator on 2017/8/21.
 */

public class MessageInfo {
    private String name;
    private String place;
    private String message;
    private boolean isSure;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSure() {
        return isSure;
    }

    public void setSure(boolean sure) {
        isSure = sure;
    }

    public MessageInfo() {
    }

    public MessageInfo(String name, String place, String message, boolean isSure) {

        this.name = name;
        this.place = place;
        this.message = message;
        this.isSure = isSure;
    }
}
