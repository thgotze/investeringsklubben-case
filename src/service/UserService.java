package service;

import models.User;
import repository.UserRepository;

import java.time.LocalDate;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean validatePassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    public void updateUserName(User user, String newName) {
        user.setFullName(newName);
        user.setLastUpdated(LocalDate.now());
    }

    public void updateUserEmail(User user, String newEmail) {
        user.setEmail(newEmail);
        user.setLastUpdated(LocalDate.now());
    }

    public void updateUserBirthDate(User user, LocalDate newBirthDate) {
        user.setBirthDate(newBirthDate);
        user.setLastUpdated(LocalDate.now());
    }

    public void updateUserPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setLastUpdated(LocalDate.now());
    }

    public void makeAdmin(User user) {
        user.setAdmin(true);
        user.setLastUpdated(LocalDate.now());
    }

    public void removeAdmin(User user) {
        user.setAdmin(false);
        user.setLastUpdated(LocalDate.now());
    }

    public void saveUser(User user) {
        userRepository.updateUserInFile(user);
    }

    public void createUser(String fullName, String email, LocalDate birthDay, boolean admin, String password) {
        int userId = userRepository.getUsersFromFile().getLast().getUserId() + 1;
        double initialSaldo = 100000.0;
        LocalDate createdDate = LocalDate.now();
        LocalDate lastUpdated = LocalDate.now();

        User user = new User(userId, fullName, email, birthDay, initialSaldo, createdDate, lastUpdated, admin, password);
        userRepository.addUserToFile(user);
    }

    public void deleteUser(int userIdToDelete, int currentUserId) {
        if (userIdToDelete == currentUserId) {
            System.out.println("Kan ikke slette den bruger du er logget ind på");
        }

        User userToDelete = findUserById(userIdToDelete);
        if (userToDelete == null) {
            System.out.println("Bruger ikke fundet");
        }

        userRepository.removeUserFromFile(userIdToDelete);
    }


    public User findUserById(int userId) {
        for (User user : userRepository.getUsersFromFile()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User findUserByFullName(String fullName) {
        for (User user : userRepository.getUsersFromFile()) {
            if (user.getFullName().equalsIgnoreCase(fullName)) {
                return user;
            }
        }
        return null;
    }
}