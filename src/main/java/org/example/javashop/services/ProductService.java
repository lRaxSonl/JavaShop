package org.example.javashop.services;

import org.example.javashop.controllers.UIHandler;
import org.example.javashop.models.Product;
import org.example.javashop.models.User;
import org.example.javashop.models.UserPurchase;
import org.example.javashop.repositories.ProductRepository;
import org.example.javashop.repositories.UserPurchaseRepository;
import org.example.javashop.services.UserService;

import java.util.ArrayList;
import java.util.List;


public class ProductService {
    private static final ProductRepository productRepository = new ProductRepository();
    private static final UserService userService = new UserService();
    private static final UserPurchaseRepository userPurchaseRepository = new UserPurchaseRepository();
    private static final UIHandler uiHandler = new UIHandler();

    public List<String> getAllProductNames() {
        List<Product> allProducts = productRepository.findAll();
        List<String> pName = new ArrayList<>();

        for (Product product : allProducts) {
            pName.add(product.getName());
        }
        return pName;
    }

    public boolean purchaseProduct(User user, Product product) {
        if (user.getBalance() >= product.getPrice() && product.getQuantity() > 0) {
            try {
                userService.deductBalance(user, product.getPrice());
                product.setQuantity(product.getQuantity() - 1);
                productRepository.save(product);

                UserPurchase userPurchase = new UserPurchase(user, product);
                userPurchaseRepository.save(userPurchase);
                return true;
            }catch (NumberFormatException e) {
                uiHandler.showAlert("Ошибка.", "Неправильный формат ввода.");
            }
        }
        return false;
    }

    public List<String> getPurchasedProductNames(User user) {
        List<UserPurchase> purchases = userPurchaseRepository.findByUser(user);
        List<String> productNames = new ArrayList<>();
        for (UserPurchase purchase : purchases) {
            productNames.add(purchase.getProduct().getName());
        }
        return productNames;
    }

    public boolean isProductAlreadyExists(String product_name) {
        Product product = productRepository.findByName(product_name);
        return product != null;
    }

    public void addNewProduct(Product product) {
        if(product.getName() != null || product.getPrice() != null || String.valueOf(product.getQuantity()) != null) {
            if(!isProductAlreadyExists(product.getName())) {
                productRepository.save(product);
            }else {
                uiHandler.showAlert("Невозможно добавить продукт.", "Продукт с таким именем уже существует.");
            }
        } else{
            uiHandler.showAlert("Ошибка.", "Неправильный формат ввода, заполните все поля.");
        }
    }
}
