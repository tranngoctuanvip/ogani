var categoryAPi = 'http://localhost:8088/category/getAll';
var craeteProductApi = 'http://localhost:8088/product/create';
var productAPi = 'http://localhost:8088/product/getProduct';
var deletedAPi = 'http://localhost:8088/product/delete';
const productsPerPage = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 0; // Trang hiện tại
function start(){
    getProduct(function(res){
        getData(res?.data || [])
    })
}
start();
var code = document.getElementById('product-code');
var name1 = document.getElementById('product-name');
var image = document.getElementById('product-image');
var price = document.getElementById('product-price');
var quantily = document.getElementById('product-quantily');
var content = document.getElementById('product-description');
var idCategory = document.getElementById('category');
var buttonAdd = document.getElementById('button-btn');
var deletebutton = document.getElementById('delete-button');


function start1() {
  getCtegory(function (res) {
    options(res?.data || []);
  });
}
start1();

function getCtegory(callback) {
  fetch(categoryAPi)
    .then(function (response) {
      return response.json();
    })
    .then(callback);
}

function options(data) {
  var options = document.querySelector("#category");
  var htmls = data
    .map(function (elem) {
      return ` <option value="${elem.id}">${elem.name}</option>`;
    })
    .join("");
  options.innerHTML = htmls;
}

function toggleForm() {
  var form = document.getElementById("product-form");
  if (form.style.display === "none") {
    form.style.display = "block";
  } else {
    form.style.display = "none";
  }
}
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
        start();
      })
      .catch(function(error) {
        // Xử lý lỗi mạng
        console.error('Lỗi kết nối: ' + error.message);
      });
  });
}


function getProduct(callback){
    const url = `${productAPi}?page=${currentPage}&size=${productsPerPage}&sorType=desc&sortBy=id`;
    fetch(url)
    .then(function(response){
        return response.json();
    })
    .then(callback);
}

function getData(data){
    var listAllProduct1 = document.querySelector('#product-table');
    var htmls = data.map(function(elem){
        return`<tr><td>${elem.id}</td>
        <td>${elem.name}</td>
        <td><img src="${elem.image}" style="width: 150px; height: 100px; text-align:center" alt="Ảnh" /></td>
        <td>${elem.price}</td>
        <td>
            <div class="actions">
                <button id="${elem.id}" onclick ="return update()"><a href="./update.html" style="text-decoration: none;">Sửa</a></button>
                <button id="${elem.id}" onclick="return deleted(this.id)">Xóa</button>
            </div>
        </td></tr>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
}


function update(){
    // var formupdate = document.querySelector('#updateForm');
    // fetch()
  
}


function deleted(id){
  var confirmed = confirm('Bạn có chắc chắn muốn xóa không?');
  // Kiểm tra xem người dùng đã xác nhận hay không
  if (confirmed) {
    var url = `${deletedAPi}?id=` +id;
    fetch(url,{
      method: 'POST'
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
    alert('Đã xóa thành công!');
  }
}
