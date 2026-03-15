package com.hamza.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO{

    private static User[] userList;

    static {
        File file = new File("src/main/java/com/hamza/users.csv");
        boolean check =  file.exists();
        int count = 0;
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                count++;
                scanner.nextLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scanner.close();
        userList = new User[count];

        int index = 0;

        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String[] values = scanner.nextLine().split(",");
                userList[index] = new User(UUID.fromString(values[0]), values[1]);
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User[] getUsers() {
        return userList;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        for (User u : userList){
            if (u.getUserID().equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
