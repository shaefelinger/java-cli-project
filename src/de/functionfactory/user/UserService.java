package de.functionfactory.user;

import java.util.UUID;

public class UserService {
    private final UserDAO userDao = new UserDAO();

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(UUID id) {
        for(User user : getUsers()) {
            if (user.getUuid().equals(id)) {
                return user;
            }
        }
        return null;
    }
}
