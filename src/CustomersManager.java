import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import entity.Customer;
import entity.Product;

public class CustomersManager {
    private static final File customerUsername = new File("src/data/customer/customer_username.txt");
    private static final File customerPassword = new File("src/data/customer/customer_password.txt");
    private static final File customerBalance = new File("src/data/customer/customer_balance.txt");
    private static final File customerRating = new File("src/data/customer/customer_rating.txt");
    private static final Scanner sc = new Scanner(System.in);

    public List<Customer> getCustomers() {
        List<Customer> customersArr = new ArrayList<>();
        try {
            Scanner Customers_UsernameScanner = new Scanner(customerUsername);
            Scanner Customers_PasswordScanner = new Scanner(customerPassword);
            Scanner Customers_BalanceScanner = new Scanner(customerBalance);
            Scanner Customers_RatingScanner = new Scanner(customerRating);

            while (Customers_UsernameScanner.hasNextLine() && Customers_PasswordScanner.hasNextLine() &&
                    Customers_BalanceScanner.hasNextLine() && Customers_RatingScanner.hasNextLine()) {

                String lineUsername = Customers_UsernameScanner.nextLine();
                String linePassword = Customers_PasswordScanner.nextLine();
                String lineBalance = Customers_BalanceScanner.nextLine();
                String lineRating = Customers_RatingScanner.nextLine();

                if (!lineBalance.isEmpty()) {
                    try {
                        customersArr.add(new Customer(lineUsername, linePassword, Double.parseDouble(lineBalance),
                                Integer.parseInt(lineRating)));
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка парсинга числа в строке: " + lineBalance + " Ошибка: " + e);
                    }
                } else {
                    System.out.println("Ошибка: пустая строка в файлах с ценами или рейтингом.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка: " + e);
        }

        return customersArr;
    }

    public void printAllCustomers() {
        List<Customer> customers = getCustomers();

        for (int i = 0; i < customers.size(); i++) {
            System.out.println(i + 1 + ") " + "Имя пользователя: " + customers.get(i).getUsername() +
                    ", Баланс: " + customers.get(i).getBalance() + " евро" + ", Рейтинг: " + customers.get(i).getRating());
            System.out.println();
        }
        sc.nextLine(); // Добавлено для очистки символа новой строки
    }

    private void writeCustomerToFile(BufferedWriter writer, String data) throws IOException {
        if (!data.isEmpty()) {
            writer.write(data);
            writer.newLine();
        }
    }

    //Добавляем нового покупателя
    public void addCustomer() {
        try (BufferedWriter usernameWriter = new BufferedWriter(new FileWriter(customerUsername, true));
             BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(customerPassword, true));
             BufferedWriter balanceWriter = new BufferedWriter(new FileWriter(customerBalance, true));
             BufferedWriter ratingWriter = new BufferedWriter(new FileWriter(customerRating, true))) {

            // Имя пользователя
            System.out.print("Если хотите выйти из задачи, введите - 0\nВведите имя пользователя: ");
            String customerUsernameInput = sc.nextLine();

            if (customerUsernameInput.equals("0")) {
                return; // Выход из метода
            }

            // Пароль
            System.out.print("Введите пароль: ");
            String customerPassword = sc.nextLine();

            // Баланс
            System.out.print("Введите баланс: ");
            double customerBalance;
            try {
                customerBalance = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите баланс корректно");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите баланс корректно");
                return;
            }

            printAllCustomers();

            // Записываем данные обратно в файлы
            writeCustomerToFile(usernameWriter, customerUsernameInput);
            writeCustomerToFile(passwordWriter, customerPassword);
            writeCustomerToFile(balanceWriter, Double.toString(customerBalance));
            writeCustomerToFile(ratingWriter, "0");

            System.out.println("Покупатель успешно добавлен.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void updateBalanceInFile(Customer customer, double newBalance) {
        List<Customer> customersArr = getCustomers();

        for (int i = 0; i < customersArr.size(); i++) {
            if (customer.getUsername().equals(customersArr.get(i).getUsername())) {
                customersArr.get(i).setBalance(newBalance);
            }
        }

        try (BufferedWriter usernameWriter = new BufferedWriter(new FileWriter(customerUsername));
             BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(customerPassword));
             BufferedWriter balanceWriter = new BufferedWriter(new FileWriter(customerBalance));
             BufferedWriter ratingWriter = new BufferedWriter(new FileWriter(customerRating))) {

            for (Customer existingCustomer : customersArr) {
                writeCustomerToFile(usernameWriter, existingCustomer.getUsername());
                writeCustomerToFile(passwordWriter, existingCustomer.getPassword());
                writeCustomerToFile(balanceWriter, Double.toString(existingCustomer.getBalance()));
                writeCustomerToFile(ratingWriter, Integer.toString(existingCustomer.getRating()));
            }

        } catch (IOException e) {
            System.out.println("Ошибка при обновлении баланса в файле: " + e.getMessage());
        }
    }

    public void customerEditor() {
        List<Customer> customersArr = getCustomers();

        printAllCustomers();
        System.out.print("Выберете покупателя, которого хотите отредактировать (0 для выхода): ");
        int customerChoice;

        try {
            customerChoice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ошибка ввода: введите число");
            return;
        }

        if (customerChoice == 0) {
            return; // Выход из метода
        }

        if (customerChoice < 1 && customerChoice > customersArr.size()) {
            System.out.println("Некорректный выбор покупателя.");
            return;
        }

        int customerIndex = customerChoice - 1;
        Customer selectedCustomer = customersArr.get(customerIndex);

        System.out.print("Введите пароль от пользователя: ");
        sc.nextLine(); // Очистка символа новой строки перед считыванием пароля
        String enterPassword = sc.nextLine();

        if (enterPassword.equals(selectedCustomer.getPassword())) {
            System.out.print("\nВведите новое имя пользователя: ");
            String newUsername = sc.nextLine();

            System.out.println("\nВведите новый пароль: ");
            String newPassword = sc.nextLine();

            selectedCustomer.setUsername(newUsername);
            selectedCustomer.setPassword(newPassword);

            // Теперь сохраняем обновленные данные в файлы
            saveCustomersToFile(customersArr);
        } else {
            System.out.println("Неверный пароль");
        }
    }

    private void saveCustomersToFile(List<Customer> customersArr) {
        try (BufferedWriter usernameWriter = new BufferedWriter(new FileWriter(customerUsername, false));
             BufferedWriter passwordWriter = new BufferedWriter(new FileWriter(customerPassword, false));
             BufferedWriter balanceWriter = new BufferedWriter(new FileWriter(customerBalance, false));
             BufferedWriter ratingWriter = new BufferedWriter(new FileWriter(customerRating, false))) {

            for (Customer existingCustomer : customersArr) {
                writeCustomerToFile(usernameWriter, existingCustomer.getUsername());
                writeCustomerToFile(passwordWriter, existingCustomer.getPassword());
                writeCustomerToFile(balanceWriter, Double.toString(existingCustomer.getBalance()));
                writeCustomerToFile(ratingWriter, Integer.toString(existingCustomer.getRating()));
            }

        } catch (IOException e) {
            System.out.println("Ошибка при обновлении данных в файле: " + e.getMessage());
        }
    }

    public void updateRating(Customer customer) {
        int newRating = customer.getRating() + 1;
        List<Customer> customersArr = getCustomers();

        for (int i = 0; i < customersArr.size(); i++) {
            if (customer.getUsername().equals(customersArr.get(i).getUsername())) {
                customersArr.get(i).setRating(newRating);
            }
        }

        saveCustomersToFile(customersArr);
    }
}
