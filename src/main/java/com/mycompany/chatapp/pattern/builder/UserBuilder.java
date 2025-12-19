package com.mycompany.chatapp.pattern.builder;

import com.mycompany.chatapp.model.User;

public class UserBuilder {
    private String email;
    private String password;
    private String displayName;
    private String phone;
    private String status = "offline";

    public UserBuilder(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserBuilder setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public UserBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    
    public User build() {
        User user = new User(email, password, displayName, phone);
        user.setStatus(status);
        return user;
    }
}