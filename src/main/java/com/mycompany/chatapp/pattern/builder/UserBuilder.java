package com.mycompany.chatapp.pattern.builder;

import com.mycompany.chatapp.model.User;

public class UserBuilder {
    private int id;
    private String email;
    private String password;
    private String displayName;
    private String status = "offline";  

    
    public UserBuilder(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

   
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getDisplayName() { return displayName; }
    public String getStatus() { return status; }

   
}