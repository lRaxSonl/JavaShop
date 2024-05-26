package org.example.javashop.services;

import org.example.javashop.models.Product;
import org.example.javashop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static final ProductRepository productRepository = new ProductRepository();

    public List<String> getAllProductNames() {
        List<Product> allProducts = productRepository.findAll();
        List<String> pName = new ArrayList<>();

        for (Product product : allProducts) {
            pName.add(product.getName());
        }
        return pName;
    }
}
