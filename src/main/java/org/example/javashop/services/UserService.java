package org.example.javashop.services;


import org.apache.shiro.crypto.hash.Sha256Hash;
import org.example.javashop.controllers.UIHandler;
import org.example.javashop.models.Product;
import org.example.javashop.models.User;
import org.example.javashop.models.UserRole;
import org.example.javashop.repositories.ProductRepository;
import org.example.javashop.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final static UserRepository userRepository = new UserRepository();
    private final static ProductRepository productRepository = new ProductRepository();
    private final static UIHandler uiHandler = new UIHandler();


    public void addBalance(User user, Double deposit) {
        Double newBalance = user.getBalance() + deposit;
        userRepository.updateUserBalance(user, newBalance);
    }

    public User userLogin(String username, String password) {
        User user;
        if (isUsernameAlreadyExists(username)) {
            user = userRepository.findByUsername(username);
        }else {
            uiHandler.showAlert("Information", "Данные введены не верно.");
            return null;
        }

        if(!isCorrectPassword(password, user.getPassword())) {
            uiHandler.showAlert("Information", "Данные введены не верно.");
            return null;
        }
        return user;
    }

    //Added new user
    public void addNewUser(String username, String password) {

        if (isUsernameAlreadyExists(username)) {
            uiHandler.showAlert("Someting wrong!", "Username is already exists!");
        }else {
            userRepository.save(new User(username, hashPassword(password), UserRole.USER, 0.0));
            uiHandler.showAlert("Success!", "Now you neew to login");
        }
    }

    //Create admin
    public void createAdmin() {
        if(!isUsernameAlreadyExists("Admin")) {
            userRepository.save(new User("Admin", hashPassword("pass"), UserRole.ADMINISTRATOR, 5000.0));
        }
    }

    private boolean isUsernameAlreadyExists(String usernameInput) {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            if(user.getUsername().equals(usernameInput)) {
                return true;
            }
        }
        return false;
    }

    //Хеширует пароль
    private String hashPassword(String password) {
        return new Sha256Hash(password).toBase64();
    }


    //Вернёт true если пароли совпадают
    private boolean isCorrectPassword(String inputPassword, String hashedPassword) {
        return new Sha256Hash(inputPassword).toBase64().equals(hashedPassword);
    }


    public boolean isAdmin(User user) {
        if(user.getRole().equals(UserRole.ADMINISTRATOR)) {
            return true;
        }else {
            return false;
        }
    }

    public boolean isManager(User user) {
        if(user.getRole().equals(UserRole.MANAGER)) {
            return true;
        }else {
            return false;
        }
    }

    public void deductBalance(User user, Double productPrice) {
        Double newBalance = user.getBalance() - productPrice;
        userRepository.updateUserBalance(user, newBalance);
    }

}
