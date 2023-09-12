var getUnpaidCartApi = 'http://localhost:8088/cart/getUnpaidCart';

function start(){
    getUnpaid(function(res){
        getUnpaidCart(res?.data || [])
        deleteCart(res?.data || [])
    });
}
start();

function getUnpaid(callback){
    fetch(getUnpaidCartApi)
    .then(function(response){
        return response.json();
    }).then(callback)
}
function deleteCart(id){
    var deleteCartApi = 'http://localhost:8088/cart/deleteCart?cartId=' + id;
    fetch(deleteCartApi,{
        method : 'POST'
    })
    .then(response => response.json())
    .then(data => {
      // Xử lý dữ liệu từ API
      console.log(data);
      start();
    })
    .catch(error => {
      // Xử lý lỗi
      console.error(error);
    })
}
function getUnpaidCart(data){
    var getUnpaid = document.querySelector('#getUnpaid');
    var htmls = data.map(function(elem){
        return `<tr>
        <td class="shoping__cart__item">
            <img src="${elem.image}" alt="">
            <h5>${elem.name}</h5>
        </td>
        <td class="shoping__cart__price">
            ${elem.price}.00$
        </td>
        <td class="shoping__cart__quantity">
            <div class="quantity">
                <div class="pro-qty">
                    <input type="text" value="${elem.quality}">
                </div>
            </div>
        </td>
        <td class="shoping__cart__total">
        100
        </td>
        <td class="shoping__cart__item__close">
            <span id=${elem.id} class="icon_close" onclick ="deleteCart(this.id)"></span>
        </td>
    </tr>`;
    });
    getUnpaid.innerHTML = htmls.join("");
}

// Lấy liên kết theo ID
var myLink = document.getElementById('my-link');

// Gắn sự kiện click vào liên kết
myLink.addEventListener('click', function(event) {
  event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
  window.location.href = 'shop-grid.html';
  // Thực hiện hành động JavaScript tại đây
  console.log('Liên kết đã được nhấp');
  // ...
});