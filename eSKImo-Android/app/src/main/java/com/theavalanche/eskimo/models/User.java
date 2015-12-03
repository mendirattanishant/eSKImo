package com.theavalanche.eskimo.models;

public class User {

    private String user_id;
    private String email_id;
    private String password;
    private String name;
    private String tagline;
    private String dpUrl;

    public User(String id, String email, String password){
        this.user_id = id;
        this.email_id = email;
        this.password = password;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getEmail() {
        return email_id;
    }

    public void setEmail(String email) {
        this.email_id = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDpUrl() {
        return dpUrl;
    }

    public void setDpUrl(String dpUrl) {
        this.dpUrl = dpUrl;
    }

}
