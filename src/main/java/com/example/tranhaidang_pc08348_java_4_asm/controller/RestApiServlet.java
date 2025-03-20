package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.*;
import com.example.tranhaidang_pc08348_java_4_asm.model.*;
import com.example.tranhaidang_pc08348_java_4_asm.utils.JsonIO;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

@WebServlet({"/api/favorites", "/api/favorites/add", "/api/cart/delete", "/api/cart/add", "/api/cart/update", "/api/getOderDetail", "/category/check", "/api/deleteProduct"})
public class RestApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/api/getOderDetail":
                    this.getOderDetail(request, response);
                    break;
                case "/category/check":
                    this.checkNameCate(request, response);
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
                case "/api/favorites/add":
                    this.Favorite(request, response);
                    break;
                case "/api/cart/add":
                    this.AddCart(request, response);
                    break;
                case "/api/cart/update":
                    this.UpdateCard(request, response);
                    break;
                case "/api/getOderDetail":
                    this.getOderDetail(request, response);
                    break;
                case "/api/deleteProduct":
                    this.deleteProduct(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void Favorite(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String dataString = JsonIO.read(request);
        ObjectMapper objectMapper = new ObjectMapper();


        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {
        };


        Map<String, Integer> map = objectMapper.readValue(dataString, typeReference);
        Account account = AccountDAO.findById(map.get("account_id"));
        Product product = ProductDAO.findById(map.get("product_id"));

        Favorite favorite = FavoriteDAO.findByProductAndAccount(account.getId(), product.getId());

        String rs;

        if (favorite != null) {
            FavoriteDAO.delete(favorite.getId());
            rs = "{\"message\":\"success\",\"type\":0}";
        } else {
            Favorite favorite1 = new Favorite();
            favorite1.setAccount(account);
            favorite1.setProduct(product);
            favorite1.setCreateDate(new Date());
            FavoriteDAO.create(favorite1);
            rs = "{\"message\":\"success\",\"type\":1}";
        }

        JsonIO.write(response, rs);
    }

    public void AddCart(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String dataString = JsonIO.read(request);
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {
        };
        Map<String, Integer> map = objectMapper.readValue(dataString, typeReference);
        Account account = AccountDAO.findById(map.get("account_id"));
        Product product = ProductDAO.findById(map.get("product_id"));

        Integer quantity = map.containsKey("quantity") ? (Integer) map.get("quantity") : 1;

        CartDetail cartDetail = CartDetailDAO.findByProductAndAccount(account.getId(), product.getId());
        if (cartDetail != null) {
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            CartDetailDAO.update(cartDetail);
        } else {
            cartDetail = new CartDetail(0, quantity ,account, product);
            CartDetailDAO.create(cartDetail);
        }

        JsonIO.write(response, "{\"message\":\"success\",\"type\":1}");
    }

    public void UpdateCard(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String dataString = JsonIO.read(request);
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {
        };
        Map<String, Integer> map = objectMapper.readValue(dataString, typeReference);
        Account account = AccountDAO.findById(map.get("account_id"));
        Product product = ProductDAO.findById(map.get("product_id"));
        int quantity = map.get("quantity");
        CartDetail cartDetail = CartDetailDAO.findByProductAndAccount(account.getId(), product.getId());
        if (cartDetail != null) {
            cartDetail.setQuantity(quantity);
            CartDetailDAO.update(cartDetail);
        }

        JsonIO.write(response, "{\"message\":\"success\",\"type\":1}");
    }


    public void getOderDetail(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String dataString = JsonIO.read(request);

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {};

        Map<String, Integer> map = objectMapper.readValue(dataString, typeReference);

        int orderId = map.get("oderID");

        List<OrderDetail> orderDetails = OrderDetailDAO.finByOderID(orderId);

        List<Map<String, Object>> jsonResponse = new ArrayList<>();

        for (OrderDetail detail : orderDetails) {
            Map<String, Object> detailMap = new HashMap<>();
            detailMap.put("productName", detail.getProduct().getName());
            detailMap.put("quantity", detail.getQuantity());
            detailMap.put("price", detail.getPrice());
            detailMap.put("total", detail.getQuantity() * detail.getPrice());
            jsonResponse.add(detailMap);
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), jsonResponse);
    }


    CategoryDAO CategoryDAO = new CategoryDAO();
    public void checkNameCate(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ request
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> requestData = objectMapper.readValue(request.getReader(), Map.class);

        // Lấy tên Category từ request
        String nameCate = requestData.get("nameCate");

        // Kiểm tra xem tên đã tồn tại hay chưa
        boolean exists = CategoryDAO.checkNameCategory(nameCate);

        // Trả về kết quả JSON
        Map<String, Boolean> jsonResponse = new HashMap<>();
        jsonResponse.put("exists", exists);

        objectMapper.writeValue(response.getWriter(), jsonResponse);

    }


    public void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

        String dataString = JsonIO.read(request);

        ObjectMapper objectMapper = new ObjectMapper();

        TypeReference<Map<String, Integer>> typeReference = new TypeReference<Map<String, Integer>>() {};

        Map<String, Integer> map = objectMapper.readValue(dataString, typeReference);

        int productId = map.get("productId");

        String deleteProductMess = deleteProduct(productId);

        String jsonResponse;

        if (deleteProductMess == null){
            jsonResponse = "Xóa sản phẩm thành công";
        } else {
            jsonResponse = deleteProductMess;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), jsonResponse);
    }

    public String deleteProduct(int productId) throws SQLException {
        String delete = ProductDAO.delete(productId);
        return delete;
    }

}
