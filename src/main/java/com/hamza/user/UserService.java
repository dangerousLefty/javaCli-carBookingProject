package com.hamza.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService {
    private final UserDAOLists userDAO;

    public UserService(UserDAOLists userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(UUID id){
        return userDAO.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ User not found with id ".concat(id.toString())
                ));
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

}
