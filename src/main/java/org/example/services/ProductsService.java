package org.example.services;


import org.example.models.Product;
import org.example.models.User;
import org.example.repositories.ProductRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ProductsService {
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

                Product product = new Product(productName, productPrice);

                productRepository.save(product);

                System.out.println("\nПродукт успешно добавлен.\n");
            }catch (InputMismatchException e) {
                System.out.printf("Ошибка ввода: " + e);
            }



        }

    }

}
