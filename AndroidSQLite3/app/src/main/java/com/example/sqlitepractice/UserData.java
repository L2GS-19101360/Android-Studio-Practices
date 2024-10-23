package com.example.sqlitepractice;

public class UserData {

    private int UserId;
    private String UserName;
    private String UserPassword;

    public UserData(int userId, String userName, String userPassword) {
        UserId = userId;
        UserName = userName;
        UserPassword = userPassword;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

}
