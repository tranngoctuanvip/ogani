var productApiGetall = 'http://localhost:8088/product/getAll';
var apiUrl  ='http://localhost:8088/product/getProduct';
const productsPerPage = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 0; // Trang hiện tại
var sortType = 'desc';
var sortBy = 'id'
var token  = localStorage.getItem('token');
function start(){
    getProduct(function(res) {
      getProducts1(res?.data || []);
      const total = res.total;
      listPage(currentPage,productsPerPage,total);
      // changePage();
    });
    // getSale(function(res){
    //   sale(res?.data || [])
    // })
    
}
start();

function getProduct(callback){
    const url = `${apiUrl}?page=${currentPage}&size=${productsPerPage}&sortType=${sortType}&sortBy=${sortBy}`;
    fetch(url)
    .then(function(response){
        return response.json();
    })
    .then(callback);
}

function getSale(callback){
  fetch(productApiGetall)
  .then(function(response){
    return response.json();
  }).then(callback)
}

function sale(data){
  var sales = document.querySelectorAll('.product__discount__slider.owl-carousel');
  var htmls= data.map(function(elem){
    return `  <div class="col-lg-4">
    <div class="product__discount__item">
        <div class="product__discount__item__pic set-bg"
            data-setbg="${elem.image}">
            <div class="product__discount__percent">-20%</div>
            <ul class="product__item__pic__hover">
                <li><a href="#"><i class="fa fa-heart"></i></a></li>
                <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
            </ul>
        </div>
        <div class="product__discount__item__text">
            <span>${elem.name}</span>
            <h5><a href="#">${elem.name}</a></h5>
            <div class="product__item__price">${elem.price}<span>$36.00</span></div>
        </div>
    </div>
</div>`
  }).join('');
  sales.innerHTML = htmls;
}

function createCart(id){
  var headers = {
    'Authorization': 'Bearer ' + token
  };    
  if(token){
    alert('Đã thêm vào giỏ hàng');
    var cartCreateApi = 'http://localhost:8088/cart/create?productId=' + id;
    fetch(cartCreateApi, {
        method: 'POST',
        headers: headers
    })
    .then(response => response.json())
    // .then(data => {
    //   // Xử lý dữ liệu từ API
    //   console.log(data);
      
    // })
    .catch(error => {
      // Xử lý lỗi
      console.error(error);
  })
  }
  else{
    alert('Bạn chưa đăng nhập');
  }
} 


function getProducts1(data){
         var listAllProduct1 = document.querySelector('#listAll-product');
    var htmls = data.map(function(elem){
        return `<div class="col-lg-4 col-md-6 col-sm-6">
        <div class="product__item">
            <div class="product__item__pic set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
                <ul class="product__item__pic__hover">
                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                    <li><a id="${elem.id}" href="#" onclick="createCart(this.id)" ><i class="fa fa-shopping-cart"></i></a></li>
                </ul>
            </div>
            <div class="product__item__text">
                <h6><a href="#">${elem.name}</a></h6>
                <h5>${elem.price}.00$</h5>
            </div>
        </div>
    </div>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
}



  // Hàm cập nhật phân trang
  function listPage(currentPage, pageSize, total) {
    const pageCount = Math.ceil(total / pageSize);
    const paginationContainer = document.querySelector('#paging');
    paginationContainer.innerHTML = '';
  
    if (currentPage >= 0) {
      createPageLink('PREV', currentPage - 1);
    }
  
    for (let i = 1; i <= pageCount; i++) {
      createPageLink(i, i);
    }
  
    if (currentPage < pageCount) {
      createPageLink('NEXT', currentPage + 1);
    }
  
    function createPageLink(text, page) {
      const pageLink = document.createElement('li');
      pageLink.classList.add('page-link');
      const link = document.createElement('a');
      link.innerText = text;
      link.href = '#';
      link.setAttribute('onclick', `changePage(${page})`);
      if (currentPage === page) {
        pageLink.classList.add('active');
      }
      pageLink.appendChild(link);
      paginationContainer.appendChild(pageLink);
    }
  }
  
  function changePage(page) {
    currentPage = page;
    start();
  }



function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();
