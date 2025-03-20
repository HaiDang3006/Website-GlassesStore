<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/16/2024
  Time: 9:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <title>ADMIN PAGE</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/css.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>

    <style>
        ul li {
            list-style: none;
        }


        #sidebar.collapsed {
            margin-left: -264px;
        }

        #main {
            margin-left: 264px;
            transition: margin-left 0.3s ease;
        }


        @media (max-width: 767.98px) {

            #sidebar {
                margin-left: -264px; /* Mặc định sidebar ẩn trên màn hình nhỏ */
            }

            #sidebar.collapsed {
                margin-left: 0; /* Sidebar hiện khi toggle */
            }

            #main {
                margin-left: 0; /* Main luôn full-width trên màn hình nhỏ */
            }

        }

    </style>
</head>
<body>

<div class="wrapper">
    <aside id="sidebar" class="js-sidebar">
        <div class="h-100">
            <div class="sidebar-logo">
                <a href="#">
                    <img src="https://gudlogo.com/wp-content/uploads/2021/01/MATTI-LOGO-official-1030x340.png"
                         alt="" height="70">
                </a>
            </div>
            <ul class="sidebar-nav">
                <li class="sidebar-header">
                    Quản trị
                </li>

                <li class="sidebar-item mb-2">
                    <a style="list-style: none" href="/Admin" class="sidebar-link">
                        <i class="bi bi-speedometer2 pe-2"></i>
                        Thống kê
                    </a>
                </li>

                <li class="sidebar-item mb-2">
                    <a href="/category" class="sidebar-link">
                        <i class="bi bi-layers pe-2"></i>
                        Quản lý loại sản phẩm
                    </a>
                </li>

                <li class="sidebar-item mb-2">
                    <a href="/product" class="sidebar-link">
                        <i class="bi bi-box pe-2"></i>
                        Quản lý Sản Phẩm
                    </a>
                </li>

                <li class="sidebar-item mb-2">
                    <a href="/Admin/account" class="sidebar-link">
                        <i class="bi bi-person pe-2"></i>
                        Quản lý Tài Khoản
                    </a>
                </li>

            </ul>
        </div>
    </aside>

    <div id="main" class="main">
        <nav class="navbar navbar-expand px-3 border-bottom">
            <button class="btn" id="sidebar-toggle" type="button">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="navbar-collapse navbar">
                <ul class="navbar-nav">
                    <li class="nav-item me-3">
                        <a href="/home" class="nav-link btn btn-primary">
                            Trang chủ
                        </a>
                    </li>
                    <li class="nav-item dropdown">
                        <a href="#" data-bs-toggle="dropdown" class="nav-icon pe-md-0">
                            <img src="/images/${account.photo}" class="avatar img-fluid rounded" alt="">
                        </a>
                        <div class="dropdown-menu dropdown-menu-end">
                            <a href="#" class="dropdown-item">Profile</a>
                            <a href="/logout" class="dropdown-item">Logout</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <main class="content px-3 py-2">
            <jsp:include page="${view}"></jsp:include>
        </main>

        <footer class="footer">
            <div class="container-fluid">
                <div class="row text-muted">
                    <div class="col-6 text-start">
                        <p class="mb-0">
                            <a href="#" class="text-muted">
                                <strong>TRÂN HẢI ĐĂNG PC08348</strong>
                            </a>
                        </p>
                    </div>
                    <div class="col-6 text-end">
                        <ul class="list-inline">
                            <li class="list-inline-item">
                                <a href="#" class="text-muted">Contact</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="#" class="text-muted">About Us</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="#" class="text-muted">Terms</a>
                            </li>
                            <li class="list-inline-item">
                                <a href="#" class="text-muted">Booking</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Chi tiết hóa đơn</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">Column 1</th>
                            <th scope="col">Column 2</th>
                            <th scope="col">Column 3</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="">
                            <td scope="row">R1C1</td>
                            <td>R1C2</td>
                            <td>R1C3</td>
                        </tr>
                        <tr class="">
                            <td scope="row">Item</td>
                            <td>Item</td>
                            <td>Item</td>
                        </tr>
                        </tbody>
                    </table>

                    <h3>Tổng giá</h3>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>

<script type="text/javascript">

    $(document).ready(function () {
        $('#myTable').DataTable();
    });

</script>
