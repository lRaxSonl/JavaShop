package org.example.services;


import org.apache.shiro.crypto.hash.Sha256Hash;
import org.example.models.Product;
import org.example.models.User;
import org.example.models.UserRole;
import org.example.repositories.ProductRepository;
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
    private final ProductRepository productRepository = new ProductRepository();
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

        User user = new User(usernameInput, userPassword, UserRole.USER, 0.0);

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

    public void showAllUsers(User inputUser) {
        if(isAdmin(inputUser)) {
            List<User> allUsers = userRepository.findAll();
            for (User user : allUsers) {
                System.out.println("\nUsername: " + user.getUsername() + ", Role: " + user.getRole() +
                        ", Balance: " + user.getBalance());
            }
        }else {
            System.out.println("У вас нет прав на эту команду");
        }
    }

    public void addNewManager(User user) {
        if(isAdmin(user)) {
            showAllUsers(user);
            System.out.print("\nВведите имя пользователя чтобы добавить менеджера: ");
            String newManagerUsername = sc.nextLine();

            if(isUsernameAlreadyExists(newManagerUsername)) {
                User newManager = userRepository.findByUsername(newManagerUsername);
                userRepository.updateUserRole(newManager, UserRole.MANAGER);
            }else {
                System.out.println("Пользователь не найден\n\n");
                return;
            }
        }else {
            System.out.println("У вас нет доступа к этой команде.");
        }
    }

    public void removeManager(User user) {
        if(isAdmin(user)) {
            showAllUsers(user);
            System.out.println("\nВведите имя пользователя чтобы удалить менеджера: ");
            String managerUsername = sc.nextLine();

            if(isUsernameAlreadyExists(managerUsername)) {
                User manager = userRepository.findByUsername(managerUsername);
                userRepository.updateUserRole(manager, UserRole.USER);
            }else {
                System.out.println("Пользователь не найден\n\n");
                return;
            }
        }else {
            System.out.println("У вас нет доступа к этой команде.");
        }
    }

    public void balanceDeposit(User user) {
        System.out.print("Введите сумму пополнения баланса: ");
        double deposit =  sc.nextDouble();

        double newBalance = user.getBalance() + deposit;
        userRepository.updateUserBalance(user, newBalance);

        System.out.println("\nДепозит зачислен.\n");
    }

    public void viewProfil(User user) {
        System.out.println("\nИмя пользователя: " + user.getUsername() + ", Роль: " + user.getRole() +
                ", Баланс: " + user.getBalance());

        System.out.println("Купленные продукты: ");
        List<Product> productList = userRepository.findProductsByUser(user);
        for (Product product : productList) {
            System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice() + ", Рейтинг: " +
                    productRepository.calculateRating(product) + "\n");
        }
    }

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
}
