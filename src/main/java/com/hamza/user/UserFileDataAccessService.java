package com.hamza.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class UserFileDataAccessService implements UserDAO {

    private static List<User> userList = new ArrayList<>();

    static {
        //File file = new File("src/main/java/com/hamza/users.csv");
        try{
            URL resource = UserFileDataAccessService.class.getClassLoader().getResource("users.csv");
            if (resource == null) {
                throw new RuntimeException("users.csv not found in resources");
            }

            File file = new File(resource.getPath());
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                String[] values = scanner.nextLine().split(",");
                userList.add(new User(UUID.fromString(values[0]), values[1]));
            }

            scanner.close();
        }

         catch (FileNotFoundException e) {
            throw new RuntimeException(e);
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
