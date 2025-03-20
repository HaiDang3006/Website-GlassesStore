<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/17/2024
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <h3 class="mb-4">QUẢN LÝ SẢN PHẨM</h3>

    <div class="card">
        <div class="card-header d-flex justify-content-between">
            <h3 class="m-2">THÊM MỚI PRODUCT</h3>
        </div>

        <div class="card-body">
            <form id="productForm" action="/product/insert" method="post" enctype="multipart/form-data">

                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name">
                    <span class="text-danger small" id="errorName"></span>

                </div>

                <div class="mb-3">
                    <label for="image" class="form-label">Ảnh</label>
                    <input type="file" class="form-control" id="image" name="image">
                    <span class="text-danger small" id="errorImage"></span>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Price</label>
                    <input type="text" class="form-control" id="price" name="price">
                    <span class="text-danger small" id="errorPrice"></span>
                </div>

                <div class="mb-3">
                    <label for="name" class="form-label">Category</label>
                    <select class="form-select" aria-label="Default select example" name="category_id" id="category">
                        <option value="">Danh mục sản phẩm</option>
                        <c:forEach var="cate" items="${categories}">
                            <option value="${cate.id}">${cate.name}</option>
                        </c:forEach>
                    </select>
                    <span class="text-danger small" id="errorCategory"></span>
                </div>

                <div class="mb-3 d-flex justify-content-between">
                    <a href="/product">
                        <button type="button" class="btn btn-primary">Quay lại</button>
                    </a>
                    <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    document.getElementById('productForm').addEventListener('submit', function (event) {
        let isValid = true;


        const name = document.getElementById('name');
        const image = document.getElementById('image');
        const price = document.getElementById('price');
        const category = document.getElementById('category');


        const errorName = document.getElementById('errorName');
        const errorImage = document.getElementById('errorImage');
        const errorPrice = document.getElementById('errorPrice');
        const errorCategory = document.getElementById('errorCategory');


        errorName.innerText = '';
        errorImage.innerText = '';
        errorPrice.innerText = '';
        errorCategory.innerText = '';

        // Kiểm tra Name
        if (name.value.trim() === '') {
            errorName.innerText = 'Vui lòng nhập tên sản phẩm.';
            isValid = false;
        }else if (name.value.length < 3) {
            errorName.innerText = 'Tên sản phẩm quá ngắn';
            isValid = false;
        }else if (name.value.length > 250) {
            errorName.innerText = 'Tên sản phẩm quá dài';
            isValid = false;
        }

        // Kiểm tra Image
        if (image.value.trim() === '') {
            errorImage.innerText = 'Vui lòng chọn ảnh.';
            isValid = false;
        }

        // Kiểm tra Price
        if (price.value.trim() === '') {
            errorPrice.innerText = 'Vui lòng nhập giá.';
            isValid = false;
        } else if (isNaN(price.value) || Number(price.value) <= 0 || Number(price.value > 500000000)) {
            errorPrice.innerText = 'Giá phải là 1 số lớn hơn 0 và nhỏ hơn 500 triệu';
            isValid = false;
        }

        // Kiểm tra Category
        if (category.value === '') {
            errorCategory.innerText = 'Vui lòng chọn danh mục.';
            isValid = false;
        }

        // Ngăn form submit nếu có lỗi
        if (!isValid) {
            event.preventDefault();
        }
    });
</script>

</body>
</html>
