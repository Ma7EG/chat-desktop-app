package com.mycompany.chatapp.proxy;

import com.mycompany.chatapp.model.User;
import java.util.List;

/**
 * Interface for User Service.

 */
public interface IUserService {


    User findById(int id);

 
    User findByEmail(String email);

 
    List<User> findAll();

   
    boolean save(User user);


    boolean delete(int id);
}
