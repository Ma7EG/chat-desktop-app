package com.mycompany.chatapp.proxy;

import com.mycompany.chatapp.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserServiceProxy implements IUserService {

    private RealUserService realService;

    private Map<Integer, User> cacheById;

    private Map<String, User> cacheByEmail;

    public UserServiceProxy() {
        this.cacheById = new HashMap<>();
        this.cacheByEmail = new HashMap<>();
        System.out.println("UserServiceProxy: Created - Caching enabled");
    }

   
    private RealUserService getRealService() {
        if (realService == null) {
            System.out.println("UserServiceProxy: Creating RealUserService (lazy loading)");
            realService = new RealUserService();
        }
        return realService;
    }

    @Override
    public User findById(int id) {
        if (cacheById.containsKey(id)) {
            System.out.println("UserServiceProxy: Cache HIT for ID: " + id);
            return cacheById.get(id);
        }

        System.out.println("UserServiceProxy: Cache MISS for ID: " + id);
        User user = getRealService().findById(id);

        if (user != null) {
            cacheById.put(id, user);
            cacheByEmail.put(user.getEmail(), user);
        }

        return user;
    }

    @Override
    public User findByEmail(String email) {
        // Check cache first
        if (cacheByEmail.containsKey(email)) {
            System.out.println("UserServiceProxy: Cache HIT for Email: " + email);
            return cacheByEmail.get(email);
        }

        System.out.println("UserServiceProxy: Cache MISS for Email: " + email);
        User user = getRealService().findByEmail(email);

        if (user != null) {
            cacheById.put(user.getId(), user);
            cacheByEmail.put(email, user);
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        System.out.println("UserServiceProxy: Fetching all users (no cache)");
        List<User> users = getRealService().findAll();

        for (User user : users) {
            cacheById.put(user.getId(), user);
            cacheByEmail.put(user.getEmail(), user);
        }

        return users;
    }

    @Override
    public boolean save(User user) {
        boolean result = getRealService().save(user);

        if (result && user.getId() > 0) {
            cacheById.put(user.getId(), user);
            cacheByEmail.put(user.getEmail(), user);
            System.out.println("UserServiceProxy: Cache updated after save");
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        User user = cacheById.get(id);

        boolean result = getRealService().delete(id);

        if (result) {
            cacheById.remove(id);
            if (user != null) {
                cacheByEmail.remove(user.getEmail());
            }
            System.out.println("UserServiceProxy: Cache cleared after delete");
        }

        return result;
    }

    
    public void clearCache() {
        cacheById.clear();
        cacheByEmail.clear();
        System.out.println("UserServiceProxy: Cache cleared");
    }

  
    public String getCacheStats() {
        return "Cache Stats: " + cacheById.size() + " users cached by ID, " +
                cacheByEmail.size() + " users cached by email";
    }
}
