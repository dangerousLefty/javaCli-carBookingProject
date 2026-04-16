package com.hamza.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UserFileDataAccessService implements UserDAO {

    //private static User[] userList;
    private static List<User> userList = new ArrayList<>();

    static {
        File file = new File("src/main/java/com/hamza/users.csv");
        boolean check =  file.exists();
        Scanner scanner;

        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String[] values = scanner.nextLine().split(",");
                userList.add(new User(UUID.fromString(values[0]), values[1]));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        return userList;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        /*
        for (User u : userList){
            if (u.getUserID().equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
        */
        return userList.stream()
                .filter(u -> u.getUserID().equals(id))
                .findAny();
    }
}
