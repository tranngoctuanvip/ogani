var productApiGetall = 'http://localhost:8088/product/getAll';
var lastestProductApi = 'http://localhost:8088/product/lastestProduct';


function start(){
    getProduct(function(res) {
        renderProduct(res?.data || [])
        selectProduct(res?.data || [])
    });
}
start();

function startLastestProduct(){
    getlastestProduct(function(res){
        lastestProduct(res?.data || [])
    });
}
startLastestProduct();

function createCart(id){    
    // console.log("id");
    // var element = document.getElementById(id);
    alert('Đã thêm vào giỏ hàng');
    var cartCreateApi = 'http://localhost:8088/cart/create?productId=' + id;
    fetch(cartCreateApi, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
      // Xử lý dữ liệu từ API
      console.log(data);
    })
    .catch(error => {
      // Xử lý lỗi
      console.error(error);
})
} 

function getProduct(callback){
    fetch(productApiGetall)
    .then(function(response){
        return response.json();
    }).then(callback);
}

function getlastestProduct(callback){
    fetch(lastestProductApi)
    .then(function(response){
        return response.json();
    }).then(callback);
}

function lastestProduct(data){
    var lastestProduct1 = document.querySelector('#lastest-product');
    var htmls = data.map(function(elem){
        return `<a href="#" class="latest-product__item">
        <div class="latest-product__item__pic">
            <img src="${elem.image}" alt="">
        </div>
        <div class="latest-product__item__text">
            <h6>${elem.name}</h6>
            <span>${elem.price}.00$</span>
        </div>
    </a>`
    });
    lastestProduct1.innerHTML = htmls.join("");
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



function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();

