var craeteProductApi = 'http://localhost:8088/product/create';
var productAPi = 'http://localhost:8088/product/getProduct';

var deletedAPi = 'http://localhost:8088/product/delete';
const curentSize = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 0; // Trang hiện tại
var sortType = 'desc';
var sortBy = 'id'; 
var isStart1Running = true;

function start(){
    getProduct(function(res){
      getData(res?.data || []);
      const total = res.total;
      listPage(currentPage,curentSize,total);
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
var searchButton = document.getElementById('search-button');

function start1() {
    getCategory(function (res) {
      options(res?.data || []);
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


function options(data) {
  var options = document.querySelector("#category");
  var htmls =`<option value="all" selected>Tất cả</option>`+ data
    .map(function (elem) {
      return `<option value="${elem.id}">${elem.name}</option>`;
    }).join("");
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
  var searchName = document.getElementById('search-name');
  var searchCategory = document.getElementById('search-category');
    const url = `${productAPi}?page=${currentPage}&size=${curentSize}&sortType=${sortType}&sortBy=${sortBy}&name=${searchName.value}&categoryId=${searchCategory.value}`;
    fetch(url)
    .then(function(response){
        return response.json();
    })
    .then(callback);
}

function getData(data){
    var listAllProduct1 = document.querySelector('#product-table');
    var i = 1;
    var htmls = data.map(function(elem){
        return`<tr><td>${i++}</td>
        <td>${elem.name}</td>
        <td><img src="${elem.image}" style="width: 150px; height: 100px; text-align:center" alt="Ảnh" /></td>
        <td>${elem.price}</td>
        <td>
            <div class="actions">
                <button id="updateButton-${elem.id}">Sửa</button>
                <button id="${elem.id}" onclick="return deleted(this.id)">Xóa</button>
            </div>
        </td></tr>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
    // Thêm sự kiện lắng nghe click cho các nút Sửa
  data.forEach(function (elem) {
    var updateButton = document.getElementById(`updateButton-${elem.id}`);
    updateButton.addEventListener('click', function () {
      redirectToUpdatePage(elem.id);
    });
  })
}

function search() {
  searchButton.addEventListener('click', function () {
  getProduct(function (res) {
      getData(res?.data || []);
    });
  });
}


// Hàm cập nhật phân trang
function listPage(currentPage, pageSize, total) {
  const pageCount = Math.ceil(total / pageSize);
  const paginationContainer = document.querySelector('.pagination');
  paginationContainer.innerHTML = '';

  if (currentPage >= 1) {
    createPageLink('PREV', currentPage - 1);
  }

  for (let i = 1; i <= pageCount; i++) {
    createPageLink(i, i);
  }

  if (currentPage < pageCount) {
    createPageLink('NEXT', currentPage + 1);
  }

  function createPageLink(text, page) {
    const pageLink = document.createElement('button');
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





function redirectToUpdatePage(id) {
  var url = './update.html?'+id;
  window.location.href = url;
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
