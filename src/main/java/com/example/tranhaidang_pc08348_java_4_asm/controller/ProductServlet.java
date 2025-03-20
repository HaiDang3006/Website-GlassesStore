package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.CategoryDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.ProductDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.Category;
import com.example.tranhaidang_pc08348_java_4_asm.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
@MultipartConfig()
@WebServlet({"/product", "/product/list" ,"/product/new", "/product/insert" ,"/product/edit", "/product/update", "/product/delete"})
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/product/new":
                    this.showNewForm(request, response);
                    break;
                case "/product/edit":
                    this.showEditForm(request, response);
                    break;
                default:
                    this.listProduct(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/product/insert":
                    this.insert(request, response);
                    break;
                case "/product/update":
                    this.update(request, response);
                    break;
                case "/product/delete":
                    this.Delete(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    public void listProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");

        if (name == null || name.isEmpty()) {
            name = "";
        }
        int pageSize = 5;
        int pageNumber = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            pageNumber = Integer.parseInt(pageParam);
        }

        List<Product> products = productDAO.finPage(pageNumber, pageSize, name);
        int totalRecords = productDAO.getTotalRecordsForSearch(name);
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        request.setAttribute("listProduct", products);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("name", name);

        request.setAttribute("view","/view/admin/product/listProduct.jsp" );
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Category> categories = categoryDAO.finAll();
        request.setAttribute("categories", categories);
        request.setAttribute("view", "/view/admin/product/formProduct.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

    public void insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Product product = new Product();
        try {
            BeanUtils.populate(product, request.getParameterMap());

            int category_id = Integer.parseInt(request.getParameter("category_id"));
            Category category = categoryDAO.finById(category_id);
            product.setCategory(category);

            Part part = request.getPart("image");
            String fileName = part.getSubmittedFileName();
            String filePath = "/images/" + fileName;
            String realPath = request.getServletContext().getRealPath(filePath);
            if (!Files.exists(Path.of(realPath))) {
                Files.createDirectories(Path.of(realPath));
            }
            part.write(realPath);
            product.setImage(fileName);

            product.setCreated_date(new Date());
            product.setAvailable(true);

            int rs = productDAO.insert(product);
            if (rs == 1) {
                response.sendRedirect("/product");
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        List<Category> categories = categoryDAO.finAll();
        Product product = productDAO.finById(id);
        request.setAttribute("product", product);
        request.setAttribute("categories", categories);

        request.setAttribute("view", "/view/admin/product/editProduct.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Product product = new Product();
        try {
            BeanUtils.populate(product, request.getParameterMap());
            int category_id = Integer.parseInt(request.getParameter("category_id"));
            Category category = categoryDAO.finById(category_id);
            product.setCategory(category);
            Part part = request.getPart("image");
            String currentImage = request.getParameter("currentImage");

            String fileName = part.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                String filePath = "/images/" + fileName;
                String realPath = request.getServletContext().getRealPath(filePath);

                if (!Files.exists(Path.of(realPath))) {
                    Files.createDirectories(Path.of(realPath));
                }
                part.write(realPath);
                product.setImage(fileName);
            } else {
                product.setImage(currentImage);
            }
            product.setCreated_date(new Date());
            product.setAvailable(true);
            int rs = productDAO.update(product);
            if (rs == 1) {
                response.sendRedirect("/product");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void Delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.delete(id);
        response.sendRedirect("/product");
    }

}
