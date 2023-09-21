var categoryApi = "http://localhost:8088/category/getAll";
var findByIdApi = "http://localhost:8088/product/findById?id=";
// function category(){
//     getAllCategory(function(res){
//         option(res?.data || [])
//     })
// }
// category();

// function getAllCategory(callback){
//     fetch(categoryApi)
//     .then(function(response){
//         return response.json();
//     })
//     .then(callback)
// }

// function option(data){
//     var category = document.querySelector('#category');
//     var htmls = data.map(function(elem){
//         return `<option value="${elem.id}">${elem.name}</option>`;
//     }).join('');
//     category.innerHTML = htmls;
// }
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
