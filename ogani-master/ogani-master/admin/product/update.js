var categoryApi = 'http://localhost:8088/category/getAll';
var updateApi = 'http://localhost:8088/product/update?id';
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

function update(id){
    
}
