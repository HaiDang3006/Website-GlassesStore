<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/17/2024
  Time: 10:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
    <style>
        .ku {
            text-decoration: none;
        }
        .a:hover .ku {
            text-decoration: #3A00C0;
        }
    </style>
</head>
<body>

<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow-lg" style="width: 445px;">
        <h2 class="text-center mb-4">Đăng nhập</h2>
        <form id="login-form" action="/login" method="post">
            <div class="mb-3">
                <input type="text" value="${email}" class="form-control" id="email" name="email" >
                <label for="email" class="form-label">Email</label>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" id="password" name="password" >
                <label for="password" class="form-label">Mật khẩu</label>
            </div>
            <span class="text-danger"> ${message}</span>

            <button id="abcd" class="btn btn-primary w-100" type="submit">Đăng nhập</button>

            <div class="a text-center mt-3">
                <a class="ku" href="/forgotPass">Quên mật khẩu</a>
            </div>
            <span class="d-flex align-items-center justify-content-center mb-1 mt-2"
                  style="font-family: Quicksand, sans-serif; font-size: 12px">
                <hr class="flex-grow-1 me-3"/>
                Hoặc
                <hr class="flex-grow-1 ms-3"/>
            </span>
            <div class="text-center mt-3">
                <a href="/home" type="button" class="btn btn-outline-info">Trang chủ</a>
                <a class="btn btn-success" href="/register">Tạo tài khoản</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
