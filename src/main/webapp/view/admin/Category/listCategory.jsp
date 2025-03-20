
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Category</title>
</head>
<body>
<div class="container-fluid">

    <div class="mt-2 mb-3">
        <h3>QUẢN LÝ CATEGORY</h3>
    </div>

    <div class="card">
        <div class="card-header d-flex justify-content-between">
            <h5 style="margin-top: 9px">DANH SÁCH CATEGORY</h5>

            <a href="/category/new" class="btn btn-primary" >
                Thêm mới
            </a>

        </div>
        <div class="card-body">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">NAME</th>
                    <th scope="col" width="15%">ACTION</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${listCategory}" var="cate">
                    <tr>
                        <th scope="row">${cate.id}</th>
                        <td>${cate.name}</td>
                        <td>
                            <a class="btn btn-success" href="/category/edit?id=${cate.id}">Sửa</a>
                            <a hidden="hidden" class="btn btn-danger" onclick="remove(${cate.id})">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="d-flex justify-content-center">
                <nav>
                    <ul class="pagination">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/category/list?page=${i}">Trang ${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>


<!-- Modal Delte -->
<div class="modal fade" id="modalDelete" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Thông báo</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="updateForm" action="/category/update" method="post">
                <div class="modal-body">

                    <div class="form-group">
                        <input type="hidden" id="id" name="id" value=""/>
                    </div>

                    <div class="form-group">
                        <label class="mb-2" for="name">Tên Category</label>
                        <input type="text" class="form-control" id="name" name="typeName" value=""/>
                        <small id="nameError" class="text-danger"></small> <!-- Thông báo lỗi -->
                    </div>
                </div>

                <div class="modal-footer mt-4">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Thoát</button>
                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script>

    function remove(id) {
        $('#id_delete').val(id);
        $('#modalDelete').modal('show');
    }

</script>

</html>
