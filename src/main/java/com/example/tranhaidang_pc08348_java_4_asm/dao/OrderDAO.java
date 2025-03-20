package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.Order;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;

public class OrderDAO {

    static EntityManager entityManager = JpaUtil.getEntityManager();

    public static Order create(Order order) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(order);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return null;
        }
        return order;
    }

}
