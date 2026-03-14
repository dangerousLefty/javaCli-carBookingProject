package com.hamza.user;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Optional;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO{

    private static final String file = "src/main/java/com/hamza/users.csv";
    private static User[] userList;

    static {
        int count = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.readLine() != null){
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        userList = new User[count];

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            int insertPtr = 0;
            while ( (line = br.readLine()) != null){
                String[] values = line.split(",");
                User user = new User(UUID.fromString(values[0]), values[1]);
                userList[insertPtr] = user;
                insertPtr++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public User[] getUsers() {
        return userList;
    }

    @Override
    public Optional<User> findUserById(UUID id) {
        //Optional<User> returnUser;
        for (User u : userList){
            if (u.getUserID().equals(id)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }
}
