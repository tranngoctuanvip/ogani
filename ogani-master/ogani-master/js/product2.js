var productApiGetall = 'http://localhost:8088/product/getAll';
var apiUrl  ='http://localhost:8088/product/getProduct';
const productsPerPage = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 1; // Trang hiện tại
var sortType = 'desc';
var sortBy = 'id'
function start(){
    getProduct(function(res) {
        getProducts(res?.data || [])
    });
    // getProductPage(function(res){

    // })
    updatePagination();
}
start();

function getProduct(callback){
    const url = `${apiUrl}?page=${currentPage}&size=${productsPerPage}&sortType=&sortBy`;
    fetch(url)
    .then(function(response){
        return response.json();
    }).then(callback);

}

// function listAllProduct(data){
//     var listAllProduct1 = document.querySelector('#listAll-product');
//     var htmls = data.map(function(elem){
//         return `<div class="col-lg-4 col-md-6 col-sm-6">
//         <div class="product__item">
//             <div class="product__item__pic set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
//                 <ul class="product__item__pic__hover">
//                     <li><a href="#"><i class="fa fa-heart"></i></a></li>
//                     <li><a href="#"><i class="fa fa-retweet"></i></a></li>
//                     <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
//                 </ul>
//             </div>
//             <div class="product__item__text">
//                 <h6><a href="#">${elem.name}</a></h6>
//                 <h5>${elem.price}.00$</h5>
//             </div>
//         </div>
//     </div>`
//     }).join('');
//     listAllProduct1.innerHTML = htmls;
// }
function getProducts(data){
         var listAllProduct1 = document.querySelector('#listAll-product');
    var htmls = data.map(function(elem){
        return `<div class="col-lg-4 col-md-6 col-sm-6">
        <div class="product__item">
            <div class="product__item__pic set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
                <ul class="product__item__pic__hover">
                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
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
    updatePagination();
}
 
  // Hàm cập nhật phân trang
  function updatePagination() {
    const pagination = document.querySelector('.product__pagination');
    pagination.innerHTML = '';
  
    // Gửi yêu cầu HTTP HEAD để lấy thông tin về số lượng tổng sản phẩm
    fetch(apiUrl, { method: 'HEAD' })
      .then(response => {
        const totalProducts = response.headers.get('X-Total-Count');
        const totalPages = Math.ceil(totalProducts / productsPerPage);
  
        for (let i = 1; i <= totalPages; i++) {
          const pageLink = document.createElement('a');
          pageLink.href = '#';
          pageLink.textContent = i;
          if (i === currentPage) {
            pageLink.classList.add('active');
          }
  
          pageLink.addEventListener('click', () => {
            currentPage = i;
            getProducts();
          });
  
          pagination.appendChild(pageLink);
        }
      })
      .catch(error => {
        console.error('Error:', error);
      });
  }
  // Gọi hàm để lấy sản phẩm và cập nhật phân trang ban đầu
//   getProducts();


function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();
