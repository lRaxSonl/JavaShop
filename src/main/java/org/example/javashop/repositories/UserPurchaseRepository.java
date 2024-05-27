package org.example.javashop.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.javashop.Singleton;
import org.example.javashop.models.User;
import org.example.javashop.models.UserPurchase;

import java.util.List;


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

    public List<UserPurchase> findByUser(User user) {
        return em.createQuery("SELECT up FROM UserPurchase up WHERE up.user = :user", UserPurchase.class)
                .setParameter("user", user)
                .getResultList();
    }
}
