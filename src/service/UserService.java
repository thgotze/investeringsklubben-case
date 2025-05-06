package service;

import Objects.User;
import repository.UserRepository;

public class UserService {
    public static User findUserById(int userId) {
        for (User user : UserRepository.users) {
            if (user.getUserId() == userId) {
                return user;
            }
        } return null;
    }
}
