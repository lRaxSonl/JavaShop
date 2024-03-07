package org.example.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.Singleton;
import org.example.models.User;
import org.example.models.UserRole;

import java.util.List;

/**
 * @author Nikita Ivkin
 * @since 07.03.2024
 * @class UserRepository
 * @description
 * The UserRepository class represents a repository for User entities.
 *
 * This class provides methods for finding all users, finding a user by username, and saving a new user.
 * It uses the Singleton class to connect to the database.
 */
public class UserRepository {

    private final static EntityManager em = Singleton.getConnection();

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public User findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public void save(User user) {
        EntityTransaction transaction = em.getTransaction();

        if (user == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



}
