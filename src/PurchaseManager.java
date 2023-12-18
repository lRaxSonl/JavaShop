import java.util.List;
import java.util.Scanner;
import entity.Product;
import entity.Customer;
public class PurchaseManager {
    private static final Scanner sc = new Scanner(System.in);

    public void buyProduct() {
        //Выбор продукта
        ProductsManager productsManager = new ProductsManager();
        CustomersManager customersManager = new CustomersManager();
        List<Product> productsArr = productsManager.getProducts();

        Product selectedProduct;

        productsManager.printAllProducts();
        System.out.print("Выберете продукт кторый хотите купить: ");
        int productChoice;

        try {
            productChoice = sc.nextInt();
        }catch (Exception e) {
            System.out.println("Ошибка ввода: " + e + ", введите число");
            return;
        }

        if (productChoice < 1 && productChoice > productsArr.size()) {
            System.out.println("Вы выбрали невенрый пункт");
            return;
        }
        selectedProduct = productsArr.get(productChoice-1);


        //Выбор покупателя
        List<Customer> customersArr = customersManager.getCustomers();
        Customer selectedCustomer;

        customersManager.printAllCustomers();
        System.out.print("Выберете покупателя: ");
        int customerChoice;

        try {
            customerChoice = sc.nextInt();
        }catch (Exception e) {
            System.out.println("Ошибка ввода: " + e + ", введите число");
            return;
        }

        selectedCustomer = customersArr.get(customerChoice-1);

        double productPrice = selectedProduct.getPrice();
        double customerBalance = selectedCustomer.getBalance();

        if(productPrice <= customerBalance) {
            double newBalance = customerBalance - productPrice;

            //Обновляем баланс
            customersManager.updateBalanceInFile(selectedCustomer, newBalance);

            //Обновляем рейтинг
            productsManager.updateRating(selectedProduct);
            customersManager.updateRating(selectedCustomer);
            System.out.println("\n\nПокупка успешно завершена, спасибо!\n");
        } else {
            System.out.println("На счету недостаточно средств.");
        }
    }
}
