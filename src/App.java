import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static final int EXIT_OPTION = 0;
    private static final int VIEW_PRODUCTS_OPTION = 1;
    private static final int ADD_PRODUCT_OPTION = 2;

    public static void run() {
        Scanner sc = new Scanner(System.in);
        ProductsManager productsManager = new ProductsManager();
        boolean isWorking = true;

        try{
            do{
                System.out.print("Выход из программы - 0\nПосмотреть товары - 1\nДобавить товар - 2\nВыберете пункт из списка: ");
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
                        productsManager.addProduct();
                        break;

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
