package com.hamza.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUser(UUID id) {
        return userDAO.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ User not found with id ".concat(id.toString())
                ));
    }

    public void validateUserExists(UUID id){
        getUser(id);
    }

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

}
