package com.example.tranhaidang_pc08348_java_4_asm.dao;
import com.example.tranhaidang_pc08348_java_4_asm.model.Account;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
public class AccountDAO {

    static EntityManager entityManager = JpaUtil.getEntityManager();
    public static Account findById(int id) {
        return entityManager.find(Account.class, id);
    }

    public List<Account> finAll() {
        String jpql = "SELECT c FROM Account c";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        return query.getResultList();
    }

    public Account findByEmail(String email) {
        String jpql = "SELECT a FROM Account a WHERE a.email = :email";
        TypedQuery<Account> query = entityManager.createQuery(jpql, Account.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public int register(Account account) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(account);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public int update(Account account) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(account);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public boolean checkEmail(String email) {
        String jpql = "SELECT COUNT(a) FROM Account a WHERE a.email = :email";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

}

