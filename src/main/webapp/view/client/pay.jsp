<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 7:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />

    <link rel="stylesheet" href="/css/style.css">

    <!-- Bootstrap CSS v5.2.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

</head>
<body>
<jsp:include page="header-footer/header.jsp"/>

<main>
    <div class="container">
        <ul class="nav p-1">
            <li class="nav-item">
                <a class="nav-link" href="/home" aria-current="page">Trang Chủ</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" aria-current="page">/</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/cart">Giỏ hàng</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" aria-current="page">/</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="/pay">Thanh Toán</a>
            </li>
        </ul>
        <hr>
    </div>

    <section>

        <div class="container">
            <div class="row ">
                <div class="col-xl-6">
                    <div class="card">
                        <div class="card-body" style="margin-bottom: 38px">
                            <h5 class="card-title">Thông tin khách hàng</h5>
                            <form action="">

                                <div class="mb-3">
                                    <label for="name" class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" name="name" id="name"
                                           aria-describedby="helpName" placeholder="Vui lòng nhập họ và tên" />
                                </div>

                                <div class="col">
                                    <div class="mb-3">
                                        <label for="phone" class="form-label">Số điện thoại</label>
                                        <input type="text" name="pho" id="phone" class="form-control"
                                               placeholder="Vui lòng nhập số điện thoại"
                                               aria-describedby="helpPhone" />
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" class="form-control" name="email" id="email"
                                           aria-describedby="helpEmail" placeholder="Vui lòng nhập địa chỉ Email" />
                                </div>

                                <h4 class="text mt-4" style="font-family: Quicksand, sans-serif;">
                                    Địa chỉ giao hàng
                                </h4>

                                <div class="row">

                                    <div class="col-md-4 mb-3">
                                        <select class="form-select">
                                            <option selected>Tỉnh / thành</option>
                                            <option value="1">Chọn tỉnh / thành</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <select class="form-select">
                                            <option selected>Quận / huyện</option>
                                            <option value="1">Chọn quận / huyện</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <select class="form-select">
                                            <option selected>Phường / xã</option>
                                            <option value="1">Chọn phường / xã</option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <input type="text" class="form-control" placeholder="Địa chỉ">
                                    </div>
                                </div>

                            </form>
                        </div>
                    </div>


                </div>

                <div class="col-xl-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Đơn hàng</h5>
                            <div class="cart-item">
                                <img alt="Image of Gọng Kính Mát Chrome Hearts Evaculation" height="50"
                                     src="http://product.hstatic.net/1000269337/product/a_img_2916_885b26df163a40d3aeb64769e1576081_grande.jpg"
                                     width="50" />
                                <div class="item-info">
                                    <p class="item-name">
                                        Kính Mát David Beckham 1002/S
                                    </p>
                                    <p class="item-color">
                                        Số lượng: 1
                                    </p>
                                </div>
                                <div class="item-price">
                                    1,500,000
                                </div>
                            </div>

                            <div class="cart-item">
                                <img alt="Image of Gọng Kính Mát Chrome Hearts Evaculation" height="50"
                                     src="http://product.hstatic.net/1000269337/product/a_img_2916_885b26df163a40d3aeb64769e1576081_grande.jpg"
                                     width="50" />
                                <div class="item-info">
                                    <p class="item-name">
                                        Kính Mát David Beckham 1002/S
                                    </p>
                                    <p class="item-color">
                                        Số lượng: 1
                                    </p>
                                </div>
                                <div class="item-price">
                                    1,500,000
                                </div>
                            </div>

                            <div class="cart-item">
                                <img alt="Image of Gọng Kính Mát Chrome Hearts Evaculation" height="50"
                                     src="http://product.hstatic.net/1000269337/product/a_img_2916_885b26df163a40d3aeb64769e1576081_grande.jpg"
                                     width="50" />
                                <div class="item-info">
                                    <p class="item-name">
                                        Kính Mát David Beckham 1002/S
                                    </p>
                                    <p class="item-color">
                                        Số lượng: 1
                                    </p>
                                </div>
                                <div class="item-price">
                                    1,500,000
                                </div>
                            </div>

                            <div class="cart-item">
                                <img alt="Image of Gọng Kính Mát Chrome Hearts Evaculation" height="50"
                                     src="http://product.hstatic.net/1000269337/product/a_img_2916_885b26df163a40d3aeb64769e1576081_grande.jpg"
                                     width="50" />
                                <div class="item-info">
                                    <p class="item-name">
                                        Kính Mát David Beckham 1002/S
                                    </p>
                                    <p class="item-color">
                                        Số lượng: 1
                                    </p>
                                </div>
                                <div class="item-price">
                                    1,800,000
                                </div>
                            </div>
                            <hr>
                            <div class="total-section">
                                <div class="d-flex justify-content-between">
                                        <span class="total-label">
                                            Tạm tính
                                        </span>
                                    <span>
                                            3,910,000
                                        </span>
                                </div>

                                <div class="d-flex justify-content-between mt-3">
                                        <span class="total-label">
                                            Tổng cộng
                                        </span>
                                    <span class="total-amount">
                                            VND 3,910,000
                                        </span>
                                </div>
                            </div>
                            <hr>

                            <div class="form-check mb-3">
                                <input class="form-check-input" type="radio" name="ck" id="ck" / checked>
                                <label class="form-check-label" for="ck"> Thanh toán khi nhận hàng
                                </label>
                            </div>

                            <button type="button" class="btn btn-primary" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                Đặt hàng
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<jsp:include page="header-footer/footer.jsp"/>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Thông báo</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Đặt hàng thành công
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
</html>
