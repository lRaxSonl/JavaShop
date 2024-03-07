package org.example;

import jakarta.persistence.EntityManager;
import org.example.models.User;
import org.example.services.ProductsService;
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
    private static final ProductsService productService = new ProductsService();
    private static User user;


    private static final int EXIT_OPTION = 0;

    private static final int LOGIN_AS_CUSTOMER = 1;
    private static final int ADD_CUSTOMER_OPTION = 2;


    private static final int VIEW_PRODUCTS_OPTION = 1;
    private static final int ADD_PRODUCT_OPTION = 2;
    private static final int VIEW_CUSTOMERS_OPTION = 3;
    private static final int BUY_PRODUCT_OPTION = 4;
    private static final int EDIT_CUSTOMER_OPTION = 5;

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
                            "Редактировать покупателя - 5\n\nВыберете пункт из списка: ");

                    int usr = sc.nextInt();
                    sc.nextLine();

                    switch (usr) {
                        case EXIT_OPTION:
                            isWorking = false;
                            em.close();
                            System.out.println();
                            break;

                        case VIEW_PRODUCTS_OPTION:
                            //Code here
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
                            //Code here
                            break;


                        case BUY_PRODUCT_OPTION:
                            System.out.println("Вы уверены что хотите купить продукты? (1 - Да, 0 - Нет):");
                            //Code here
                            break;


                        case EDIT_CUSTOMER_OPTION:
                            System.out.println("Вы уверены что хотите отредактировать покупалея? (1 - Да, 0 - Нет):");
                            //Code here
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
        do {
            System.out.print("Выход из программы - 0" +
                    "\nВойти в аккаунт - 1\nЗарегистрироватся как покупатель - 2\n" +
                    "\nВыберете пункт из списка: ");
            int usr = sc.nextInt();
            sc.nextLine();

            switch (usr) {
                case EXIT_OPTION:
                    em.close();
                    System.out.println();

                    islogin_process = false;
                    break;


                case LOGIN_AS_CUSTOMER:
                    user = userService.userLogin();

                    islogin_process = false;
                    isWorking = true; //Программа продолжает свою работу
                    break;

                case ADD_CUSTOMER_OPTION:
                    userService.addNewUser();
                    break;


                default:
                    System.out.println("\nВыбран некоректный пункт\n\n");
            }


        }while (islogin_process);

        return isWorking;
    }
}
