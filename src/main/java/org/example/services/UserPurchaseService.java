package org.example.services;

import org.example.models.Product;
import org.example.models.User;
import org.example.models.UserPurchase;
import org.example.repositories.ProductRepository;
import org.example.repositories.UserPurchaseRepository;
import org.example.repositories.UserRepository;

import java.util.List;
import java.util.Scanner;

public class UserPurchaseService {
    private static final UserPurchaseRepository userPurchaseRepository = new UserPurchaseRepository();
    private static final ProductRepository productRepository = new ProductRepository();
    private static final UserRepository userRepository = new UserRepository();
    private static final ProductService productService = new ProductService();
    private static final Scanner sc = new Scanner(System.in);

    public void buyProduct(User user) {
        productService.viewAllProducts();
        System.out.print("Напишите название товара для покупки: ");
        String productName = sc.nextLine();

        if(isProductNameAlreadyExists(productName)) {
            Product product = productRepository.findByName(productName);

            if(user.getBalance() >= product.getPrice()) {
                if(product.getQuantity() > 0) {
                    double newBalance = user.getBalance() - product.getPrice(); //Новый баланс
                    int newQuantity = product.getQuantity() - 1;

                    userRepository.updateUserBalance(user, newBalance); //Обновляем баланс в базе данных
                    productRepository.updateProductQuantity(product, newQuantity); //Обновляем кол-во товара в базе данных
                    savePurchase(user, product); //Сохраняем покупку

                    System.out.println("Товар успешно куплен.");
                }else {
                    System.out.println("Недостаточно товара на складе");
                    return;
                }

            }else {
                System.out.println("На балансе недостаточно денег.");
            }

        }else {
            System.out.println("\nТакого товара не существует.\n");
        }
    }

    private void savePurchase(User user, Product product) {
        userPurchaseRepository.save(new UserPurchase(user, product));
    }

    private boolean isProductNameAlreadyExists(String name) {
        List<Product> productsList= productRepository.findAll();

        for (Product product : productsList) {
            if(product.getName().equals(name)) {
                return true;
            }
        }return false;
    }
}
