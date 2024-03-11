package org.example.services;


import org.example.models.Product;
import org.example.models.User;
import org.example.repositories.ProductRepository;

import java.util.*;

public class ProductService {
    private final static Scanner sc = new Scanner(System.in);
    private final static ProductRepository productRepository = new ProductRepository();
    private final static UserService userService = new UserService();

    public void addNewProducts(User inputUser) {
        if(userService.isAdmin(inputUser) || userService.isManager(inputUser)) {
            System.out.print("\nВведите название продукта: ");
            String productName = sc.nextLine();

            System.out.print("\nВведите цену продукта: ");
            try {
                Double productPrice = sc.nextDouble();

                System.out.print("\nВведите кол-во продукта: ");
                int productQuantity = sc.nextInt();

                Product product = new Product(productName, productPrice, productQuantity);

                productRepository.save(product);

                System.out.println("\nПродукт успешно добавлен.\n");
            }catch (InputMismatchException e) {
                System.out.printf("Ошибка ввода: " + e);
            }

        } else {
            System.out.println("\nУ вас нет доступа к этой команде.\n");
        }
    }

    public void viewAllProducts() {
        List<Product> productsList = productRepository.findAll();

        for(Product product : productsList) {
            System.out.println("\nProduct name: " + product.getName() + ", Price: " + product.getPrice() + ", Рейтинг: " + productRepository.calculateRating(product) + "\n");
        }
    }

    public void viewAllProductsRating() {
        Map<Product, Long> productsRating = new HashMap<>();
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            Long rating = productRepository.calculateRating(product);
            productsRating.put(product, rating);
        }

        //Создаем список записей Map для сортировки по рейтингу
        List<Map.Entry<Product, Long>> entryList = new ArrayList<>(productsRating.entrySet());

        //Сортируем записи по убыванию рейтинга
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        //Выводим отсортированный список продуктов
        for (Map.Entry<Product, Long> entry : entryList) {
            Product product = entry.getKey();
            Long rating = entry.getValue();

            System.out.println("\nПродукт: " + product.getName() + ", Рейтинг: " + rating + "\n");
        }
    }
}
