<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <link rel="stylesheet" href="style.css">

    <!-- Bootstrap CSS v5.2.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>

        #image {
            display: none;
        }

    </style>
</head>
<body>
<header>

    <nav class="navbar navbar-expand-lg">
        <div class="container">
            <a class="navbar-brand" href="#">
                <img src="https://gudlogo.com/wp-content/uploads/2021/01/MATTI-LOGO-official-1030x340.png"
                     alt="Logo" height="70">
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">

                <form class="my-lg-0 mx-auto">
                    <input style="width: 400px;" class="form-control" type="text"
                           placeholder="Nhập tên sản phẩm cần tìm..."/>
                </form>

                <ul class="navbar-nav">

                    <li class="nav-item dropdown">

                        <c:choose>

                            <c:when test="${account != null}">
                                <div class="text-end">
                                    <a class="nav-link dropdown-toggle" href="#" id="dropdownId"
                                       data-bs-toggle="dropdown"
                                       aria-haspopup="true" aria-expanded="false"> ${account.fullname} <i
                                            class="bi bi-person"></i>
                                    </a>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <div class="text-end">
                                    <a class="nav-link dropdown-toggle" href="#" id="dropdownId"
                                       data-bs-toggle="dropdown"
                                       aria-haspopup="true" aria-expanded="false">TÀI KHOẢN <i class="bi bi-person"></i>
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>


                        <div class="dropdown-menu" aria-labelledby="dropdownId">

                            <c:if test="${account == null}">
                                <a class="dropdown-item" href="/login"><i class="bi bi-box-arrow-right me-1"></i> ĐĂNG
                                    NHẬP</a>
                            </c:if>

                            <c:if test="${account == null}">
                                <a class="dropdown-item" href="/register"><i class="bi bi-person-plus me-1"></i> ĐĂNG KÝ</a>
                            </c:if>

                            <c:if test="${account == null}">
                                <a class="dropdown-item" href="/forgotPass"><i class="bi bi-key me-1"></i> QUÊN MẬT KHẨU</a>
                            </c:if>

                            <c:if test="${account != null}">
                                <a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#profileModal"> <i
                                        class="bi bi-person-plus"></i> Tài khoản của tôi</a>
                                <a class="dropdown-item" href="/changePass"><i class="bi bi-key me-1 "></i>Đổi mật khẩu</a>
                                <a class="dropdown-item" href="/logout"><i class="bi bi-key me-1"></i> Đăng xuất</a>
                            </c:if>

                            <c:if test="${account != null && account.admin}">
                                <a class="dropdown-item" href="/Admin"><i class="bi bi-house-door me-1"></i> Trang chủ</a>
                            </c:if>

                        </div>

                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page">|</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/favorite"> YÊU THÍCH <i class="bi bi-heart"></i></a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page">|</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="/cart"> GIỎ HÀNG <i class="bi bi-cart3"></i></a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

</header>

<div class="modal fade mt-5" id="profileModal" tabindex="-1" aria-labelledby="profileModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
            <div class="modal-header border-0">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-8 mb-4">
                        <div class="card mb-3">
                            <form action="/updateInfo" method="post" enctype="multipart/form-data">
                                <div class="row g-0">
                                    <div class="col-md-4 text-center bg-light">
                                        <input hidden="hidden" class="form-control" name="id" value="${account.id}">
                                        <img src="/images/${account.photo}"
                                             alt="Avatar" class="img-fluid mt-5 mb-4 rounded-circle"
                                             style="width: 100px;"/>
                                        <div class="">
                                            <label for="image" class="form-label btn btn-outline-secondary">Chọn
                                                ảnh</label>
                                            <input type="file" class="form-control" name="photo" id="image"
                                                   aria-describedby="helpId" placeholder=""/>
                                            <input type="hidden" name="currentImage" value="${account.photo}">
                                        </div>
                                    </div>

                                    <div class="col-md-8">
                                        <div class="card-body p-4">
                                            <h4>Hồ sơ của bạn</h4>
                                            <hr class="mb-4">
                                            <div class="row pt-1">
                                                <div class="col-6 mb-3">
                                                    <h6>Họ và tên</h6>
                                                    <input class="form-control" name="name" value="${account.fullname}">
                                                </div>
                                                <div class="col-6 mb-3">
                                                    <h6>Email</h6>
                                                    <input class="form-control" name="email" value="${account.email}">
                                                </div>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Cập nhật thông tin</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer border-0">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Thoát</button>
            </div>
        </div>
    </div>
</div>


<%--van loc--%>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        let form = document.querySelector("form[action='/updateInfo']");
        let errorMessageDiv = document.getElementById("error-message");

        form.addEventListener("submit", function (event) {
            let nameInput = document.querySelector("input[name='name']");
            let emailInput = document.querySelector("input[name='email']");
            let fileInput = document.querySelector("input[name='photo']");
            let currentImage = document.querySelector("input[name='currentImage']").value;

            // Xóa thông báo lỗi cũ trước khi kiểm tra
            errorMessageDiv.innerText = "";

            // Kiểm tra Họ và tên
            if (nameInput.value.trim() === "") {
                errorMessageDiv.innerText = "Họ và tên không được để trống!";
                event.preventDefault();
                return;
            }
            if (nameInput.value.trim().length < 3) {
                errorMessageDiv.innerText = "Họ và tên phải có ít nhất 3 ký tự!";
                event.preventDefault();
                return;
            }

            // Kiểm tra Email
            let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (emailInput.value.trim() === "") {
                errorMessageDiv.innerText = "Email không được để trống!";
                event.preventDefault();
                return;
            }
            if (!emailPattern.test(emailInput.value)) {
                errorMessageDiv.innerText = "Email không đúng định dạng!";
                event.preventDefault();
                return;
            }

            // Kiểm tra hình ảnh (chỉ bắt lỗi nếu chưa có ảnh cũ)
            if (fileInput.files.length === 0 && !currentImage) {
                errorMessageDiv.innerText = "Vui lòng chọn ảnh đại diện!";
                event.preventDefault();
                return;
            }
        });
    });


</script>


</body>

</html>
