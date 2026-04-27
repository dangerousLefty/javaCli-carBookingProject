package com.hamza.user;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserFakerDataAccessService implements UserDAO {

    private static List<User> userList = new ArrayList<>();
    static {
        Faker faker = new Faker();

        for (int i = 0; i < 20; i++){
            userList.add(new User(UUID.randomUUID(), faker.name().fullName()));
        }
    }

    @Override
    public List<User> getUsers() {
        return userList;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        return userList.stream()
                .filter(u -> id.equals(u.getUserID()))
                .findFirst();
    }
}
