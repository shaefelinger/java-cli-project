package de.functionfactory.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDAO {
    @Override
    public List<User> getUsers() {

        File file = new File("src/de/functionfactory/users.csv");
        Scanner scanner = null;

        try {
            List<User> usersFromFile = new ArrayList<>();

            scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String[] split = scanner.nextLine().split(",");
                User currentUser = new User(UUID.fromString(split[0]), split[1]);
                usersFromFile.add(currentUser);
            }

            return usersFromFile;
        } catch (IOException e) {
            throw new RuntimeException("😱" + e);
        }
    }
}
