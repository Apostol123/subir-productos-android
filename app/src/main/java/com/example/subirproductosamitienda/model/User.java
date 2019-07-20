package com.example.subirproductosamitienda.model;

import com.google.gson.annotations.SerializedName;

public class User {

    String id;
    String sessionId;
    String username;
    String password;
    @SerializedName("body")
    String firstName;
    String secondName;
    String email;
    String token ;




    public User(String id, String username, String password, String firstName, String secondName, String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
    }

    public User(String id, String sessionId, String username, String password,String token) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.sessionId=sessionId;
        this.token=token;

    }

    public User() {


    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
