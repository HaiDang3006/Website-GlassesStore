<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 12/4/2024
  Time: 12:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
</head>
<body>

<div class="card">
    <h5 class="card-header p-3">DANH SÁCH TÀI KHOẢN</h5>
    <div class="card-body">
        <table id="myTable" class="table table-striped table-bordered display">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Email</th>
                <th scope="col">Họ và tên</th>
                <th scope="col">Trạng thái</th>
                <th scope="col">Chức vụ</th>
                <th scope="col">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listAccount}" var="item">

                <tr>
                    <th scope="row">${item.id}</th>
                    <td>${item.email}</td>
                    <td>${item.fullname}</td>
                    <td>${item.activated ? "Đang hoạt động" : "Ngừng hoạt động"}</td>
                    <td>${item.admin ? "Admin" : "Khách hàng"}</td>
                    <td>
                        <a class="btn btn-success" onclick="edit('${item.email}', ${item.activated})">Sửa</a>
                    </td>
                </tr>

            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Update -->
<div class="modal fade" id="modalUpdateAcc" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel2">Cập nhật Catergory</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="updateForm" action="/update/acc" method="post">

                <div class="modal-body">
                    <div class="form-group">
                        <input type="hidden" id="email1" name="email" value=""/>
                    </div>

                    <div class="form-check form-switch">
                        <input class="form-check-input" name="trangThai" type="checkbox" id="trangThai" />
                        <label class="form-check-label" for="trangThai">Trạng thái</label>
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
    function edit(email, activated) {

        $('#email1').val(email);

        $('#trangThai').prop('checked', activated);

        $('#modalUpdateAcc').modal('show');
    }
</script>

</html>
