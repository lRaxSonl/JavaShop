package org.example.repositories;

import jakarta.persistence.EntityManager;
import org.example.Singleton;

public class UserPurchaseRepository {
    private final static EntityManager em = Singleton.getConnection();

}
