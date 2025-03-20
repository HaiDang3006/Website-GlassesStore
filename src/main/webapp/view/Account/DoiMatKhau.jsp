<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đổi mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
</head>
<body>

<div class="d-flex justify-content-center align-items-center vh-100">

    <div class="card p-4 shadow-lg" style="width: 445px;">
        <h2 class="text-center mb-4">Đổi mật khẩu</h2>

        <form id="login-form" action="/changePass" method="post" onsubmit="return validateForm()">
            <div class="mb-3">
                <label for="cu" class="form-label">Nhập mật khẩu cũ</label>
                <input type="password" class="form-control" id="cu" name="cu">
            </div>
            <div class="mb-3">
                <label for="moi" class="form-label">Nhập mật khẩu mới</label>
                <input type="password" class="form-control" id="moi" name="moi">
            </div>
            <div class="mb-3">
                <label for="moi2" class="form-label">Xác nhận mật khẩu mới</label>
                <input type="password" class="form-control" id="moi2" name="moi">
            </div>

            <!-- Hiển thị lỗi -->
            <div id="error-message" class="text-danger mb-3"></div>

            <button class="btn btn-primary w-100" type="submit">Đổi mật khẩu</button>
        </form>
    </div>
</div>

</body>
<script>
    function validateForm() {
        const oldPass = document.getElementById("cu").value.trim();
        const newPass = document.getElementById("moi").value.trim();
        const confirmPass = document.getElementById("moi2").value.trim();
        const errorMessage = document.getElementById("error-message");

        errorMessage.textContent = "";

        if (oldPass === "") {
            errorMessage.textContent = "Vui lòng nhập mật khẩu cũ.";
            return false;
        }

        if (newPass === "") {
            errorMessage.textContent = "Vui lòng nhập mật khẩu mới.";
            return false;
        }

        if (confirmPass === "") {
            errorMessage.textContent = "Vui lòng xác nhận mật khẩu mới.";
            return false;
        }

        if (newPass !== confirmPass) {
            errorMessage.textContent = "Mật khẩu mới và xác nhận không khớp.";
            return false;
        }

        if (newPass.length < 6) {
            errorMessage.textContent = "Mật khẩu mới phải có ít nhất 6 ký tự.";
            return false;
        }

        return true;
    }
</script>
</html>
