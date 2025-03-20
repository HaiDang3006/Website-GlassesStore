package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.AccountDAO;
import com.example.tranhaidang_pc08348_java_4_asm.dao.DashBoarDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/Admin", "/Admin/account"})
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getServletPath();
        try {
            switch (action) {
                case "/Admin":
                    this.home(request, response);
                    break;
                case "/Admin/account":
                    this.account(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    AccountDAO accountDAO = new AccountDAO();
    DashBoarDAO dashBoarDAO = new DashBoarDAO();
    public void home(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String tongDoanhThu = dashBoarDAO.tongDoanhThu();
        String doanhThuHomNay = dashBoarDAO.doanhThuHomNay();
        String khachHang = dashBoarDAO.KH();
        List<Object[]> hoaDon = dashBoarDAO.DSHD();

        List<Object[]> DataChart = dashBoarDAO.DataChart();

        List<Object[]> sanPhamBanChay = dashBoarDAO.top3Product();
        request.setAttribute("TongDoanhThu", tongDoanhThu);
        request.setAttribute("DoanhThuHomNay", doanhThuHomNay);
        request.setAttribute("HoaDon", hoaDon);
        request.setAttribute("KhachHang", khachHang);
        request.setAttribute("top3", sanPhamBanChay);
        request.setAttribute("DataChart", DataChart);


        request.setAttribute("view", "/view/admin/thongKe.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

    public void account(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Account> accounts = accountDAO.finAll();
        request.setAttribute("listAccount", accounts);

        request.setAttribute("view", "/view/admin/Account/ListAccount.jsp");
        request.getRequestDispatcher("/view/admin/Admin.jsp").forward(request, response);
    }

}
