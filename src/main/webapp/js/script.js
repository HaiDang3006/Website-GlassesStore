

function addFavorite(accountId, productId, idHeader) {
    if (accountId == null) {
        Swal.fire({
            title: "Thông báo",
            text: "Chức năng này cần phải đăng nhập",
            icon: "warning",
            timer: 4000
        });
    }
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
            if (json.type == 1) {
                heartElement.classList.remove("bi-heart");
                heartElement.classList.add("bi-heart-fill", "text-danger");
            } else {
                heartElement.classList.remove("bi-heart-fill", "text-danger");
                heartElement.classList.add("bi-heart");
            }
        })
    })
}

function addCart(accountId, productId) {

    if (accountId == null) {
        Swal.fire({
            title: "Thông báo",
            text: "Chức năng này cần phải đăng nhập",
            icon: "warning",
            timer: 4000
        });
    }

    var quantity = document.getElementById("quantity").value;
    var url = "http://localhost:8080/api/cart/add";
    var data = {"account_id": accountId, "product_id": productId, "quantity": quantity};

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