<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/17/2024
  Time: 12:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">

    <div class="mt-2 mb-3">
        <h3>QUẢN LÝ SẢN PHẨM</h3>
    </div>

    <div class="card">
        <div class="card-header d-flex justify-content-between">
            <h5 style="margin-top: 9px">DANH SÁCH SẢN PHẨM</h5>
            <a class="btn btn-primary" href="/product/new">Thêm sản phẩm</a>
        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">IMAGE</th>
                    <th scope="col">NAME</th>
                    <th scope="col">PRICE</th>
                    <th scope="col">CATEGORY</th>
                    <th scope="col" width="15%">ACTION</th>
                </tr>
                </thead>
                <c:forEach items="${listProduct}" var="product">
                    <tr>
                        <th scope="row">${product.id}</th>
                        <td><img alt="" src="/images/${product.image}" width="50" height="50"></td>
                        <td>${product.name}</td>

                        <td><fmt:formatNumber>${product.price}</fmt:formatNumber></td>

                        <td>${product.category.name}</td>
                        <td>
                            <a class="btn btn-success" href="/product/edit?id=${product.id}">Sửa</a> |
                            <a class="btn btn-danger" id="${product.id}" onclick="deleteProduct(${product.id})">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div class="d-flex justify-content-center">
                <nav>
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/product/list?page=${i}">Trang ${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>

</body>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    function deleteProduct(id) {
        var url = "http://localhost:8080/api/deleteProduct";
        var data = {"productId": id};

        var options = {
            method: "POST",
            headers: { // Sửa "header" thành "headers"
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        };

        Swal.fire({
            title: "Thông báo!",
            text: "Bạn có muốn xóa sản phẩm này không?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Xóa",
            cancelButtonText: "Không"
        }).then((result) => {
            if (result.isConfirmed) {
                fetch(url, options)
                    .then(response => response.json())
                    .then(json => {

                        let icon = '';

                        if (json == "Không thể xóa sản phẩm") {
                            icon = "warning"
                        } else {
                            icon = "success"
                        }
                        Swal.fire({
                            title: "Kết quả",
                            text: json,
                            icon: icon
                        }).then(() => {
                            location.reload();
                        });
                    })
            }
        });

        return true;
    }

</script>
</html>
