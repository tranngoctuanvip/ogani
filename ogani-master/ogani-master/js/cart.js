var getUnpaidCartApi = 'http://localhost:8088/cart/getUnpaidCart';
var paymentApi = 'http://localhost:8088/payment/createPayment';
var totalPaymentApi = 'http://localhost:8088/payment/totalPayment';
var token = localStorage.getItem('token');
// var unpaidApi = 'http://localhost:8088/cart/unpaid';
function starts(){
    getUnpaid(function(res){
        getUnpaidCart(res?.data || [])
    });
    totalPayment(function(res){
        getPayment(res?.data || [])
    });
}
starts();

function getUnpaid(callback){
    if (token){
        fetch(getUnpaidCartApi,{
            method: 'GET',
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        })
        .then(function(response){
            return response.json();  
        }).then(callback)
    }
}

function deleteCart(id){
    if(token){
        var confirmed = confirm('Bạn có chắc chắn muốn xóa không?');
        if(confirmed){
            var deleteCartApi = 'http://localhost:8088/cart/deleteCart?cartId=' + id;
            fetch(deleteCartApi,{
                method : 'POST',
                headers: {
                    'Authorization' : 'Bearer ' + token
                }
            })
            .then(response => response.json())
            .then(data =>{
                console.log(data);
                starts();
            })
            .catch(error => {
              // Xử lý lỗi
              console.error(error);
            })
        }
    }
    else {
        alert('Vui lòng đăng nhập');
    }
}

function totalPayment(callback){
    if(token){
        fetch(totalPaymentApi,{
            method: 'GET',
            headers: {
                'Authorization' : 'Bearer ' + token
            }
        }).then(response => response.json())
        .then(data => console.log(data))
        .then(callback)
    }
    else{
        alert('Vui lòng đăng nhập để thanh toán')
    }
}

function getPayment(data){
    var getPayment = document.querySelector('.shoping__checkout');
    var htmls = data.map(function(elem){
        return ` <h5>Cart Total</h5>
        <ul>
            <li>Subtotal <span>${elem.message}VND</span></li>
        </ul>
        <a href="#" class="primary-btn">PROCEED TO CHECKOUT</a>`
    }).join('');
    getPayment.innerHTML = htmls;
}

function getUnpaidCart(data){
    var getUnpaid = document.querySelector('#getUnpaid');
    var htmls = data.map(function(elem){
        var total = elem.price * elem.quality;
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
                <button class="minus-btn" onclick="minus()">-</button>
                    <input type="text" id="quantity-input" value="${elem.quality}">
                <button class="plus-btn" onclick="plus()">+</button>
                </div>
            </div>
        </td>
        <td class="shoping__cart__total">
        ${total}.00$
        </td>
        <td class="shoping__cart__item__close">
            <span id=${elem.id} class="icon_close" onclick ="return deleteCart(this.id)"></span>
        </td>
    </tr>`
    });
    getUnpaid.innerHTML = htmls.join("");
}

// Lấy liên kết theo ID
var myLink = document.getElementById('my-link');

var quantityInput = document.getElementById('#quantity-input');
var minusBtn1 = document.querySelector('.minus-btn');
var plusBtn1 = document.querySelector('.plus-btn');
// Xử lý sự kiện khi nhấn nút trừ
function minus(){
    minusBtn1.addEventListener('click', function(event) {
        event.preventDefault;
        var currentValue = quantityInput.value;
        if (currentValue > 0) {
          quantityInput.value = currentValue - 1;
        }
        });
}


// Xử lý sự kiện khi nhấn nút cộng
function plus(){
    plusBtn1.addEventListener('click', function(event) {
        event.preventDefault;
        var currentValue = quantityInput.value;
        quantityInput.value = currentValue + 1;
        });
}
 




// function statusByid(id,quality){
//         var data = {
//             quantity : quality 
//         }
//         var url = 'http://localhost:8088/cart/update?id=' +id;
//         fetch(url,{
//             method : 'POST',
//             body: JSON.stringify(data)
//         }).then(function(response){
//             return response.json();
//         }).catch(function(error){
//             console.log(error)
//             return Promise.reject(error);
//         })    
//         start();
// }

// Gắn sự kiện click vào liên kết
myLink.addEventListener('click', function(event) {
  event.preventDefault(); // Ngăn chặn hành vi mặc định của liên kết
  window.location.href = 'shop-grid.html';
  // Thực hiện hành động JavaScript tại đây
  console.log('Liên kết đã được nhấp');
  // ...
});