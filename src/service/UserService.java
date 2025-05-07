package service;

import objects.User;
import repository.UserRepository;

public class UserService {
    public static User findUserById(int userId) {
        for (User user : UserRepository.readUsersFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        } return null;
    }

    public static User findByFullName(String fullName) {
        for (User user : UserRepository.readUsersFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }
}
