package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.CartDetail;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CartDetailDAO {


    static EntityManager entityManager = JpaUtil.getEntityManager();

    public static CartDetail findById(int id) {
        return entityManager.find(CartDetail.class, id);
    }


    public static CartDetail findByProductAndAccount(int accountId, int productId) {
        try {
            String jpql = "select f from CartDetail f where f.account.id = :accountId and f.product.id = :productId";
            TypedQuery<CartDetail> query = entityManager.createQuery(jpql, CartDetail.class);
            query.setParameter("accountId", accountId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    public static List<CartDetail> finAllByID(int id) {
        String jpql = "SELECT c FROM CartDetail c where c.account.id = :id";
        TypedQuery<CartDetail> query = entityManager.createQuery(jpql, CartDetail.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public static int create(CartDetail cartDetail) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(cartDetail);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }
    public static int update(CartDetail cartDetail) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(cartDetail);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public static int delete(int id) {
        entityManager.getTransaction().begin();
        try {
            CartDetail cartDetail = entityManager.find(CartDetail.class, id);
            entityManager.remove(cartDetail);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }



//    public List<com.example.tranhaidang_pc08348_java_4_asm.model.Card> finAllByID(int id) {
//        String jpql = "SELECT c FROM Card c where c.account.id = :id";
//        TypedQuery<com.example.tranhaidang_pc08348_java_4_asm.model.Card> query = entityManager.createQuery(jpql, Card.class);
//        query.setParameter("id", id);
//        return query.getResultList();
//    }
//
//    public int TotalProduct() {
//        String jpql = "SELECT COUNT(p) FROM Card p";
//        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
//        Long count = query.getSingleResult();
//        return count.intValue();
//    }

}
