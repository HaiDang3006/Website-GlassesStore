<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/14/2024
  Time: 7:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>


<html>
<head>
    <title>Title</title>
    <!-- Required meta tags -->
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>

    <link rel="stylesheet" href="/css/style.css">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>

        .product-image {
            width: 80px;
            height: auto;
        }

        .product-info {
            flex: 1;
            padding-left: 15px;
        }

        .price {
            font-weight: bold;
            /*color: #dc3545;*/
        }

        .quantity-box input {
            width: 50px;
            text-align: center;
            margin: 0 5px;
        }

        .discount-text {
            color: #28a745;
            font-size: 14px;
        }

    </style>

</head>
<body>
<jsp:include page="header-footer/header.jsp"/>
<main>
    <div class="container">
        <ul class="nav p-1 mt-2 ">
            <li class="nav-item">
                <a class="nav-link" href="/home" aria-current="page">Trang Chủ</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" aria-current="page">/</a>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">Giỏ hàng</a>
            </li>
        </ul>
        <hr>


        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="home-tab" data-bs-toggle="tab"
                        data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane"
                        aria-selected="true">Giỏ hàng
                </button>
            </li>

            <li class="nav-item" role="presentation">
                <button class="nav-link" id="profile-tab" data-bs-toggle="tab"
                        data-bs-target="#profile-tab-pane" type="button" role="tab"
                        aria-controls="profile-tab-pane" aria-selected="false">Đơn hàng
                </button>
            </li>
        </ul>

        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel"
                 aria-labelledby="home-tab" tabindex="0">
                <c:choose>

                    <c:when test="${listCart == null || listCart.isEmpty()}">
                        <section>
                            <div class="container">
                                <div class="text-center mt-3">
                                    <img src="https://deo.shopeemobile.com/shopee/shopee-pcmall-live-sg/orderlist/5fafbb923393b712b964.png" alt="Giỏ hàng trống">
                                    <p>Giỏ hàng của bạn còn trống</p>
                                    <button class="btn btn-buy-now">Mua ngay</button>
                                </div>
                            </div>
                        </section>
                    </c:when>

                    <c:otherwise>
                        <div class="card-body">
                            <section>
                                <form id="cartForm" action="/cart/order" method="post">
                                    <div class="row">
                                        <div class="col-xl-7 p-3">
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="title d-flex justify-content-between mb-3">
                                                        <h5 class="card-title">GIỎ HÀNG CỦA BẠN</h5>
                                                    </div>
                                                    <div class="table-responsive">
                                                        <table class="table border-1">
                                                            <thead>
                                                            <tr>
                                                                <th class="text-nowrap" scope="col">Hình ảnh</th>
                                                                <th class="text-nowrap" scope="col">Tên</th>
                                                                <th class="text-nowrap" scope="col">Giá</th>
                                                                <th class="text-nowrap" scope="col">Số lượng</th>

                                                                <th class="text-nowrap" style="width: 121px"
                                                                    scope="col">TỔNG CỘNG
                                                                </th>
                                                                <th class="text-nowrap" scope="col">Tùy Chọn</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach items="${listCart}" var="item">
                                                                <tr>
                                                                    <td scope="row">
                                                                        <img src="/images/${item.product.image}" alt=""
                                                                             height="70">
                                                                    </td>
                                                                    <td>${item.product.name}</td>
                                                                    <td id="unitPrice_${item.id}">
                                                                        <fmt:formatNumber>${item.product.price}</fmt:formatNumber></td>
                                                                    <td>

                                                                        <div class="quantity-control d-flex justify-content-evenly">
                                                                            <button type="button" class="btn btn-light"
                                                                                    onclick="updateQuantity(${item.id}, -1, ${item.product.id}, ${account.id})">
                                                                                -
                                                                            </button>
                                                                            <input type="text" id="quantity_${item.id}"
                                                                                   value="${item.quantity}"
                                                                                   oninput="updateTotal(${item.id})"
                                                                                   readonly>
                                                                            <button type="button" class="btn btn-light"
                                                                                    onclick="updateQuantity(${item.id}, 1, ${item.product.id}, ${account.id})">
                                                                                +
                                                                            </button>
                                                                        </div>
                                                                    </td>
                                                                    <td id="totalPrice_${item.id}">
                                                                        <fmt:formatNumber>${item.product.price * item.quantity}</fmt:formatNumber>
                                                                    </td>
                                                                    <td>
                                                                        <div class="ok d-flex justify-content-between">
                                                                            <a href="/cart/delete?id=${item.id}">
                                                                                <i class="bi bi-trash3-fill "></i>
                                                                            </a>
                                                                            <a href="">
                                                                                <input type="checkbox"
                                                                                       id="checkbox_${item.id}"
                                                                                       class="item-checkbox"
                                                                                       name="cartDetailIds"
                                                                                       value="${item.id}"
                                                                                       data-total="${item.product.price * item.quantity}"
                                                                                       data-quantity="${item.quantity}"
                                                                                       onchange="updateTotal(${item.id})">
                                                                            </a>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xl-5 p-3">
                                            <div class="card">
                                                <div class="card-body">

                                                    <div class="title d-flex justify-content-between">
                                                        <h5 class="card-title">TẠM TÍNH: </h5>
                                                        <h5 class="card-title text-danger" id="totalAmount">0</h5>
                                                    </div>
                                                    <hr>

                                                    <div class="row">
                                                        <div class="mb-2">
                                                            <div class="section-title">
                                                                Địa chỉ giao hàng
                                                            </div>
                                                            <div class="mt-3">

                                                                <div class="row">
                                                                    <div class="col-md-6">

                                                                        <select class="form-select mb-3" id="province">
                                                                            <option value="">Chọn tỉnh / thành</option>
                                                                        </select>

                                                                    </div>
                                                                    <div class="col-md-6">
                                                                        <select class="form-select mb-3" id="district">
                                                                            <option value="">Chọn quận / huyện</option>
                                                                        </select>
                                                                    </div>
                                                                </div>

                                                                <input class="form-control mb-3" id="street" placeholder="Tên đường, số nhà" type="text"/>

                                                                <input class="form-control mb-3" id="fullAddress" name="address" placeholder="Địa chỉ đầy đủ" type="text" readonly/>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <button type="submit" class="btn btn-danger">Đặt hàng</button>
                                                    <div class="row">
                                                        <div class="col-12">
                                                            <div class="contact-info mt-3">
                                                                <p>
                                                                    Bạn cần hỗ trợ? Liên hệ ngay:
                                                                </p>
                                                                <p>
                                                                    <i class="fas fa-phone-alt">
                                                                    </i>
                                                                    0981787876
                                                                </p>
                                                                <p>
                                                                    <i class="fas fa-envelope">
                                                                    </i>
                                                                    dangthpc08348@gmail.com
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </section>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab"
                 tabindex="0">
                <div class="card-body">
                    <section>
                        <c:forEach items="${finAllProductByHoaDon}" var="item">
                            <div class="card p-3 mt-3">
                                <h6><span class="badge bg-danger">Đã đặt hàng</span></h6>
                                <c:forEach items="${hoaDonChiTietMap[item[0]]}" var="chiTiet">
                                    <div class="d-flex align-items-center border-top pt-3">
                                        <img src="images/${chiTiet[3]}"
                                             alt="Sản phẩm" class="product-image ms-3">
                                        <div class="product-info">
                                            <h6>${chiTiet[2]}</h6>
                                            <p class="text-muted mb-2">Giá tiền: <fmt:formatNumber>${chiTiet[4]}</fmt:formatNumber>VNĐ</p>
                                            <p class="text-muted mb-2">Số lượng: ${chiTiet[6]}</p>
                                            <p class="text-muted mb-2">Ngày đặt: ${chiTiet[8]}</p>
                                            <p class="text-muted">Địa chỉ: ${chiTiet[5]}</p>
                                        </div>
                                        <div class="price">Đơn giá: <fmt:formatNumber>${chiTiet[7]}</fmt:formatNumber>VNĐ</div>
                                    </div>

                                </c:forEach>
                                <div class="border-top mt-3 pt-2">

                                    <p class="discount-text">
                                        Giảm ₫300.000 phí vận chuyển đơn tối thiểu ₫0; Giảm ₫500.000 phí vận
                                        chuyển
                                        đơn tối thiểu ₫500.000
                                    </p>

                                </div>
                            </div>
                        </c:forEach>
                    </section>
                </div>
            </div>
        </div>

        <hr>


        <section class="mt-5">
            <h2 class="text mb-3 mt-4" style="font-family: Quicksand, sans-serif;">
                CÓ THỂ BẠN THÍCH
            </h2>
            <div class="row">
                <c:forEach var="item" items="${listProduct}">
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
                                        <a onclick="addCart(${account != null ? account.id : "null"}, ${item.id})"
                                           class="btn btn-primary btn-custum">Thêm vào giỏ hàng</a>
                                    </div>

                                    <div class="mt-1">
                                        <i onclick="addFavorite(${account != null ? account.id : "null"}, ${item.id}, 'heart-${item.id}')"
                                           id="heart-${item.id}" class="btn bi bi-heart fs-2"></i>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </div>

