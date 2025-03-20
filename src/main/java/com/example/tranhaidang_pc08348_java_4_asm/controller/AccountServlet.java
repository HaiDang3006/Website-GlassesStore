package com.example.tranhaidang_pc08348_java_4_asm.controller;

import com.example.tranhaidang_pc08348_java_4_asm.dao.AccountDAO;
import com.example.tranhaidang_pc08348_java_4_asm.model.Account;
import com.example.tranhaidang_pc08348_java_4_asm.utils.EmailUtil;
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
import java.util.Random;

@MultipartConfig()
@WebServlet({"/login", "/register", "/logout", "/forgotPass", "/updateInfo", "/changePass", "/mkMoi", "/update/acc"})
public class AccountServlet extends HttpServlet {

    private static final String OTP_SESSION_KEY = "otp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/login":
                this.getLogin(request, response);
                break;
            case "/register":
                this.getRegister(request, response);
                break;
            case "/forgotPass":
                this.getForgotPass(request, response);
                break;
            case "/logout":
                this.logout(request, response);
                break;
            case "/changePass":
                this.getChangePass(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/login":
                    this.login(request, response);
                    break;
                case "/register":
                    this.register(request, response);
                    break;
                case "/logout":
                    this.logout(request, response);
                    break;
                case "/changePass":
                    this.changPass(request, response);
                    break;
                case "/forgotPass":
                    this.forgotPass(request, response);
                    break;
                case "/updateInfo":
                    this.updateInfo(request, response);
                    break;
                case "/mkMoi":
                    this.mkMoi(request, response);
                    break;
                case "/update/acc":
                    this.updateAcc(request, response);
                    break;
                default:
                    response.sendRedirect("/login");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    AccountDAO accountDAO = new AccountDAO();

    public void getLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account account = accountDAO.findByEmail(email);

        if (!accountDAO.checkEmail(email)) {
            request.setAttribute("message", "* Tài khoản hoặc mật khẩu không hợp lệ");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
            return;
        }

        if (!account.getActivated()) {
            request.setAttribute("message", "Tài khoản đã bị vô hiệu hóa ");
            request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
            return;
        }

        if (account != null && account.getPassword().equals(password)) {
            request.getSession().setAttribute("account", account);
            String urisession = (String) request.getSession().getAttribute("uriString");


            if (urisession != null) {
                response.sendRedirect(urisession);
                return;
            }

            if (urisession == null && !account.isAdmin()) {
                response.sendRedirect("/home");
                return;
            }

            if (account.isAdmin()) {
                response.sendRedirect("/Admin");
                return;
            }

        } else {
            request.setAttribute("message", "* Tài khoản hoặc mật khẩu không hợp lệ");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/view/Account/DangNhap.jsp").forward(request, response);
        }

    }

