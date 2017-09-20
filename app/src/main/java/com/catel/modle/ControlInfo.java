package com.catel.modle;

/**
 * Created by Administrator on 2017/8/25.
 */

public class ControlInfo {
    private String place;
    private String code;

    public ControlInfo(String place, String code) {
        this.place = place;
        this.code = code;
    }

    public ControlInfo() {
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
