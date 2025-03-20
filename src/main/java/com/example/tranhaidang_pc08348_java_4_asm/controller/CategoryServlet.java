package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.CategoryDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;


import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig()
@WebServlet({"/category", "/category/list", "/category/insert", "/category/update","/category/edit","/category/delete", "/category/new"})
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/category/new":
                    this.showNewForm(request, response);
                    break;
                case "/category/edit":
                    this.showNewFormUpdate(request, response);
                    break;
                default:
                    this.ListCategory(request, response);
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
                case "/category/insert":
                    this.insertCate(request, response);
                    break;
                case "/category/update":
                    this.updateCate(request, response);
                    break;
                case "/category/delete":
                    this.DeleteCate(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    CategoryDAO categoryDAO = new CategoryDAO();

    public void ListCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
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
        List<Category> categories = categoryDAO.finPage(pageNumber, pageSize, name);
        int totalRecords = categoryDAO.getTotalRecordsForSearch(name);
        int totalPages = (totalRecords + pageSize - 1) / pageSize;
        request.setAttribute("listCategory", categories);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", pageNumber);

        request.setAttribute("view","/view/admin/Category/listCategory.jsp" );

        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

    public void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        request.setAttribute("view", "/view/admin/Category/fromCate.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }


    public void insertCate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String name = request.getParameter("typeName");
        Category category = new Category();

        Part part = request.getPart("image");
        String fileName = part.getSubmittedFileName();
        String filePath = "/images/" + fileName;
        String realPath = request.getServletContext().getRealPath(filePath);
        if (!Files.exists(Path.of(realPath))) {
            Files.createDirectories(Path.of(realPath));
        }

        part.write(realPath);
        category.setImage(fileName);

        category.setName(name);
        categoryDAO.insertCategory(category);
        response.sendRedirect("/category");


    }

    public void showNewFormUpdate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {

        int id = Integer.parseInt(request.getParameter("id"));

        Category category;

        if (id > 0){
            category = categoryDAO.finById(id);
        }else {
            category = new Category();
        }

        request.setAttribute("category", category);
        request.setAttribute("view", "/view/admin/Category/fromCate.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }



    public void updateCate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Category catagory = new Category();

        int id = Integer.parseInt(request.getParameter("id"));
        String cate = request.getParameter("typeName");


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
            catagory.setImage(fileName);
        } else {
            catagory.setImage(currentImage);
        }


        catagory.setId(id);
        catagory.setName(cate);
        categoryDAO.updateCategory(catagory);
        response.sendRedirect("/category");
    }

    public void DeleteCate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.deleteCategory(id);
        response.sendRedirect("/category");
    }

}
