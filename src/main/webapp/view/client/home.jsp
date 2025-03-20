<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 6:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>

<html lang="en">
<head>
    <title>Trang chủ</title>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <link rel="stylesheet" href="/css/style.css">

    <!-- Bootstrap CSS v5.2.1 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</head>

<body>

<jsp:include page="header-footer/header.jsp"/>

<main>
    <section class="carousel">
        <div id="carousel" class="carousel slide">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="https://theme.hstatic.net/1000269337/1000981122/14/slideShow_f1_1.png?v=293"
                         class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src=https://theme.hstatic.net/1000269337/1000981122/14/slideShow_f1_3.png?v=293"
                         class="d-block w-100" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="https://theme.hstatic.net/1000269337/1000981122/14/slideShow_f1_1.png?v=299"
                         class="d-block w-100" alt="...">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </section>

    <section class="p-4 mt-3">
        <div class="container">

            <h2 class="text-center" style="font-family: Quicksand, sans-serif;">
                Danh Mục Nổi Bật
            </h2>

            <div class="row justify-content-center">
                <c:forEach var="item" items="${category}">
                    <div class="col-auto">
                        <div class="card-custom">
                            <a href="/categoryDetail?id=<c:out value='${item.id}' />" style="text-decoration: none;">
                                <img src="${item.image}"
                                     alt="">
                                <div class="title">${item.name}</div>
                                <div class="subtitle">Xem Ngay</div>
                            </a>
                        </div>
                    </div>
                </c:forEach>


            </div>
        </div>
    </section>

    <section class="bg-light p-4">
        <div class="container">
            <h2 class="d-flex align-items-center justify-content-center mb-3 mt-4"
                style="font-family: Quicksand, sans-serif;">
                <hr class="flex-grow-1 me-3"/>
                Danh Sách Sản Phẩm
                <hr class="flex-grow-1 ms-3"/>
            </h2>

            <div class="row">
                <c:forEach var="item" items="${products}">
                    <div class="col-xl-3">
                        <div class="card mt-3 border-0" style="width: 320px;">
                            <a href="/detail?id=<c:out value='${item.id}' />&categoryId=<c:out value='${item.category.id}' />">
                                <img src="images/${item.image}" class="card-img-top" alt="...">
                            </a>
                            <div class="card-body">
                                <h4 class="card-title">${item.category.name}</h4>
                                <p class="card-text" style="font-size: 19px;">${item.name}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="h5 mb-0"><fmt:formatNumber>${item.price}</fmt:formatNumber>VNĐ</span>
                                </div>

                                <input type="hidden" id="quantity" value="1" readonly>

                                <div class="d-flex justify-content-between mt-3">
                                    <div>
                                        <a onclick="addCart(${account != null ? account.id : "null"}, ${item.id})"
                                           class="btn btn-primary btn-custum">Thêm vào giỏ hàng</a>
                                    </div>

                                    <div class="mt-1">
                                        <i onclick="addFavorite(${account != null ? account.id : 'null'}, ${item.id}, 'heart-${item.id}')"
                                           id="heart-${item.id}" class="btn bi ${favoriteProductIds.contains(item.id) ? 'bi-heart-fill text-danger' : 'bi-heart'} fs-2">
                                        </i>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <nav aria-label="Page navigation example ">
                    <ul class="pagination justify-content-center mt-4">
                        <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item"><a class="page-link" href="/ajax/fetch">Next</a></li>
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<script>

    document.addEventListener('DOMContentLoaded', function () {
        const colors = ['#F9F1FF', '#FAF4EB', '#F4E5E4', '#E5F2F4'];

        const cards = document.querySelectorAll('.card-custom');

        cards.forEach((card, index) => {
            const color = colors[index % colors.length];
            card.style.backgroundColor = color;
        });
    });


</script>

</html>
