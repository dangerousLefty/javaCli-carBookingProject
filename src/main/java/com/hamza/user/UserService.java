package com.hamza.user;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User getUser(UUID id){
        return userDAO.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ User not found with id ".concat(id.toString())
                ));
    }

    public User[] getUsers() {
        return userDAO.getUsers();
    }

}
