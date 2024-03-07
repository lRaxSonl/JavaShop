package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.Singleton;
import org.example.models.Product;

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
}
