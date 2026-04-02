package com.hamza.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {
    List<User> getUsers();

    Optional<User> findUserById(UUID id);
}
