<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 7:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Detail</title>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>

    <link rel="stylesheet" href="/css/style.css">

    <!-- Bootstrap CSS v5.2.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>
        .review-card {
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 16px;
            margin-bottom: 16px;
        }

        .review-card img {
            max-width: 100px;
            margin-right: 16px;
        }

        .review-header {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
        }

        .review-header img {
            border-radius: 50%;
            width: 40px;
            height: 40px;
            margin-right: 8px;
        }

        .review-header .name {
            font-weight: bold;
        }

        .card-img {
            width: 100%;
            max-width: 400px;
            height: auto;
            object-fit: cover;
            display: block;
            margin: 0 auto;
        }
    </style>

</head>
<body>
<jsp:include page="header-footer/header.jsp"/>

<main>
    <div class="container my-2">
        <ul class="nav p-1">
            <li class="nav-item">
                <a class="nav-link" href="/home" aria-current="page">Trang Chủ</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" aria-current="page">/</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">${product.name}</a>
            </li>
        </ul>
    </div>

    <section>
        <div class="container">
            <div class="row">

                <div class="col-xl-6">
                    <img src="images/${product.image}"
                         class="card-img" alt="...">
                </div>

                <div class="col-xl-6">
                    <div class="card1 p-4 bg-light">
                        <div class="product-title">${product.name}</div>
                        <div class="product-info mt-2">
                            Tình trạng: Còn hàng
                            &nbsp;&nbsp;|&nbsp;&nbsp;
                            Loại sản phẩm: ${product.category.name}
                        </div>
                        <div class="product-price mt-3"><fmt:formatNumber>${product.price}</fmt:formatNumber></div>
                        <hr>
                        <div class="mt-2">
                            <div class="d-flex align-items-center">
                                <i class="fas fa-shield-alt text-primary me-2"></i>
                                <span>Hoàn tiền 100% nếu hàng không chính hãng</span>
                            </div>
                        </div>

                        <div class="mt-4">
                            <div class="quantity-control">
                                <span>Số lượng: &nbsp; </span>
                                <button type="button" class="btn btn-light" onclick="decreaseValue()">-</button>
                                <input type="text" id="quantity" value="1" readonly>
                                <button type="button" class="btn btn-light" onclick="increaseValue()">+</button>
                            </div>
                        </div>

                        <div class="d-flex mt-3">
                            <a onclick="addCart(${account != null ? account.id : 'null'}, ${product.id})" class="btn btn-primary btn-custum">Thêm vào giỏ hàng</a>
                        </div>
                        <hr>

                        <div class="row mt-4">
                            <div class="col-xl-6">
                                <div class="service-box mb-2">
                                    <i class="fas fa-box-open"></i>
                                    <span>Được mở hộp kiểm tra khi nhận hàng</span>
                                </div>
                                <div class="service-box mb-2">
                                    <i class="fas fa-exchange-alt"></i>
                                    <span>Đổi/Trả hàng trong 07 ngày</span>
                                </div>
                                <div class="service-box mb-2">
                                    <i class="fas fa-shipping-fast"></i>
                                    <span>Giao hàng toàn quốc</span>
                                </div>
                            </div>
                            <div class="col-xl-6">
                                <div class="service-box">
                                    <i class="fas fa-shipping-fast"></i>
                                    <span>Nhận hàng từ 5 - 7 ngày</span>
                                </div>
                                <div class="service-box mt-2">
                                    <i class="fas fa-shipping-fast"></i>
                                    <span>Miễn phí giao hàng với hoá đơn 300k</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
        </div>
    </section>


    <section>
        <div class="container">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link active" href="#">
                        Bình luận
                    </a>
                </li>
            </ul>

            <c:choose>
                <c:when test="${a != null}">
                    <div class="review-card mt-3">
                        <div class="review-header">
                            <img alt="User avatar" height="40"
                                 src=""
                                 width="40"/>
                            <div class="name">
                                Name
                            </div>
                        </div>
                        <div class="review-body">
                            <p>

                            </p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="text-center mt-4">Không có bình luận nào cho sản phẩm này</p>
                </c:otherwise>
            </c:choose>
        </div>
    </section>

    <section>
        <div class="container mt-5">

            <h2 class="text mb-3 mt-4" style="font-family: Quicksand, sans-serif;">
                Sản phẩm liên quan
            </h2>

            <div class="row">
                <c:forEach var="item" items="${Related}">
                    <c:if test="${item.id != product.id}">
                        <div class="col-xl-3">
                            <div class="card mt-3 border-0" style="width: 320px;">
                                <a href="/detail?id=<c:out value='${item.id}' />&categoryId=<c:out value='${item.category.id}' />">
                                    <img src="images/${item.image}" class="card-img-top" alt="...">
                                </a>
                                <div class="card-body">
                                    <h4 class="card-title">${item.category.name}</h4>
                                    <p class="card-text" style="font-size: 19px;">${item.name}</p>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <span class="h5 mb-0"><fmt:formatNumber>${item.price}</fmt:formatNumber></span>
                                    </div>
                                    <input type="hidden" id="quantity" value="1" readonly>

                                    <div class="d-flex justify-content-between mt-3">
                                        <div>
                                            <a onclick="addCart(${account != null ? account.id : "null"}, ${item.id})" class="btn btn-primary btn-custum">Thêm vào giỏ hàng</a>
                                        </div>

                                        <div class="mt-1">
                                            <i onclick="addFavorite(${account != null ? account.id : "null"}, ${item.id}, 'heart-${item.id}')"
                                               id="heart-${item.id}" class="btn bi bi-heart fs-2"></i>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>

                <nav aria-label="Page navigation example ">
                    <ul class="pagination justify-content-center mt-4">
                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </ul>
                </nav>

            </div>

        </div>
    </section>

</main>

<jsp:include page="header-footer/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</body>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>


<script>

    function increaseValue() {
        let quantityInput = document.getElementById("quantity");
        let currentValue = parseInt(quantityInput.value);
        quantityInput.value = currentValue + 1;
    }

    function decreaseValue() {
        let quantityInput = document.getElementById("quantity");
        let currentValue = parseInt(quantityInput.value);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    }

</script>

<script>

</script>

</html>
