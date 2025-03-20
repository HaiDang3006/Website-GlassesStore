<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 11/16/2024
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js"></script>

</head>
<body>
<div class="container-fluid">

    <div class="mb-3">
        <h4>Thông kê doanh thu</h4>
    </div>

    <div class="row">
        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card h-80 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                Tổng Doanh Thu
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${TongDoanhThu}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-calendar fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card h-80 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Doanh Thu Hôm Nay
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${DoanhThuHomNay}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-4 col-md-6 mb-4">
            <div class="card h-80 py-2">
                <div class="card-body">
                    <div class="row no-gutters align-items-center">
                        <div class="col mr-2">
                            <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                Số lượng khách hàng
                            </div>
                            <div class="h5 mb-0 font-weight-bold text-gray-800">${KhachHang}</div>
                        </div>
                        <div class="col-auto">
                            <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">

        <div class="col-xl-6">
            <div class="card">
                <div class="card-body">
                    <canvas id="line" class="d-flex justify-content-between"></canvas>
                </div>
            </div>
        </div>

        <div class="col-xl-6">
            <div class="card">
                <div class="card-body">
                    <canvas id="myChart" class="d-flex justify-content-between"></canvas>
                </div>
            </div>
        </div>

    </div>


    <div class="card">
        <h5 class="card-header p-3">THÔNG KÊ HÓA ĐƠN</h5>
        <div class="card-body">
            <table id="myTable" class="table table-striped table-bordered display">
                <thead>
                <tr>
                    <th>Tên khách hàng</th>
                    <th>Địa chỉ</th>
                    <th>Ngày đặt hàng</th>
                    <th>Số lượng Sản Phẩm</th>
                    <th>Tổng tiền</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${HoaDon}" var="item">
                    <tr>
                        <td class="colum">${item[0]}</td>
                        <td class="colum">${item[2]}</td>
                        <td><fmt:formatDate value="${item[3]}" pattern="dd/MM/yyyy"/></td>
                        <td class="colum">${item[1]}</td>
                        <td class="colum"><fmt:formatNumber>${item[4]}</fmt:formatNumber></td>
                        <td>
                            <button
                                    onclick="getOderDetail(${item[5]})"
                                    type="button"
                                    class="btn btn-primary"
                                    data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                Xem
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="modal fade mt-5" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
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
                            <th scope="col">Tên sản phẩm</th>
                            <th scope="col">Số lượng</th>
                            <th scope="col">Đơn giá</th>
                            <th scope="col">Tổng</th>
                        </tr>
                        </thead>
                        <tbody id="chiTietHoaDon">
                        <tr class="">
                            <td scope="row">R1C1</td>
                            <td>R1C2</td>
                            <td>R1C3</td>
                        </tr>
                        </tbody>
                    </table>
                    <h3>Tổng giá: <span id="totalPrice">0 VNĐ</span></h3>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>

    var Thang = [];
    var KhachHang = [];
    var DoanhThu = [];

    <c:forEach items="${DataChart}" var="item">
    KhachHang.push("${item[0]}")
    Thang.push("${item[2]}")
    DoanhThu.push("${item[4]}")
    </c:forEach>

    let myChart = document.getElementById('myChart').getContext('2d');
    new Chart(myChart, {
        type: 'bar',
        data: {
            labels: Thang,
            datasets: [{
                label: 'Số lượng khách hàng',
                data: KhachHang,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                    'rgba(255, 159, 64, 0.6)',
                    'rgba(255, 99, 132, 0.6)'
                ],
            }]
        },
        options: {
            title: {
                display: true,
                text: 'SỐ LƯỢNG KHÁCH HÀNG THEO THÁNG',
                fontSize: 23
            },
            legend: {
                display: true,
                position: 'left',
                labels: {
                    fontColor: '#000'
                }
            },
            tooltips: {
                enabled: true
            }
        }
    });


    let myChart2 = document.getElementById('line').getContext('2d');
    let line = new Chart(myChart2, {
        type: 'line',
        data: {
            labels: Thang,
            datasets: [{
                label: 'Doanh Thu Tháng',
                data: DoanhThu,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.6)',
                    'rgba(54, 162, 235, 0.6)',
                    'rgba(255, 206, 86, 0.6)',
                    'rgba(75, 192, 192, 0.6)',
                    'rgba(153, 102, 255, 0.6)',
                    'rgba(255, 159, 64, 0.6)',
                    'rgba(255, 99, 132, 0.6)'
                ],
                borderWidth: 1,
                borderColor: '#777',
                hoverBorderWidth: 3,
                hoverBorderColor: '#000'
            }]
        },
        options: {
            title: {
                display: true,
                text: 'DOANH THU THEO THÁNG',
                fontSize: 23
            },
            legend: {
                display: true,
                position: 'Left',
                labels: {
                    fontColor: '#000'
                }
            },
            layout: {
                padding: {
                    left: 0,
                    right: 0,
                    bottom: 0,
                    top: 0
                }
            },
            tooltips: {
                enabled: true
            }
        }
    });


    $(document).ready(function () {
        $('#myTable').DataTable();
    });

    function getOderDetail(oderID) {

        var url = "http://localhost:8080/api/getOderDetail";
        var data = {"oderID": oderID};
        var options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        };

        fetch(url, options).then(resp => {
            return resp.json();
        }).then(json => {
            let totalPrice = 0;
            $('#chiTietHoaDon').empty();
            if (json.length > 0) {
                json.forEach(item => {
                    const row =
                        "<tr>" +
                        "<td>" + item.productName + "</td>" +
                        "<td>" + item.quantity + "</td>" +
                        "<td>" + item.price.toLocaleString('vi-VN') + "</td>" +
                        "<td>" + item.total.toLocaleString('vi-VN') + "</td>" +
                        "</tr>";
                    $('#chiTietHoaDon').append(row);
                    totalPrice += item.total;
                });
                $('#totalPrice').text(totalPrice.toLocaleString('vi-VN'));

            } else {
                $('#chiTietHoaDon').append('<tr><td colspan="4" class="text-center">Không có dữ liệu</td></tr>');
            }
        }).catch(err => {
            console.error("Error:", err);
            $('#chiTietHoaDon').append('<tr><td colspan="4" class="text-center">Lỗi khi lấy dữ liệu</td></tr>');
        });
    }

</script>


</body>
</html>
