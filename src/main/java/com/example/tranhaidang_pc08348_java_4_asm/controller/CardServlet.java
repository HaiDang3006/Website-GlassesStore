package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.CartDetailDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.OrderDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.OrderDetailDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.ProductDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet({"/cart" ,"/cart/order", "/cart/delete",})
public class CardServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/cart/delete":
                    this.deleteCart(request, response);
                    break;
                default:
                    this.ListCard(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/cart/order":
                    this.cartOder(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ProductDAO productDAO = new ProductDAO();

    public void ListCard(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {

        Account account = (Account) request.getSession().getAttribute("account");


        List<CartDetail> cartDetails =  CartDetailDAO.finAllByID(account.getId());


        List<Object[]> finAllProductByHoaDon = productDAO.finAllProductByHoaDon(account.getId());

        Map<Integer, List<Object[]>> hoaDonChiTietMap = new HashMap<>();

        for(Object[] hoaDon : finAllProductByHoaDon){
            int orderId = (int) hoaDon[0];
            List<Object[]> chiTietHoaDon = productDAO.findAllHoaDonChiTiet(orderId);
            hoaDonChiTietMap.put(orderId, chiTietHoaDon);
        }


        request.setAttribute("listCart", cartDetails);

        request.setAttribute("listProduct", productDAO.findMostLikedProducts());

        request.setAttribute("finAllProductByHoaDon", finAllProductByHoaDon);

        request.setAttribute("hoaDonChiTietMap", hoaDonChiTietMap);

        request.getRequestDispatcher("/view/client/cart.jsp").forward(request, response);
    }

    public void cartOder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Account account = (Account) request.getSession().getAttribute("account");

        String[] cartDetailIds = request.getParameterValues("cartDetailIds");

        String address = request.getParameter("address");

        Order order = new Order(0, new Date(), 1, address, account, null);

        order = OrderDAO.create(order);

        for (String id : cartDetailIds) {

            int cartDetailId = Integer.parseInt(id);

            CartDetail cartDetail = CartDetailDAO.findById(cartDetailId);

            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);

            orderDetail.setProduct(cartDetail.getProduct());

            orderDetail.setPrice(cartDetail.getProduct().getPrice());

            orderDetail.setQuantity(cartDetail.getQuantity());

            int rs = OrderDetailDAO.create(orderDetail);

            if (rs == 1) {
                CartDetailDAO.delete(cartDetailId);
            }

        }

        response.sendRedirect("/cart");
    }

    public void deleteCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CartDetailDAO.delete(id);
        response.sendRedirect("/cart");
    }

}
