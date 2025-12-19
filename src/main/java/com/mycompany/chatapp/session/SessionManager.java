package com.mycompany.chatapp.session;

import com.mycompany.chatapp.model.User;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;
    private SessionManager() {
    }

    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    
    public void login(User user) {
        this.currentUser = user;
        if (user != null) {
            user.setStatus("online");
            user.save();
        }
        System.out.println("Session started for: " + (user != null ? user.getDisplayName() : "null"));
    }

    
    public void logout() {
        if (currentUser != null) {
            currentUser.setStatus("offline");
            currentUser.save();
            System.out.println("Session ended for: " + currentUser.getDisplayName());
        }
        currentUser = null;
    }

    
    public User getCurrentUser() {
        return currentUser;
    }

    
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    
    public int getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : -1;
    }
}