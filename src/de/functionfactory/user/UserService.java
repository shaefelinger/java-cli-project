package de.functionfactory.user;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
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
