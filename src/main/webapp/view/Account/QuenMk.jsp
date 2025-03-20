<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/19/2024
  Time: 10:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
</head>
<body>
<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow-lg" style="width: 400px; border-radius: 15px;">
        <h2 class="text-center mb-4">Quên mật khẩu</h2>
        <form id="forgot-password-form" action="/forgotPass" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Nhập email của bạn</label>
                <input type="email" class="form-control" id="email" name="email" value="${email}">
                <span class="text-danger" name="messageError"> ${message}</span>

            </div>

            <button class="btn btn-primary w-100" id="submit-btn" type="submit">Gửi yêu cầu</button>
            <div class="text-center mt-3">
                <a href="/login">Quay lại Đăng nhập</a>
            </div>
        </form>
    </div>
</div>
</body>


<script>


</script>
</html>
