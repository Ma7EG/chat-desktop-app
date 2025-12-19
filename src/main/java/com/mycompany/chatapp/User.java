package com.mycompany.chatapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private final int id;           
    private final String username;  
    private final String password;  
    private final String email;     
    private final String status;    
    private final List<User> contacts; 

    
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.status = builder.status;
        this.contacts = builder.contacts;
    }

    
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getStatus() { return status; }
    public List<User> getContacts() { return contacts; }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", email=" + email + '}';
    }
    
    public static class UserBuilder {
       
        private int id; 
        private String username; 
        private String password;
        private String email = "";     
        private String status = "Offline"; 
        private List<User> contacts = new ArrayList<>();

        
        public UserBuilder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        
        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setStatus(String status) {
            this.status = status;
            return this;
        }
        
        public UserBuilder addContact(User contact) {
            this.contacts.add(contact);
            return this;
        }

        
        public User build() {
            return new User(this);
        }
    }
}