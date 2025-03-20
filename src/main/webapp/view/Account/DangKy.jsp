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
    <title>Đăng Ký</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
</head>
<body>
<div class="d-flex justify-content-center align-items-center vh-100">
    <div class="card " style="border-radius: 15px; width: 40%">
        <div class="card-body p-4 p-md-5">
            <h2 class="text-center mb-4">Đăng ký</h2>
            <form id="dangKy" action="/register" method="post">
                <form>
                    <div class="mb-3">
                        <label for="articleFullName" class="form-label">Họ và tên</label>
                        <input type="text" id="articleFullName" class="form-control form-control-bg" name="fullname"/>
                        <span class="text-danger small" id="errorName"></span>

                    </div>
                    <div class="mb-3">
                        <label for="articleEmail" class="form-label">Email</label>
                        <input type="text" class="form-control form-control-bg" id="articleEmail" name="email">
                        <span class="text-danger small" id="errorEmail">${message}</span>

                    </div>
                    <div class="mb-3">
                        <label for="articlePassword" class="form-label">Mật khẩu</label>
                        <input type="password" id="articlePassword" class="form-control form-control-bg"
                               name="password"/>
                        <span class="text-danger small" id="errorPass1"></span>

                    </div>
                    <div class="mb-3">
                        <label for="confirm" class="form-label">Xác nhận mật khẩu</label>
                        <input type="password" id="confirm" class="form-control form-control-bg" name="password2"/>
                        <span class="text-danger small" id="errorPass2"></span>

                    </div>
                    <button type="submit" class="btn btn-primary w-100">Đăng ký</button>
                </form>

                <hr class="my-4">
                <div class="text-center ">
                    <a href="/home" class="btn btn-outline-primary">Trang chủ</a>
                    <a href="/login" class="btn btn-secondary">Đăng nhập</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    document.getElementById("dangKy").addEventListener("submit", function (event) {
        let isValid = true;

        const fullname = document.getElementById("articleFullName");
        const email = document.getElementById("articleEmail");
        const password = document.getElementById("articlePassword");
        const confirmPassword = document.getElementById("confirm");

        const errorName = document.getElementById("errorName");
        const errorEmail = document.getElementById("errorEmail");
        const errorPass1 = document.getElementById("errorPass1");
        const errorPass2 = document.getElementById("errorPass2");

        errorName.innerText = "";
        errorEmail.innerText = "";
        errorPass1.innerText = "";
        errorPass2.innerText = "";

        const specialCharRegex = /[!@#$%^&*(),.?":{}|<>]/;

        //Fullname
        if (fullname.value.trim() === "") {
            errorName.innerText = "Vui lòng nhập họ và tên";
            isValid = false;
        } else if (fullname.value.length < 3) {
            errorName.innerText = "Họ và tên phải trên 3 ký tự";
            isValid = false;
        }

        if (fullname.value.length > 255){
            errorName.innerText = "Họ và tên phải dưới 255 ký tự";
            isValid = false;
        }

        if(specialCharRegex.test(fullname.value.trim())){
            errorName.innerText = "Họ và tên không chứa ký tự đặt biệt";
            isValid = false;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        //Email
        if (email.value.trim() === "") {
            errorEmail.innerText = "Vui lòng nhập Email";
            isValid = false;
        } else if (!emailRegex.test(email.value.trim())) {
            errorEmail.innerText = "Email không hợp lệ";
            isValid = false;
        }

        //password
        if (password.value.trim() === "") {
            errorPass1.innerText = "Vui lòng nhập mật khẩu";
            isValid = false;
        } else if (password.value.length < 6 || password.value.length > 250) {
            errorPass1.innerText = "Mật khẩu không hợp lệ";
            isValid = false;
        }

        //password2
        if (confirmPassword.value.trim() === "") {
            errorPass2.innerText = "Vui lòng xác nhận mật khẩu";
            isValid = false;
        } else if (confirmPassword.value.trim() !== password.value.trim()) {
            errorPass2.innerText = "Xác nhận mật khẩu không đúng";
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }

    });
</script>

</html>
