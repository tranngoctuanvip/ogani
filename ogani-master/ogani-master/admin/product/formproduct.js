
var categoryAPi = 'http://localhost:8088/category/getAll';
var craeteProductApi = 'button-btn';
function start(){
    getCtegory(function(res){
        options(res?.data || [])
    });
}
start();

function getCtegory(callback){
    fetch(categoryAPi,{
        method: 'GET'
    })
    .then(function(response){
            return response.json();
    })
    .then(callback);
}
function options(data){
    var options = document.querySelector('#category');
    var htmls = data.map(function(elem){
        return ` <option value="${elem.id}">${elem.name}</option>`;
    }).join('');
    options.innerHTML = htmls;
}

function toggleForm() {
    var form = document.getElementById('product-form');
    if (form.style.display === 'none') {
      form.style.display = 'block';
    } else {
      form.style.display = 'none';
    }
}

document.getElementById('button-btn').addEventListener('click', function(event) {
    event.preventDefault(); // Ngăn chặn chuyển hướng trang sau khi submit
    var code = document.getElementById('product-code');
    var name =  document.getElementById('product-name');
    var image = document.getElementById('product-image');
    var price = document.getElementById('product-price');
    var quantily = document.getElementById('product-quantily');
    var content = document.getElementById('product-description');
    var idCategory = document.getElementById('category');
    // var form = event.target;
    var formData = new FormData();
    formData.append('code', code.value);
    formData.append('name', name.value);
    formData.append('image', image.value);
    formData.append('price', price.value);
    formData.append('quantity', quantily.value);
    formData.append('content', content.value);
    formData.append('categoryId', idCategory.value);

    fetch(craeteProductApi, {
      method: 'POST',
      body: formData
    }).then(function(response) {
      if (response.ok) {
        // Xử lý thành công
        console.log('Dữ liệu đã được gửi thành công!');
      } else {
        // Xử lý lỗi
        console.error('Gửi dữ liệu thất bại!');
      }
    }).catch(function(error) {
      // Xử lý lỗi mạng
      console.error('Lỗi kết nối: ' + error.message);
    });
});
  