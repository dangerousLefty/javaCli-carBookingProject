package com.hamza.user;

import java.util.NoSuchElementException;
import java.util.UUID;

public class UserService {
    //private final UserArrayDataAccessService userArrayDataAccessService = new UserArrayDataAccessService();
    private final UserFileDataAccessService userFileDataAccessService = new UserFileDataAccessService();
    public User getUser(UUID id){
        //return userArrayDataAccessService.findUserById(id)
        return userFileDataAccessService.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "❌ User not found with id ".concat(id.toString())
                ));
    }

    public User[] getUsers() {
        //return userArrayDataAccessService.getUsers();
        return userFileDataAccessService.getUsers();
    }

}
