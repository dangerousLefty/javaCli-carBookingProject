package com.hamza.user;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public User getUser(String id){
        return userDAO.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ User not found with id ".concat(id)
                ));
    }

    public User[] getUsers() {
        return userDAO.getUsers();
    }

    public void getUserList(){
        for (User u : getUsers()){
            System.out.println(u.toString());
        }
    }
}
