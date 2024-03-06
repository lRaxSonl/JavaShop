package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * @author Nikita Ivkin
 * @since 07.03.2024
 * @class Singleton
 * @description The class connects to the database
 */
public class Singleton {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    private Singleton() {
    }

    public static EntityManager getConnection() {
        if (em == null) {
            synchronized (Singleton.class) {
                if (em == null) {
                    emf = Persistence.createEntityManagerFactory("javashop");
                    em = emf.createEntityManager();
                }
            }
        }
        return em;
    }
}
