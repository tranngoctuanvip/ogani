var craeteProductApi = 'http://localhost:8088/product/create';
var productAPi = 'http://localhost:8088/product/getProduct';
var deletedAPi = 'http://localhost:8088/product/delete';
const curentSize = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 0; // Trang hiện tại
var sortType = 'desc';
var sortBy = 'id'; 

var token = localStorage.getItem('token');
var role = localStorage.getItem('role');

var buttonAdd = document.getElementById('button-btn');
var deletebutton = document.getElementById('delete-button');
var searchButton = document.getElementById('search-button');

function start(){
    getProduct(function(res){
      getData(res?.data || []);
      const total = res.total;
      listPage(currentPage,curentSize,total);
  })
}
start();



function start1() {
    getCategory(function (res) {
      // options(res?.data || []);
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

function getProduct(callback){
  if(token){
    var searchName = document.getElementById('search-name');
    var searchCategory = document.getElementById('search-category');
      const url = `${productAPi}?page=${currentPage}&size=${curentSize}&sortType=${sortType}&sortBy=${sortBy}&name=${searchName.value}&categoryId=${searchCategory.value}`;
      fetch(url,{
        method: 'GET',
        headers: {'Authorization' : 'Bearer ' + token}
      })
      .then(function(response){
          return response.json();
      })
      .then(callback);
  }
  else {
    window.location.href = "http://127.0.0.1:5500/ogani-master/admin/login/login.html";
  }
}

var deleteId = document.getElementById('deleteId');
function checkButton(){
  if(role.includes('STAFF') || role.includes('SHIPPER')){
    deleteId.style.display = 'none';
  }
}

checkButton();

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
                <button id="openBtn"><a href="./update.html?id=${elem.id}" style="text-decoration: none;"><i class="fas fa-edit" style="color: #020412;"></i></a></button>
                <button id="deleteId" onclick="return deleted(${elem.id})"><i class="fas fa-trash-alt"></i></button>
            </div>
        </td></tr>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
    // Thêm sự kiện lắng nghe click cho các nút Sửa
  // data.forEach(function (elem) {
  //   var updateButton = document.getElementById(`updateButton-${elem.id}`);
  //   updateButton.addEventListener('click', function () {
  //     redirectToUpdatePage(elem.id);
  //   });
  // })
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
  if(token){
    var confirmed = confirm('Bạn có chắc chắn muốn xóa không?');
    // Kiểm tra xem người dùng đã xác nhận hay không
    if (confirmed) {
      var url = `${deletedAPi}?id=` +id;
      fetch(url,{
        method: 'POST',
        headers: {'Authorization' : 'Bearer ' + token}
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
  else{
    alert('Bạn vui lòng đăng nhập');
  }
}