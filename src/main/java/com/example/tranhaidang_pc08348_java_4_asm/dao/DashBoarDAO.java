package com.example.tranhaidang_pc08348_java_4_asm.dao;

import com.example.tranhaidang_pc08348_java_4_asm.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DashBoarDAO {

     EntityManager entityManager = JpaUtil.getEntityManager();

    public String tongDoanhThu() {
        String jpql = "SELECT SUM(o.quantity * o.price) FROM OrderDetail o";
        TypedQuery<Number > query = entityManager.createQuery(jpql, Number.class);
        Number  totalRevenue = query.getSingleResult();
        if (totalRevenue != null) {
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return currencyFormat.format(totalRevenue.doubleValue());
        } else {
            return "0 VND";
        }
    }

    public List<Object[]> DSHD() {
        String jpql = "select C.fullname, B.quantity, A.address, A.createdDate, SUM(B.price * B.quantity), B.order.id " +
                "from Order A, OrderDetail B, Account C " +
                "where A.id = B.order.id " +
                "and A.account.id = C.id " +
                "group by C.fullname, B.quantity, A.address, A.createdDate, B.order.id";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }

    public String doanhThuHomNay() {
        String jpql = "SELECT SUM(a.price * a.quantity) FROM OrderDetail a " +
                "JOIN a.order b " +
                "WHERE b.createdDate = CURRENT_DATE";
        TypedQuery<Number> query = entityManager.createQuery(jpql, Number.class);
        Number totalRevenue = query.getSingleResult();
        if (totalRevenue == null) {
            return "0đ";
        }
        return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(totalRevenue);
    }

    public String KH(){
        String jpql = "SELECT COUNT(DISTINCT a.account.id) FROM Order a";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        Long totalRevenue = query.getSingleResult();
        if(totalRevenue == null) {
            return "0";
        }
        return totalRevenue.toString();
    }

    public List<Object[]> top3Product(){
        String jpql = "SELECT b.name, SUM(a.quantity) AS tong1sanpham " +
                "FROM OrderDetail a " +
                "INNER JOIN a.product b " +
                "GROUP BY b.id, b.name " +
                "ORDER BY SUM(a.quantity) DESC";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setMaxResults(3);
        return query.getResultList();
    }

    public List<Object[]> DataChart(){
        String jpql = "SELECT COUNT(DISTINCT a.account.id) AS sokhachtrongthang,\n" +
                "            COUNT(od.quantity) AS sosanpham,\n" +
                "            MONTH(a.createdDate) AS thang,\n" +
                "            YEAR(a.createdDate) AS nam, " +
                "            SUM(od.quantity * od.price)" +
                "        FROM Order a\n" +
                "        INNER JOIN a.orderDetails od\n" +
                "        INNER JOIN od.product p\n" +
                "        GROUP BY MONTH(a.createdDate), YEAR(a.createdDate)\n" ;
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
}
