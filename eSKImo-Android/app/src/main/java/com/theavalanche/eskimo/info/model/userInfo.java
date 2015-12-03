package com.theavalanche.eskimo.info.model;

public class UserInfo {

    String user_id;
    String email_id;
    String name;
    String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}