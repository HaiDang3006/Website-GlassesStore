<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Quản lý Loại Sản Phẩm</title>
</head>
<body>

<div class="container-fluid">
    <h3 class="mb-4">QUẢN LÝ LOẠI SẢN PHẨM</h3>

    <div class="card">
        <div class="card-header d-flex justify-content-between">
            <h3 class="m-2">
                ${category != null ? 'Cập nhật sản phẩm' : 'Thêm mới sản phẩm'}
            </h3>
        </div>

        <div class="card-body">
            <form id="categoryForm"
                  action="${category != null ? '/category/update' : '/category/insert'}"
                  method="post" enctype="multipart/form-data">
                <div class="modal-body">

                    <input type="hidden" name="id" value="${category.id}"/>

                    <div class="form-group">
                        <label class="mb-2" for="nameInsert">Tên Category</label>
                        <input type="text" class="form-control" id="nameInsert" name="typeName"
                               value="${category.name}"/>
                        <small id="nameErrorInsert" class="text-danger">
                            <c:out value="${nameErrorInsert}"/>
                        </small>
                    </div>

                    <div class="form-group">
                        <label for="image" class="form-label">Ảnh</label>
                        <input type="file" class="form-control" id="image" name="image">
                        <span class="text-danger small" id="errorImage"></span>
                        <c:if test="${category.image != null}">
                            <div class="mt-2">
                                <input type="hidden" class="form-control" value="${category.image}" id="currentImage" name="currentImage">
                            </div>
                        </c:if>
                    </div>
                </div>

                <div class="modal-footer mt-4">
                    <button type="button" class="btn btn-secondary">Thoát</button>
                    <button type="submit" class="btn btn-primary">
                        ${category != null ? 'Cập nhật' : 'Thêm mới'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>


<script>
    document.getElementById('categoryForm').addEventListener('submit', function (event) {
        let isValid = true;

        const name = document.getElementById('nameInsert');
        const image = document.getElementById('image');

        const errorName = document.getElementById('nameErrorInsert');
        const errorImage = document.getElementById('errorImage');
        const currentImage = document.getElementById('currentImage');


        errorName.innerText = '';
        errorImage.innerText = '';


        // Kiểm tra Name
        if (name.value.trim() === '') {
            errorName.innerText = 'Tên danh mục không được để trống!';
            isValid = false;
        } else if (name.value.length < 3) {
            errorName.innerText = 'Tên danh mục phải có ít nhất 3 ký tự!';
            isValid = false;
        } else if (name.value.length > 50) {
            errorName.innerText = 'Tên Category không được quá 50 ký tự!';
            isValid = false;
        }

        // Kiểm tra ảnh (Chỉ bắt buộc khi thêm mới)
        if (image.value.trim() === '' && (!currentImage || currentImage.value.trim() === '')) {
            errorImage.innerText = 'Vui lòng chọn ảnh.';
            isValid = false;
        }

        let allowedExtensions = ["image/jpeg", "image/png", "image/jpg"];
        let file = image.files[0];
        if (file) {
            if (!allowedExtensions.includes(file.type)) {
                errorImage.textContent = "Chỉ chấp nhận file ảnh (.jpg, .jpeg, .png).";
                isValid = false;
            }
        }

        // Ngăn form submit nếu có lỗi
        if (!isValid) {
            event.preventDefault();
        }
    });

</script>


</html>
