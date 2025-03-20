package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.CategoryDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.FavoriteDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.ProductDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.Account;
import com.example.tranhaidang_pc08348_java_4_asm.model.Favorite;
import com.example.tranhaidang_pc08348_java_4_asm.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet({"/home", "/categoryDetail", "/detail", "/pay", "/favorite"})
public class homeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/home":
                    this.home1(request, response);
                    break;
                case "/categoryDetail":
                    this.categoryDetail(request, response);
                    break;
                case "/detail":
                    this.detail(request, response);
                    break;
                case "/pay":
                    this.pay(request, response);
                    break;
                case "/favorite":
                    this.favorite(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    FavoriteDAO favoriteDAO = new FavoriteDAO();


    public void home1(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        List<Product> products = productDAO.finAll();


        Set<Integer> favoriteProductIds = new HashSet<>();

        Account account = (Account) request.getSession().getAttribute("account");

        if (account != null) {
            favoriteProductIds = favoriteDAO.getFavoriteProductIds(account.getId());
        }

        request.setAttribute("products", products);
        request.setAttribute("favoriteProductIds", favoriteProductIds);
        request.getRequestDispatcher("/view/client/home.jsp").forward(request, response);

    }



    public void categoryDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        List<Object[]> categories = categoryDAO.findCategoriesWithProductCount();
        request.setAttribute("listCategory", categories); 
        
        request.setAttribute("category", categoryDAO.finById(id));
        request.setAttribute("listProduct", productDAO.finProductByCategory(id));
        request.getRequestDispatcher("/view/client/listCate.jsp").forward(request, response);
    }

    public void detail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        int idCate = Integer.parseInt(request.getParameter("categoryId"));
        request.setAttribute("Related", productDAO.finProductByCategory(idCate));
        request.setAttribute("product", productDAO.finById(id));
        request.getRequestDispatcher("/view/client/detail.jsp").forward(request, response);
    }


    public void pay(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        request.getRequestDispatcher("/view/client/pay.jsp").forward(request, response);
    }

    public void favorite(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Account account = (Account) request.getSession().getAttribute("account");

        request.setAttribute("listProduct", favoriteDAO.finByAccount(account.getId()));
        List<Favorite> ok = favoriteDAO.finByAccount(account.getId());
        request.getRequestDispatcher("/view/client/yeuThich.jsp").forward(request, response);
    }

}
