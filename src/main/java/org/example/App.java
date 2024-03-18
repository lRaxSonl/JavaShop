package org.example;

import jakarta.persistence.EntityManager;
import org.example.models.User;
import org.example.services.ProductService;
import org.example.services.UserPurchaseService;
import org.example.services.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Nikita Ivkin
 * @since 07.03.2024
 * @сlass App
 * @description The class representing the application interface
 */
public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final ProductService productService = new ProductService();
    private static final UserPurchaseService userPurchaseService = new UserPurchaseService();
    private static User user;


    private static final int EXIT_OPTION = 0;

    private static final int LOGIN_AS_CUSTOMER = 1;
    private static final int ADD_CUSTOMER_OPTION = 2;


    private static final int VIEW_PRODUCTS_OPTION = 1;
    private static final int ADD_PRODUCT_OPTION = 2;
    private static final int VIEW_CUSTOMERS_OPTION = 3;
    private static final int BUY_PRODUCT_OPTION = 4;
    private static final int EDIT_CUSTOMER_OPTION = 5;
    private static final int ADD_NEW_MANAGER_OPTION = 6;
    private static final int REMOVE_MANAGER_OPTION = 7;
    private static final int DEPOSIT_OPTION = 8;
    private static final int VIEW_PROFILE_OPTION = 9;
    private static final int VIEW_RATING_OPTION = 10;

    public static void run() {
        EntityManager em = Singleton.getConnection();

        boolean isWorking = loginProcess(em);

        userService.createAdmin();

        if(isWorking == true) {
            try {
                do {
                    System.out.print("Выход из программы - 0\nПосмотреть товары - 1\n" +
                            "Добавить товар - 2\nПосмотреть зарегистрированных покупателей - 3" +
                            "\nКупить товар - 4\n" +
                            "Редактировать покупателя - 5\nДобавить менеджера - 6\n" +
                            "Пополнить баланс - 8\nПосмотреть профиль - 9\n" +
                            "Рейтинг продуктов - 10\n" + "Выберете пункт из списка: ");

                    int usr = sc.nextInt();
                    sc.nextLine();

                    switch (usr) {
                        case EXIT_OPTION:
                            isWorking = false;
                            System.out.println();
                            break;

                        case VIEW_PRODUCTS_OPTION:
                            productService.viewAllProducts();
                            break;

                        case ADD_PRODUCT_OPTION:
                            System.out.println("Вы уверены что хотите добавить товар? (1 - Да, 0 - Нет): ");
                            if (sc.nextInt() != 0) {
                                productService.addNewProducts(user);
                                break;
                            }else {
                                break;
                            }

                        case VIEW_CUSTOMERS_OPTION:
                            userService.showAllUsers(user);
                            break;


                        case BUY_PRODUCT_OPTION:
                            System.out.println("Вы уверены что хотите купить продукты? (1 - Да, 0 - Нет):");
                            if (sc.nextInt() != 0) {
                                userPurchaseService.buyProduct(user);
                                break;
                            }else {
                                break;
                            }


                        case EDIT_CUSTOMER_OPTION:
                            System.out.println("Вы уверены что хотите отредактировать свой профиль? (1 - Да, 0 - Нет):");
                            //Code here
                            break;

                        case ADD_NEW_MANAGER_OPTION:
                            System.out.println("Вы уверены что хотите добавить менэджера? (1 - Да, 0 - Нет): ");
                            if (sc.nextInt() != 0) {
                                userService.addNewManager(user);
                                break;
                            }else {
                                break;
                            }

                        case REMOVE_MANAGER_OPTION:
                            System.out.println("Вы уверены что хотите удалить менэджера? (1 - Да, 0 - Нет): ");
                            if (sc.nextInt() != 0) {
                                userService.removeManager(user);
                                break;
                            }else {
                                break;
                            }

                        case DEPOSIT_OPTION:
                            System.out.println("Вы уверены что хотите пополнить баланс? (1 - Да, 0 - Нет): ");
                            if (sc.nextInt() != 0) {
                                userService.balanceDeposit(user);
                                break;
                            }else {
                                break;
                            }

                        case VIEW_PROFILE_OPTION:
                            userService.viewProfil(user);
                            break;

                        case VIEW_RATING_OPTION:
                            productService.viewAllProductsRating();
                            break;

                        default:
                            System.out.println("\nВыбран некоректный пункт\n\n");
                    }

                } while (isWorking);

            } catch (InputMismatchException e) {
                System.out.println("\nОшибка ввода " + e);

            } finally {
                sc.close();
                System.out.println("Выход...");
            }
        }
    }


    private static boolean loginProcess(EntityManager em) {
        boolean islogin_process = true;
        boolean isWorking = false;

        try {

            do {
                System.out.print("Выход из программы - 0" +
                        "\nВойти в аккаунт - 1\nЗарегистрироватся как покупатель - 2\n" +
                        "\nВыберете пункт из списка: ");
                int usr = sc.nextInt();
                sc.nextLine();

                switch (usr) {
                    case EXIT_OPTION:
                        System.out.println();

                        islogin_process = false;
                        break;


                    case LOGIN_AS_CUSTOMER:
                        user = userService.userLogin();

                        if (user != null) {
                            islogin_process = false;
                            isWorking = true; //Программа продолжает свою работу
                        } else {
                            islogin_process = false;
                            isWorking = false; //Программа заканчивает свою работу
                        }
                        break;

                    case ADD_CUSTOMER_OPTION:
                        userService.addNewUser();
                        break;


                    default:
                        System.out.println("\nВыбран некоректный пункт\n\n");
                }


            } while (islogin_process);

        } catch (InputMismatchException e) {
            System.out.println("\nОшибка ввода: " + e);

        } finally {
            System.out.println("Выход...");
        }
        return isWorking;
    }
}
