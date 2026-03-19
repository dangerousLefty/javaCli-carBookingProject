package com.hamza.user;

import java.util.Optional;
import java.util.UUID;

public interface UserDAO {
    User[] getUsers();
    Optional<User> findUserById(UUID id);
}
