package com.hamza.user;

import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService {
    //private final UserArrayDataAccessService userDAO = new UserArrayDataAccessService();
    private final UserFileDataAccessService userDAO = new UserFileDataAccessService();
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
