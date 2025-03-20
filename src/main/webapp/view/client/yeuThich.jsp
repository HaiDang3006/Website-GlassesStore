<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/19/2024
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>


<html>
<head>
    <title>Title</title>
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
                <a class="nav-link" href="#">Yêu thích</a>
            </li>
        </ul>
    </div>

    <section class="bg-light p-4">
        <div class="container">

            <h2 class="text mb-3 mt-4" style="font-family: Quicksand, sans-serif;">
                Danh Sách Sản Phẩm Yêu Thích
            </h2>
            <hr>
            <div class="row">

                <c:forEach var="item" items="${listProduct}">
                    <div class="col-xl-3">
                        <div class="card mt-3 border-0" style="width: 320px;">
                            <a href="/detail?id=<c:out value='${item.product.id}' />&categoryId=<c:out value='${item.product.category.id}' />">
                                <img src="images/${item.product.image}" class="card-img-top" alt="..." >
                            </a>
                            <div class="card-body">
                                <h4 class="card-title">${item.product.category.name}</h4>
                                <p class="card-text" style="font-size: 19px;">${item.product.name}</p>
                                <div class="d-flex justify-content-between align-items-center">
                                    <span class="h5 mb-0"><fmt:formatNumber>${item.product.price}</fmt:formatNumber></span>
                                    <div>
                                        <small class="text-danger">Lượt thích: 40</small>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-between mt-3">
                                    <div>
                                        <a onclick="addCart(${account != null ? account.id : "null"}, ${item.product.id})" class="btn btn-primary btn-custum">Thêm vào giỏ hàng</a>
                                    </div>
                                    <div class="mt-1">
                                        <i onclick="addFavorite(${account.id}, ${item.product.id}, 'heart-${item.product.id}')"
                                           id="heart-${item.product.id}" class="btn bi bi-heart-fill text-danger fs-2"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </section>
</main>

<jsp:include page="header-footer/footer.jsp"/>

</body>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<script>
    function addFavorite(accountId, productId, idHeader) {
        var url = "http://localhost:8080/api/favorites/add";
        var data = {"account_id": accountId, "product_id": productId};
        var options = {
            method: "post",
            header: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }
        fetch(url, options).then(resp => {
            console.log("ok: " + resp.ok);
            console.log("status: " + resp.status);
            resp.json().then(json => {
                console.log(json);
                const heartElement = document.getElementById(idHeader);
                if (json.type != 1) {
                    heartElement.classList.remove("bi-heart-fill", "text-danger");
                    heartElement.classList.add("bi-heart");
                }
            })
        })
    }

    function addCart(accountId, productId) {
        if(accountId == null){
            if(confirm("Chức năng này cần phải đăng nhập bạn có muốn đăng nhập: Yes để chuyến đến đăng nhập")){
                window.location.href = "http://localhost:8080/login";
            }
            return;
        }
        var url = "http://localhost:8080/api/cart/add";
        var data = {"account_id": accountId, "product_id": productId};
        var options = {
            method: "post",
            header: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }
        fetch(url, options).then(resp => {
            console.log("ok: " + resp.ok);
            console.log("status: " + resp.status);
            resp.json().then(json => {
                console.log(json);
                Swal.fire({
                    title: "Thêm thành công!",
                    text: "Sản phẩm đã có trong giỏ hàng!",
                    icon: "success",
                    timer: 1000
                });
            })
        })
    }


</script>

</html>
