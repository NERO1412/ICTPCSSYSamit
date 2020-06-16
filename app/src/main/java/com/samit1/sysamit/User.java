package com.samit1.sysamit;

public class User {

    String userName;
    String userStatus;
    String userId;

    public User(String userId, String userName, String userStatus) {
        //this.userId = userId;
        setUserId(userId);
        this.userName = userName;
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
