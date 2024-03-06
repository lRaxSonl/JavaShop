package org.example.services;


import org.apache.shiro.crypto.hash.Sha256Hash;
import org.example.models.User;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Scanner;

/**
 * @author Nikita Ivkin
 * @since 07.03.2024
 * @class UserService
 * @description
 * The UserService class represents a service for user-related operations.
 *
 * This class provides methods for adding a new user, user login, and supporting operations.
 * It uses a UserRepository to interact with the database for user-related data.
 */
public class UserService {
    private final UserRepository userRepository = new UserRepository();
    private final Scanner sc = new Scanner(System.in);

    public void addNewUser() {
        boolean createUsername = true;
        String usernameInput;

        do{

            System.out.print("\nЕсли хотите выйти из задачи, введите - 0\nВведите имя пользователя: ");
            usernameInput = sc.nextLine();


            if (usernameInput.equals("0")) {
                createUsername = false;
                return; // Выход из метода
            }

            else if (isUsernameAlreadyExists(usernameInput)) {
                System.out.println("\nТакое имя пользователя уже существует\n");
            }

            else {
                createUsername = false;
            }

        }while (createUsername);

        // Пароль
        System.out.print("Введите пароль: ");
        String userPassword = sc.nextLine();

        userPassword = hashPassword(userPassword);

        User user = new User(usernameInput, userPassword, 0.0);

        userRepository.save(user);
    }

    public User userLogin() {
        boolean loginProcess = true;
        String usernameInput;

        do {

            System.out.print("\nЕсли хотите выйти из задачи, введите - 0\nВведите имя пользователя: ");
            usernameInput = sc.nextLine();

            if (usernameInput.equals("0")) {
                loginProcess = false;
                return null; // Выход из метода
            }

            System.out.print("Введите пароль: ");
            String passwordInput = sc.nextLine();

            if(isUsernameAlreadyExists(usernameInput)) {
                User user = userRepository.findByUsername(usernameInput);

                if(isCorrectPassword(passwordInput, user.getPassword())) {
                    System.out.println(user.getUsername() + ", вы успешно авторизировались\n");
                    loginProcess = false;
                    return user;
                }else {
                    System.out.println("\nНеверное имя пользователя или пароль. Попробуйте снова.\n");
                }
            }else {
                System.out.println("\nНеверное имя пользователя или пароль. Попробуйте снова.\n");
            }

        }while (loginProcess);
        return null;
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
}
