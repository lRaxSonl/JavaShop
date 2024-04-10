package org.example.javashop.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.javashop.Singleton;
import org.example.javashop.models.Product;

import java.util.List;

public class ProductRepository {
    private final static EntityManager em = Singleton.getConnection();
    public void save(Product product) {
        EntityTransaction transaction = em.getTransaction();

        if (product == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        try {
            transaction.begin();
            em.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Product> findAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product findByName(String name) {
        return em.createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void updateProductQuantity(Product product, int quantity) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            if (product != null) {
                product.setQuantity(quantity);
                em.merge(product);
            }

            transaction.commit();
        }catch (Exception e) {
            if(transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Long calculateRating(Product product) {
        return em.createQuery("SELECT COUNT(up) FROM UserPurchase up WHERE up.product = :product", Long.class)
                .setParameter("product", product)
                .getSingleResult();
    }
}
