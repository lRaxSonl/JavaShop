import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static final int EXIT_OPTION = 0;
    private static final int VIEW_PRODUCTS_OPTION = 1;
    private static final int ADD_PRODUCT_OPTION = 2;
    private static final int ADD_CUSTOMER_OPTION = 3;
    private static final int VIEW_CUSTOMERS_OPTION = 4;
    private static final int BUY_PRODUCT_OPTION = 5;
    private static final int EDIT_CUSTOMER_OPTION = 6;

    public static void run() {
        Scanner sc = new Scanner(System.in);
        ProductsManager productsManager = new ProductsManager();
        CustomersManager customersManager = new CustomersManager();
        PurchaseManager purchaseManager = new PurchaseManager();
        boolean isWorking = true;

        try{
            do{
                System.out.print("Выход из программы - 0\nПосмотреть товары - 1\nДобавить товар - 2\nЗарегистрироватся как покупатель - 3\n"
                                + "Посмотреть зарегистрированных покупателей - 4 \nКупить товар - 5\nРедактировать покупателя - 6\nВыберете пункт из списка: ");

                int usr = sc.nextInt();
                sc.nextLine();

                switch (usr) {
                    case EXIT_OPTION:
                        isWorking = false;
                        System.out.println();
                        break;

                    case VIEW_PRODUCTS_OPTION:
                        productsManager.printAllProducts();
                        break;

                    case ADD_PRODUCT_OPTION:
                        System.out.println("Вы уверены что хотите добавить товар? (1 - Да, 0 - Нет):");
                        if (sc.nextInt() != 0) {
                            productsManager.addProduct();
                            break;
                        }else {
                            break;
                        }

                    case ADD_CUSTOMER_OPTION:
                        System.out.println("Вы уверены что хотите зарегистрироваться? (1 - Да, 0 - Нет):");
                        if (sc.nextInt() != 0) {
                            customersManager.addCustomer();
                            break;
                        }else {
                            break;
                        }

                    case VIEW_CUSTOMERS_OPTION:
                        customersManager.printAllCustomers();
                        break;

                    case BUY_PRODUCT_OPTION:
                        System.out.println("Вы уверены что хотите купить продукты? (1 - Да, 0 - Нет):");
                        if (sc.nextInt() != 0) {
                            purchaseManager.buyProduct();
                            break;
                        }else {
                            break;
                        }

                    case EDIT_CUSTOMER_OPTION:
                        System.out.println("Вы уверены что хотите отредактировать покупалея? (1 - Да, 0 - Нет):");
                        if (sc.nextInt() != 0) {
                            customersManager.customerEditor();
                            break;
                        }else {
                            break;
                        }


                    default:
                        System.out.println("\nВыбран некоректный пункт\n\n");
                    }

            }while (isWorking);

        }catch (InputMismatchException e) {
            System.out.println("\nОшибка ввода " + e);

        }finally {
            sc.close();
            System.out.println("Выход...");
        }
    }

}