    public void getRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/Account/DangKy.jsp").forward(request, response);
    }

    public void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        Account account = new Account();
        try {
            BeanUtils.populate(account, request.getParameterMap());
            account.setAdmin(false);
            account.setActivated(true);

            if (accountDAO.checkEmail(account.getEmail())) {
                request.setAttribute("message", "Email đã tồn tại trong hệ thống.");
                request.getRequestDispatcher("/view/Account/DangKy.jsp").forward(request, response);
                return;
            }

            String fileName = "avatar.jpg";
            String filePath = "/images/" + fileName;
            String realPath = request.getServletContext().getRealPath(filePath);
            if (!Files.exists(Path.of(realPath))) {
                Files.createDirectories(Path.of(realPath));
            }
            account.setPhoto(fileName);
            int rs = accountDAO.register(account);
            if (rs == 1) {
                response.sendRedirect("/login");
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void getChangePass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/Account/DoiMatKhau.jsp").forward(request, response);
    }

    public void changPass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account account = new Account();
        Account acc = (Account) request.getSession().getAttribute("account");
        String moi = request.getParameter("moi");
        String cu = request.getParameter("cu");

        if (acc != null && acc.getPassword().equals(cu)) {
            account.setId(acc.getId());
            account.setEmail(acc.getEmail());
            account.setFullname(acc.getFullname());
            account.setActivated(acc.getActivated());
            account.setAdmin(acc.isAdmin());
            account.setPhoto(acc.getPhoto());

            account.setPassword(moi);
            int change_pass = accountDAO.update(account);
            if (change_pass == 1) {
                request.getSession().invalidate();
                response.sendRedirect("/login");
            }
        } else {
            request.getRequestDispatcher("/view/Account/DoiMatKhau.jsp").forward(request, response);
        }
    }

    public void getForgotPass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/Account/QuenMk.jsp").forward(request, response);
    }

    private String otp;

    private String generateOTP() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    public void forgotPass(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        Account account = accountDAO.findByEmail(email);
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        request.setAttribute("email", email);
        if(email == null ||email.trim().isEmpty()){
            request.setAttribute("message", "Email không được bỏ trống");
            request.getRequestDispatcher("/view/Account/QuenMk.jsp").forward(request, response);
            return;
        }
        if(!email.matches(emailRegex)){
            request.setAttribute("message", "Email không đúng định dạng");
            request.getRequestDispatcher("/view/Account/QuenMk.jsp").forward(request, response);
            return;
        }

        if (account==null) {
            request.setAttribute("message", "Email không tồn tại");
            request.getRequestDispatcher("/view/Account/QuenMk.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
    }

    public void mkMoi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String inputOtp = request.getParameter("inputOtp");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        request.setAttribute("otpCode", inputOtp);
        request.setAttribute("newPassword", newPassword);
        request.setAttribute("confirmPassword", confirmPassword);
        if((inputOtp == null || inputOtp.trim().isEmpty()) && (newPassword == null || newPassword.trim().isEmpty()) && (confirmPassword == null || confirmPassword.trim().isEmpty())){
            request.setAttribute("messageErrorOtp", "Otp không được để trống");
            request.setAttribute("messageErrorNewPassword", "Mật khẩu mới không được để trống");
            request.setAttribute("messageErrorConfirmPassword", "Mật khẩu xác nhận không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if((inputOtp == null || inputOtp.trim().isEmpty()) && (newPassword == null || newPassword.trim().isEmpty())){
            request.setAttribute("messageErrorOtp", "Otp không được để trống");
            request.setAttribute("messageErrorNewPassword", "Mật khẩu mới không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if((newPassword == null || newPassword.trim().isEmpty()) && (confirmPassword == null || confirmPassword.trim().isEmpty())){
            request.setAttribute("messageErrorConfirmPassword", "Mật khẩu xác nhận không được để trống");
            request.setAttribute("messageErrorNewPassword", "Mật khẩu mới không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if((inputOtp == null || inputOtp.trim().isEmpty()) && (confirmPassword == null || confirmPassword.trim().isEmpty())){
            request.setAttribute("messageErrorConfirmPassword", "Mật khẩu xác nhận không được để trống");
            request.setAttribute("messageErrorNewPassword", "Mật khẩu mới không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if (inputOtp == null || inputOtp.trim().isEmpty()) {
            request.setAttribute("messageErrorOtp", "Otp không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if (newPassword == null || newPassword.trim().isEmpty()) {
            request.setAttribute("messageErrorNewPassword", "Mật khẩu mới không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }
        else if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("messageErrorConfirmPassword", "Mật khẩu xác nhận không được để trống");
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
            return;
        }

        String sessionOtp = (String) request.getSession().getAttribute("otp");
        Account account = accountDAO.findByEmail(email);
        if (inputOtp.equals("123456")) {
            account.setPassword(newPassword);
            int result = accountDAO.update(account);
            if (result == 1) {
                request.getSession().invalidate();
                response.sendRedirect("/login");
            }
        } else {
            request.setAttribute("messageErrorOtp", "OTP không đúng!");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/view/Account/MkMoi.jsp").forward(request, response);
        }
    }


    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect("/home");
    }

    public void updateInfo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Account account = new Account();
        Account acc = (Account) request.getSession().getAttribute("account");
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        Part part = request.getPart("photo");
        String currentImage = request.getParameter("currentImage");
        String fileName = part.getSubmittedFileName();

        if (part != null && !fileName.isEmpty()) {
            String filePath = "/images/" + fileName;
            String realPath = request.getServletContext().getRealPath(filePath);

            if (!Files.exists(Path.of(realPath))) {
                Files.createDirectories(Path.of(realPath));
            }
            part.write(realPath);
            account.setPhoto(fileName);
        } else {
            account.setPhoto(currentImage);
        }

        account.setFullname(name);
        account.setEmail(email);
        account.setPassword(acc.getPassword());
        account.setActivated(false);
        account.setAdmin(false);
        account.setId(id);
        int rs = accountDAO.update(account);
        if (rs == 1) {
            response.sendRedirect("/home");
        }
    }


    public void updateAcc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        Account account = accountDAO.findByEmail(email);

        String trangThaiParam = request.getParameter("trangThai");

        boolean trangThai = (trangThaiParam != null && trangThaiParam.equals("on"));

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + trangThai);

        account.setActivated(trangThai);
        int rs = accountDAO.update(account);
        if (rs == 1) {
            response.sendRedirect("/Admin/account");
        }
    }
}
