package org.example.javashop.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.javashop.Singleton;
import org.example.javashop.models.UserPurchase;


public class UserPurchaseRepository {
    private final static EntityManager em = Singleton.getConnection();

    public void save(UserPurchase userPurchase) {
        EntityTransaction transaction = em.getTransaction();

        if (userPurchase == null) {
            throw new IllegalArgumentException("UserPurchase cannot be null");
        }

        try {
            transaction.begin();
            em.persist(userPurchase);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
