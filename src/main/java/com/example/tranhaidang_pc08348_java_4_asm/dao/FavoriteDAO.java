package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.Category;
import com.example.tranhaidang_pc08348_java_4_asm.model.Favorite;
import com.example.tranhaidang_pc08348_java_4_asm.model.Product;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteDAO {

    static  EntityManager entityManager = JpaUtil.getEntityManager();


    public static Favorite findByProductAndAccount(int accountId, int productId) {
        try {
            String jpql = "select f from Favorite f where f.account.id = :accountId and f.product.id = :productId";
            TypedQuery<Favorite> query = entityManager.createQuery(jpql, Favorite.class);
            query.setParameter("accountId", accountId);
            query.setParameter("productId", productId);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }


    public List<Favorite> finByAccount(int accountId) {
        try {
            String jpql = "select f from Favorite f where f.account.id = :accountId";
            TypedQuery<Favorite> query = entityManager.createQuery(jpql, Favorite.class);
            query.setParameter("accountId", accountId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static int create(Favorite favorite) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(favorite);
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
            Favorite favorite = entityManager.find(Favorite.class, id);
            entityManager.remove(favorite);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }


    public Set<Integer> getFavoriteProductIds(int accountId) {
        String jpql = "SELECT f.product.id FROM Favorite f WHERE f.account.id = :accountId";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("accountId", accountId);

        return new HashSet<>(query.getResultList());
    }


}
