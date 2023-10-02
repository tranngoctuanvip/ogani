var productApiGetall = 'http://localhost:8088/product/getAll';
var lastestProductApi = 'http://localhost:8088/product/lastestProduct';
var unpaidApi = 'http://localhost:8088/cart/unpaid';
var topRatedProductApi = 'http://localhost:8088/product/topRatedProduct';
var token = localStorage.getItem('token');

function start1(){
    getProduct(function(res) {
        renderProduct(res?.data || [])
        selectProduct(res?.data || [])
    });
    unpaid(function(res){
        selectUnpaid(res?.data || [])
    });
}
start1();


function createCart(id){   
    var headers ={
        'Authorization' : 'Bearer '+ token
    } 
    if(token){
        alert('Đã thêm vào giỏ hàng');
        var cartCreateApi = 'http://localhost:8088/cart/create?productId=' + id;
        fetch(cartCreateApi, {
            method: 'POST',
            headers: headers
        })
        .then(function(response){
            return response.json();
        }).then(data =>{
            console.log(data);
            start1();
        })
        .catch(error => {
          // Xử lý lỗi
          console.error(error);
    })
    }
    else{
        alert('Vui lòng đăng nhập để thêm vào giỏ hàng');
    }  
} 

function getProduct(callback){
    fetch(productApiGetall)
    .then(function(response){
        return response.json();
    }).then(callback);
}

function renderProduct(data){
    var listProduct = document.querySelector('#list-product');
    var htmls = data.map(function(elem){
       return `<div class="col-lg-3">
       <div class="categories__item set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
           <h5><a href="#">${elem.name}</a></h5>
       </div>
   </div>`
}).join('');
    listProduct.innerHTML = htmls;
}


function selectProduct(data){
    var selectProduct = document.querySelector('#select-product');
    var htmls = data.map(function(elem){
    return `<div class="featured__item">
    <div class="featured__item__pic set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
        <ul class="featured__item__pic__hover">
            <li><a href="#"><i class="fa fa-heart"></i></a></li>
            <li><a href="#"><i class="fa fa-retweet"></i></a></li>
            <div id="successMessage"></div>
            <li><a id="${elem.id}" href="#" onclick="createCart(this.id)"><i class="fa fa-shopping-cart"></i></a></li>
        </ul>
    </div>
    <div  class="featured__item__text">
        <h6><a href="#">${elem.name}</a></h6>
        <h5>${elem.price}.00$</h5>
    </div>
</div>`
    });
    selectProduct.innerHTML = htmls.join("");
}

function unpaid(callback){
    fetch(unpaidApi)
    .then(function(response){
        return response.json();
    })
    .then(callback)
}

function selectUnpaid(data){
    var selectUnpaids = document.querySelector('#unpaid');
    var htmls = data.map(function(elem){
        return `<i class="fa fa-shopping-bag"></i> <span>${elem.unpaid}</span>`;
    }).join('');
    selectUnpaids.innerHTML = htmls;
}

function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();


