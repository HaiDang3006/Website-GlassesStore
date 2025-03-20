package com.example.tranhaidang_pc08348_java_4_asm.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    static EntityManagerFactory factory;

    static {
        factory = Persistence.createEntityManagerFactory("JA");
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

}
