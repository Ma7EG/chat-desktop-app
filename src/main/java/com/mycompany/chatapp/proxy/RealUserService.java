package com.mycompany.chatapp.proxy;

import com.mycompany.chatapp.model.User;
import java.util.List;

public class RealUserService implements IUserService {

    public RealUserService() {
        System.out.println("RealUserService: Created - Direct database access");
    }

    @Override
    public User findById(int id) {
        System.out.println("RealUserService: Fetching user from database, ID: " + id);
        return User.find(id);
    }

    @Override
    public User findByEmail(String email) {
        System.out.println("RealUserService: Fetching user from database, Email: " + email);
        return User.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        System.out.println("RealUserService: Fetching all users from database");
        return User.findAll();
    }

    @Override
    public boolean save(User user) {
        System.out.println("RealUserService: Saving user to database");
        return user.save();
    }

    @Override
    public boolean delete(int id) {
        System.out.println("RealUserService: Deleting user from database, ID: " + id);
        User user = User.find(id);
        if (user != null) {
            return user.delete();
        }
        return false;
    }
}
