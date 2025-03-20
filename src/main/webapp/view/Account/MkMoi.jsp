<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/19/2024
  Time: 10:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
</head>
<body>
<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow-lg" style="width: 400px; border-radius: 15px;">
        <h2 class="text-center mb-4">Đặt lại mật khẩu</h2>
        <form action="/mkMoi" method="post">

            <input hidden="hidden" type="email" class="form-control" value="${email}" name="email">

            <div class="mb-3" id="otp-section">
                <label for="otpCode" class="form-label">Nhập mã OTP</label>
                <input type="text" class="form-control" id="otpCode" name="inputOtp" maxlength="6" value="${inputOtp}">
                <c:if test="${messageErrorOtp != null}">
                    <span class="text-danger" id="messageErrorOtp">${messageErrorOtp}</span>
                </c:if>

            </div>

            <div class="mb-3">
                <label for="newPassword" class="form-label">Mật khẩu mới</label>
                <input type="password" class="form-control" id="newPassword" name="newPassword" value="${newPassword}">

                <c:if test="${messageErrorNewPassword != null}">
                    <span class="text-danger" id="messageErrorNewPassword">${messageErrorNewPassword}</span>
                </c:if>
            </div>


            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" value="${confirmPassword}">
                <c:if test="${messageErrorConfirmPassword != null}">
                    <span class="text-danger" id="messageErrorConfirmPassword">${messageErrorConfirmPassword}</span>
                </c:if>

            </div>
            <button class="btn btn-primary w-100" type="submit">Đặt lại mật khẩu</button>
        </form>
    </div>
</div>
</body>
</html>
