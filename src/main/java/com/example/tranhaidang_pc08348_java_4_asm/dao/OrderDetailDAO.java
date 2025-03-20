package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.OrderDetail;
import com.example.tranhaidang_pc08348_java_4_asm.model.Product;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderDetailDAO {

    static EntityManager entityManager = JpaUtil.getEntityManager();
    public static int create(OrderDetail orderDetail) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(orderDetail);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public static List<OrderDetail> finByOderID(int id) {
        String jpql = "SELECT c FROM OrderDetail c where c.order.id = :id";
        TypedQuery<OrderDetail> query = entityManager.createQuery(jpql, OrderDetail.class);
        query.setParameter("id", id);

        return query.getResultList();
    }

}
