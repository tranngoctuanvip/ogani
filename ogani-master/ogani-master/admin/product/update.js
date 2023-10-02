var categoryApi = "http://localhost:8088/category/getAll";
var findByIdApi = "http://localhost:8088/product/findById?id=";
var updateProductApi = 'http://localhost:8088/product/update?id=';
function category() {
  getAllCategory(function (res) {
    option(res?.data || []);
  });
}
category();

function getAllCategory(callback) {
  fetch(categoryApi)
    .then(function (response) {
      return response.json();
    })
    .then(callback);
}

function option(data) {
  var category = document.querySelector("#search-category");
  var htmls = data
    .map(function (elem) {
      return `<option value="${elem.id}">${elem.name}</option>`;
    })
    .join("");
  category.innerHTML = htmls;
}

var queryString = window.location.search;
var params = new URLSearchParams(queryString);
var id = params.get("id");

var url1 = `${findByIdApi}${id}`;
fetch(url1, {
  method: "GET",
  headers: { "Content-Type": "application/json" },
})
  .then(function (response) {
    return response.json();
  })
  .then(function (data) {
    document.getElementById("product-code").value = data.data[0].code;
    document.getElementById("product-name").value = data.data[0].name;
    document.getElementById("search-category").value = data.data[0].category_id;
    document.getElementById("image").src = data.data[0].image;
    document.getElementById("product-price").value = data.data[0].price;
    document.getElementById("product-quantity").value = data.data[0].quantity;
    document.getElementById("product-description").value = data.data[0].content;
    // console.log(data.data);
    return data;
  })
  .catch((error) => {
    console.log(error);
  });

  var code = document.getElementById('product-code');
  var name1 = document.getElementById('product-name');
  var image = document.getElementById('product-image');
  var price = document.getElementById('product-price');
  var quantity = document.getElementById('product-quantity');
  var content = document.getElementById('product-description');
  var idCategory = document.getElementById('search-category');
  var img = document.getElementById('image');

function update(){
    document.getElementById('product-form').addEventListener('submit',function(event){
        event.preventDefault();
        var xacNhan = confirm("Bạn chắc chắn muốn tiếp tục và chỉnh sửa?");
        if (xacNhan){          
          var images = image.files[0];
          if(!images){
            images = img.src;
          }
            var formData = new FormData();
            formData.append('code',code.value);
            formData.append('name',name1.value);
            formData.append('image',images);
            formData.append('price',price.value);
            formData.append('quantity',quantity.value);
            formData.append('content',content.value);
            formData.append('categoryId',idCategory.value);      
            var url2 = `${updateProductApi}${id}`;
            fetch(url2,{
                method: 'POST',
                headers: { "Content-Type": "application/json" },
                body: formData
            }).then(function(response){
                    alert('Sửa sản phẩm thành công!');
                    window.location.replace('./product.html');
            }).catch(function(error) {
                // Xử lý lỗi mạng
                console.error('Lỗi kết nối: ' + error.message);
              });
        }
    })
}
    
