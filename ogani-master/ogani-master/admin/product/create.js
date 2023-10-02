var craeteProductApi = 'http://localhost:8088/product/create';

function start1() {
    getCategory(function (res) {
    //   options(res?.data || []);
      searchCategory(res?.data || []);
    });
  }
start1();

function getCategory(callback) {
  var url = 'http://localhost:8088/category/getAll';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(callback);
}

function searchCategory(data){
    var searchCategory = document.querySelector('#search-category');
    var htmls =`<option value="" selected>Tất cả</option>`+ data.map(function(elem){
        return `<option value="${elem.id}">${elem.name}</option>`;
    }).join('');
    searchCategory.innerHTML = htmls;
    
  }

var code = document.getElementById('product-code');
var name1 = document.getElementById('product-name');
var image = document.getElementById('product-image');
var price = document.getElementById('product-price');
var quantily = document.getElementById('product-quantily');
var content = document.getElementById('product-description');
var idCategory = document.getElementById('search-category');

function add(){
    document.getElementById('product-form').addEventListener('submit', function(event) {
      event.preventDefault(); // Ngăn chặn chuyển hướng trang sau khi submit
      var formData = new FormData();
      formData.append('code',code.value);
      formData.append('name',name1.value);
      formData.append('image',image.files[0]);
      formData.append('price',price.value);
      formData.append('quantity',quantily.value);
      formData.append('content',content.value);
      formData.append('categoryId',idCategory.value);
      fetch(craeteProductApi, {
        method: 'POST',
        body: formData
      })
        .then(function(response) {
          if (response.ok) {
            // Xử lý thành công
            console.log('Dữ liệu đã được gửi thành công!');
          } else {
            // Xử lý lỗi
            console.error('Gửi dữ liệu thất bại!');
          }
          window.location.replace('./product.html');
        //   start();
        })
        .catch(function(error) {
          // Xử lý lỗi mạng
          console.error('Lỗi kết nối: ' + error.message);
        });
    });
  }
