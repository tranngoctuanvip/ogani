var categoryApi = "http://localhost:8088/category/getAll";
var findByIdApi = "http://localhost:8088/product/findById?id=";
function category(){
    getAllCategory(function(res){
        option(res?.data || [])
    })
}
category();

function getAllCategory(callback){
    fetch(categoryApi)
    .then(function(response){
        return response.json();
    })
    .then(callback)
}

function option(data){
    var category = document.querySelector('#category');
    var htmls = data.map(function(elem){
        return `<option value="${elem.id}">${elem.name}</option>`;
    }).join('');
    category.innerHTML = htmls;
}
var urls = window.location.search;
var id = urls.split("?")[1];
async function update() {

  var url1 = `${findByIdApi}${id}`;
  var response = await fetch(url1);
  var data = await response.json();
//   var dataArray = Object.values(data);
  //   var code1 = document.getElementById('code');
  document.getElementById('name').value = data.name;
//   document.getElementById('category') = data.category_id;
//   document.getElementById('image1') = data.image;
  document.getElementById('price').value = data.price;
  document.getElementById('quantity').value = data.quantity;
  document.getElementById('content').value = data.content; 
  console.log(data);
  return data;
}  update();


document.addEventListener("DOMContentLoaded", function() {
            var modal = document.createElement("div");
            modal.classList.add("modal");
            
            var modalContent = document.createElement("div");
            modalContent.classList.add("modal-content");
            
            var closeBtn = document.createElement("span");
            closeBtn.classList.add("close");
            closeBtn.innerHTML = "&times;";
            
            var form = document.createElement("form");
            form.id = "myForm";
            form.method = "post";
            
            var nameLabel = document.createElement("label");
            nameLabel.textContent = "Tên sản phẩm:";
            
            var nameInput = document.createElement("input");
            nameInput.type = "text";
            nameInput.name = "name";
            nameInput.required = true;
            
            var categoryLabel = document.createElement("label");
            categoryLabel.textContent = "Danh mục:";
            
            var categoryInput = document.createElement("select");
            categoryInput.options= 'category';
            categoryInput.required = true;
            
            var imageLabel = document.createElement('Hình ảnh');
            imageLabel.type = 'file';

            var priceLabel = document.createElement('Giá');
            priceLabel.type = 'text';



            var submitBtn = document.createElement("input");
            submitBtn.type = "submit";
            submitBtn.value = "Submit";
            
            form.appendChild(nameLabel);
            form.appendChild(nameInput);
            form.appendChild(emailLabel);
            form.appendChild(emailInput);
            form.appendChild(submitBtn);
            
            modalContent.appendChild(closeBtn);
            modalContent.appendChild(form);
            
            modal.appendChild(modalContent);
            
            document.body.appendChild(modal);
            
            // Hiển thị cửa sổ form khi nhấn nút "Open Form"
            var openBtn = document.getElementById("updateButton");
            openBtn.addEventListener("click", function() {
                modal.style.display = "block";
            });
            
            // Đóng cửa sổ form khi nhấn nút "Close" hoặc click bên ngoài form
            closeBtn.addEventListener("click", function() {
                modal.style.display = "none";
            });
            
            window.addEventListener("click", function(event) {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            })
          })