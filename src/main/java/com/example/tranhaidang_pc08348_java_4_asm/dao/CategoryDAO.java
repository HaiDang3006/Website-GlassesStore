package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.Category;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class CategoryDAO {

    EntityManager entityManager = JpaUtil.getEntityManager();

    public List<Category> finAll() {
        String jpql = "SELECT c FROM Category c";
        TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
        return query.getResultList();
    }

    public List<Category> finTop3() {
        String jpql = "SELECT c FROM Category c ORDER BY c.id ASC"; 
        TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
        query.setMaxResults(3); 
        return query.getResultList();
    }

    public List<Object[]> findCategoriesWithProductCount() {
        String jpql = "SELECT c.id, c.name, COUNT(p) FROM Category c LEFT JOIN c.products p GROUP BY c.id, c.name";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    public List<Category> finPage(int pageNumber, int pageSize, String name) {
        String jpql = "SELECT c FROM Category c WHERE c.name LIKE :search";
        TypedQuery<Category> query = entityManager.createQuery(jpql, Category.class);
        query.setParameter("search", "%" + name + "%");
        int firstResult = (pageNumber - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int getTotalRecordsForSearch(String name) {
        String jpql = "SELECT COUNT(c) FROM Category c WHERE c.name LIKE :search";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("search", "%" + name + "%");
        Long count = query.getSingleResult();
        return count.intValue(); // Trả về tổng số bản ghi
    }

    public Category finById(int id) {
        return entityManager.find(Category.class, id);
    }

    public void insertCategory(Category category) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.persist(category);
        entityManager.getTransaction().commit();
    }

    public void updateCategory(Category category) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.merge(category);
        entityManager.getTransaction().commit();
    }

    public void deleteCategory(int id) throws SQLException {
        entityManager.getTransaction().begin();
        entityManager.remove(finById(id));
        entityManager.getTransaction().commit();
    }

    public boolean checkNameCategory(String name) {
        String jpql = "SELECT COUNT(a) FROM Category a WHERE a.name = :name";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("name", name)
                .getSingleResult();
        return count > 0;
    }


}
