package de.functionfactory.user;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class userFileDataAccessService implements UserDAO {
//    private static final User[] users;

//    static {
//        File file = new File("src/de/functionfactory/users.csv");
//        Scanner scanner = null;
//
//        int fileLength = 0;
//        try {
//            scanner = new Scanner(file);
//            while (scanner.hasNext()) {
//                System.out.println(scanner.nextLine());
//                fileLength++;
//            }
//            scanner.close();
//            System.out.println(fileLength);
//
//            User[] usersFromFile = new User[fileLength];
//
//            scanner = new Scanner(file);
//            int index = 0;
//
//            while (scanner.hasNext()) {
//                String currentLine = scanner.nextLine();
//                String[] parts = currentLine.split(",");
//                User currentUser = new User(UUID.fromString(parts[0]), parts[1]);
//                usersFromFile[index] = currentUser;
//                index++;
//            }
//
//            users = usersFromFile;
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("😱" + e);
//        }
//    }

    @Override
    public User[] getUsers() {
//        return users;

        File file = new File("src/de/functionfactory/users.csv");
        Scanner scanner = null;

        int fileLength = 0;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
                fileLength++;
            }
            scanner.close();
            System.out.println(fileLength);

            User[] usersFromFile = new User[fileLength];

            scanner = new Scanner(file);
            int index = 0;

            while (scanner.hasNext()) {
                String currentLine = scanner.nextLine();
                String[] parts = currentLine.split(",");
                User currentUser = new User(UUID.fromString(parts[0]), parts[1]);
                usersFromFile[index] = currentUser;
                index++;
            }

            return usersFromFile;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("😱" + e);
        }
    }
}
