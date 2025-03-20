package com.example.tranhaidang_pc08348_java_4_asm.Filter;

import com.example.tranhaidang_pc08348_java_4_asm.model.Account;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/Admin/*", "/cart", "/favorite", "/product/*", "/category/*"})
public class AuthFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Account account = (Account) request.getSession().getAttribute("account");

        String urString = request.getRequestURI();

        request.getSession().setAttribute("uriString", urString);

        if (account == null) {
            request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
            return;
        }

        if ((urString.contains("Admin") || urString.contains("product") || urString.contains("category")) && !account.isAdmin()) {
            request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
            return;
        }


        chain.doFilter(request, response);
    }
}
