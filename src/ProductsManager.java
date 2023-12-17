import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import entity.Product;

public class ProductsManager {
    private static final File productsName = new File("src/data/product_name.txt");
    private static final File productsPrice = new File("src/data/product_price.txt");
    private static final File productsRating = new File("src/data/product_rating.txt");
    private static final Scanner sc = new Scanner(System.in);

    //Возращаем список продуктов
    public List<Product> getProducts() {
        List<Product> productsArr = new ArrayList<>();
        try {
            Scanner productsNameScanner = new Scanner(productsName);
            Scanner productsPriceScanner = new Scanner(productsPrice);
            Scanner productsRatingScanner = new Scanner(productsRating);

            while (productsNameScanner.hasNextLine() && productsPriceScanner.hasNextLine() && productsRatingScanner.hasNextLine()) {

                String lineName = productsNameScanner.nextLine();
                String linePrice = productsPriceScanner.nextLine();
                String lineRating = productsRatingScanner.nextLine();

                if (!linePrice.isEmpty() && !lineRating.isEmpty()) {
                    try {
                        productsArr.add(new Product(lineName, Double.parseDouble(linePrice), Integer.parseInt(lineRating)));
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка парсинга числа в строке: " + linePrice + " или " + lineRating + " Ошибка: " + e);
                    }
                } else {
                    System.out.println("Ошибка: пустая строка в файлах с ценами или рейтингом.");
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Error" + e);
        }

        return productsArr;
    }

    //Выводим список продуктов
    public void printAllProducts() {
        List<Product> products = getProducts();

        for(int i = 0;i < products.size(); i++) {
            System.out.println();
            System.out.println(i+1 + ") " + "Название: " + products.get(i).getName() + ", цена: " + products.get(i).getPrice() + ", рейтинг: " + products.get(i).getRating());
            System.out.println();
        }
        sc.nextLine(); // Добавлено для очистки символа новой строки
    }

    //Метод для записи в файл
    private void writeProductToFile(BufferedWriter writer, String data) throws IOException {
        if (!data.isEmpty()) {
            writer.newLine();
            writer.write(data);
        }else {
            writer.write(data);
        }
    }

    //Добавляем новый продукт
    public void addProduct() {
        try (BufferedWriter nameWriter = new BufferedWriter(new FileWriter(productsName, true));
             BufferedWriter priceWriter = new BufferedWriter(new FileWriter(productsPrice, true));
             BufferedWriter ratingWriter = new BufferedWriter(new FileWriter(productsRating, true))) {

            // Название
            System.out.print("Введите название товара: ");
            String productName = sc.nextLine();

            // Цена
            System.out.print("Введите цену товара: ");
            double productPrice;
            try {
                productPrice = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите цену корректно");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите цену корректно");
                return;
            }

            // Рейтинг
            System.out.print("Введите рейтинг товара: ");
            double productRating;
            try {
                productRating = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Введите рейтинг корректно");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода. Введите цену корректно");
                return;
            }


            printAllProducts();

            writeProductToFile(nameWriter, productName);
            writeProductToFile(priceWriter, Double.toString(productPrice));
            writeProductToFile(ratingWriter, Integer.toString((int) productRating));


            System.out.println("Товар успешно добавлен.");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
