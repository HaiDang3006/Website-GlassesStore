<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 7:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>

<html>
<head>
    <title>Title</title>
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
    <section class="mt-2">

        <div class="container">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <ul class="nav p-1">
                    <li class="nav-item">
                        <a class="nav-link" href="/home" aria-current="page">Trang Chủ</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page">/</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">${category.name}</a>
                    </li>
                </ul>


                <div class="d-flex align-items-center">
                    <span class="me-2">Showing 1–12 of 20 results</span>
                    <select class="form-select" style="width: 200px;">
                        <option selected>Sort by price: high to low</option>
                        <option value="low-high">Sort by price: low to high</option>
                    </select>
                </div>
            </div>

            <div class="row">
                <div class="col-xl-3">
                    <h4>DANH MỤC</h4>
                    <ul class="list-group list-group-flush">
                        <c:forEach var="item" items="${listCategory}">
                            <li class="list-group-item">
                                <a href="/categoryDetail?id=<c:out value='${item[0]}'/>"
                                   style="text-decoration: none; font-size: 20px" class="nav-link d-flex justify-content-between align-items-center">
                                        ${item[1]}
                                    <span style="font-size: 15px">(${item[2]})</span>

                                </a>
                            </li>

                        </c:forEach>
                    </ul>
                </div>

                <div class="col-xl-9">
                    <div class="row">
                        <c:forEach var="item" items="${listProduct}">
                            <div class="col-xl-4">
                                <div class="card mt-3 border-0">
                                    <a href="/detail?id=<c:out value='${item.id}' />&categoryId=<c:out value='${item.category.id}' />">
                                        <img src="images/${item.image}"
                                             class="card-img-top" alt="...">
                                    </a>
                                    <div class="card-body">
                                        <p class="card-title">${item.category.name}</p>
                                        <p class="card-text mb-1" style="font-size: 19px;">${item.name}</p>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <span class="h5 mb-0"><fmt:formatNumber>${item.price}</fmt:formatNumber>VNĐ</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                    </div>

                </div>


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

</html>
