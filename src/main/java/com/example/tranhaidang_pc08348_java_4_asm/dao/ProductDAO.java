package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.model.Account;
import com.example.tranhaidang_pc08348_java_4_asm.model.Category;
import com.example.tranhaidang_pc08348_java_4_asm.model.Product;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class ProductDAO {

    static EntityManager entityManager = JpaUtil.getEntityManager();

    public static Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> finAll() {
        String jpql = "SELECT c FROM Product c ORDER BY c.id DESC";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        return query.getResultList();
    }


    public List<Product> findMostLikedProducts() {
        String jpql = "SELECT f.product " + "FROM Favorite f " +
                "GROUP BY f.product " +
                "ORDER BY COUNT(f.id) DESC";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setMaxResults(4);
        return query.getResultList();
    }

    public List<Product> finProductByCategory(int category) {
        String jpql = "SELECT c FROM Product c where c.category.id = :id";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("id", category);
        return query.getResultList();
    }

    public static List<Object[]> findAllHoaDonByAccountId(int accountId) {
        String jpql = "SELECT b.id, c.fullname, d.name, d.image, d.price, " +
                "b.quantity, " +
                "(b.quantity * d.price) AS totalPrice, " +
                "a.createdDate " +
                "FROM Order a " +
                "INNER JOIN a.orderDetails b " +
                "INNER JOIN a.account c " +
                "INNER JOIN b.product d " +
                "WHERE c.id = :accountId";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("accountId", accountId); // Truyền tham số
        return query.getResultList();
    }

    public static List<Object[]> finAllHoaDon(int accountId) {
        String jpql = "SELECT a.id, a.createdDate " +
                "FROM Order a " +
                "WHERE a.account.id = :accountId " +
                "ORDER BY a.id DESC";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("accountId", accountId); // Truyền tham số
        return query.getResultList();
    }

    public static List<Object[]> finAllProductByHoaDon(int accountId) {
        String jpql = "SELECT a.id, a.createdDate " +
                "FROM Order a " +
                "WHERE a.account.id = :accountId " +
                "ORDER BY a.id DESC ";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("accountId", accountId); // Truyền tham số
        return query.getResultList();
    }

    public static List<Object[]> findAllHoaDonChiTiet(int orderId) {
        String jpql = "SELECT b.id, c.fullname, d.name, d.image, d.price, a.address ," +
                "b.quantity, " +
                "(b.quantity * d.price) AS totalPrice, " + // Tổng giá cho từng sản phẩm
                "a.createdDate " +
                "FROM Order a " +
                "INNER JOIN a.orderDetails b " +
                "INNER JOIN a.account c " +
                "INNER JOIN b.product d " +
                "WHERE a.id = :orderId " +
                "ORDER BY a.id DESC"; // Sắp xếp theo ngày mới nhất
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("orderId", orderId); // Truyền tham số
        return query.getResultList();
    }


    public List<Product> finPage(int pageNumber, int pageSize, String name) {
        String jpql = "SELECT p FROM Product p JOIN p.category c WHERE p.name LIKE :search OR c.name LIKE :search";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("search", "%" + name + "%");
        int firstResult = (pageNumber - 1) * pageSize;
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public int getTotalRecordsForSearch(String name) {
        String jpql = "SELECT COUNT(p) FROM Product p JOIN p.category c WHERE p.name LIKE :search OR c.name LIKE :search";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("search", "%" + name + "%");
        Long count = query.getSingleResult();
        return count.intValue();
    }

    public Product finById(int id) {
        return entityManager.find(Product.class, id);
    }

    public int insert(Product product) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public int update(Product product) {
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return 0;
        }
        return 1;
    }

    public static String delete(int id) throws SQLException {
        entityManager.getTransaction().begin();
        try {
            Product product = entityManager.find(Product.class, id);
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            // TODO: handle exception
            entityManager.getTransaction().rollback();
            return "Không thể xóa sản phẩm";
        }
        return null;
    }
}
