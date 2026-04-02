package com.hamza.user;

import java.util.Optional;
import java.util.UUID;

public class UserArrayDataAccessService {

    private static final User[] userList;

    static {
        userList = new User[]{

                new User(UUID.fromString("f41c329c-a1ad-4a06-9fdc-695c7e9cb830"), "Montana"),
                new User(UUID.fromString("a930642e-e717-48df-99c5-bde3766ff0a4"), "Austin"),
                new User(UUID.fromString("f41c329c-a1ad-4a06-9fdc-695d7e9cb830"), "Wilmington"),
                new User(UUID.fromString("87994382-6bff-460e-9f1d-57410415e94c"), "Trevor"),
                new User(UUID.fromString("fe9d0e1a-2abe-4918-8278-cd1663d4b470"), "Tony"),
                new User(UUID.fromString("ab0edfce-e78f-4419-9ae9-b930e1e38856"), "Malcolm X"),
                new User(UUID.fromString("d76d1e3a-03c0-4439-a4c8-b72add46c629"), "Ebenezer"),
                new User(UUID.fromString("1bfc0468-b666-4176-875c-2d281d6056a8"), "Francis"),
                new User(UUID.fromString("3c7536c2-0092-4786-b9e6-313d221a4eeb"), "Agustus"),
                new User(UUID.fromString("7a814e87-d7a0-4973-b950-6f4caa83e3bc"), "Sherlock")
        };
    }

    public User[] getUsers(){
        return userList;
    }

    public Optional<User> findUserById(UUID id){
        //Optional<User> returnUser;
        for (User u : userList){
            if (u.getUserID().equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

}
