package com.hpu.sencondhand.bean;

/**
 * Created by：何学慧
 * Detail:
 * on 2019/12/30
 */

public class register {
    private String userID;
    private String name;
    private String telNumber;
    private String major;
    private String password;

    public register(String userID, String name, String telNumber, String major, String password) {
        this.userID = userID;
        this.name = name;
        this.telNumber = telNumber;
        this.major = major;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "register{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", major='" + major + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