</main>
<jsp:include page="header-footer/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/script.js"></script>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>


<script
        src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"
></script>

<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"
></script>


<script>
    document.addEventListener("DOMContentLoaded", function() {
        fetch("https://provinces.open-api.vn/api/?depth=2")
            .then(response => response.json())
            .then(data => {
                let provinceSelect = document.getElementById("province");
                data.forEach(province => {
                    let option = document.createElement("option");
                    option.value = province.code;
                    option.textContent = province.name;
                    provinceSelect.appendChild(option);
                });

                window.provinceData = data;
            })
            .catch(error => console.error("Lỗi khi tải dữ liệu tỉnh/thành:", error));
    });

    document.getElementById("province").addEventListener("change", function() {
        let provinceCode = this.value;
        let districtSelect = document.getElementById("district");
        districtSelect.innerHTML = '<option value="">Chọn quận / huyện</option>';

        if (provinceCode && window.provinceData) {
            let province = window.provinceData.find(p => p.code == provinceCode);
            if (province && province.districts) {
                province.districts.forEach(district => {
                    let option = document.createElement("option");
                    option.value = district.code;
                    option.textContent = district.name;
                    districtSelect.appendChild(option);
                });
            }
        }

        updateFullAddress();
    });

    document.getElementById("district").addEventListener("change", updateFullAddress);
    document.getElementById("street").addEventListener("input", updateFullAddress);

    function updateFullAddress() {
        let province = document.getElementById("province").selectedOptions[0]?.text || "";
        let district = document.getElementById("district").selectedOptions[0]?.text || "";
        let street = document.getElementById("street").value.trim();

        let fullAddress = [street, district, province].filter(Boolean).join(", ");
        document.getElementById("fullAddress").value = fullAddress;
    }

    document.getElementById("cartForm").addEventListener("submit", function (e) {
        e.preventDefault();

        const province = document.getElementById("province").value.trim();
        const district = document.getElementById("district").value.trim();
        const street = document.getElementById("street").value.trim();
        const address = document.getElementById("fullAddress").value.trim();
        if (!address || !province || !district || !street ) {
            Swal.fire({
                title: "Thông báo",
                text: "Cần nhập đầy đủ thông tin",
                icon: "error",
                timer: 2000
            });
            e.preventDefault();
            return;
        }

        const checkboxes = document.querySelectorAll(".item-checkbox");
        let isAnyItemChecked = false;

        checkboxes.forEach((checkbox) => {
            if (checkbox.checked) {
                isAnyItemChecked = true;
            }
        });

        if (!isAnyItemChecked) {
            Swal.fire({
                title: "Thông báo",
                text: "Bạn chưa chọn sản phẩm để thanh toán",
                icon: "error",
                timer: 2000
            });
            e.preventDefault();
            return;
        }

        if(address && isAnyItemChecked){
            Swal.fire({
                title: "Thành công!",
                text: "Đặt hàng thành công" +
                    " hãy kiểm tra trong đơn hàng",
                icon: "success",
                timer: 2000,
                showConfirmButton: false
            }).then(() => {
                e.target.submit();
            });
        }
    });

    function updateQuantity(itemId, change, productID, accountID) {

        const quantityInput = document.getElementById("quantity_" + itemId);

        let quantity = parseInt(quantityInput.value);
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
        }

        quantity = Math.max(1, quantity + change);
        quantityInput.value = quantity;

    }

    function updateTotal(itemId) {
        const quantity = parseInt(document.getElementById("quantity_" + itemId).value);
        const unitPriceText = document.getElementById("unitPrice_" + itemId).innerText;
        const totalPriceElement = document.getElementById("totalPrice_" + itemId);
        const checkbox = document.getElementById("checkbox_" + itemId);

        const unitPrice = parseFloat(unitPriceText.replace(/\D/g, ""));

        const totalPrice = quantity * unitPrice;

        totalPriceElement.innerText = totalPrice.toLocaleString();

        updateCartTotal();
    }

    function updateCartTotal() {
        let totalAmount = 0;

        document.querySelectorAll("[id^='totalPrice_']").forEach((element) => {
            const productId = element.id.split('_')[1];
            const checkbox = document.getElementById("checkbox_" + productId);

            if (checkbox.checked) {
                const price = parseFloat(element.innerText.replace(/\D/g, ""));
                totalAmount += price;
            }
        });

        document.getElementById("totalAmount").innerText = totalAmount.toLocaleString();
    }

    window.onload = function () {
        updateQuantity();
        updateCartTotal();
    };

</script>
</body>
</html>
