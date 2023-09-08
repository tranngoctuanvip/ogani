
var categoryApiGetall = 'http://localhost:8088/category/getAll';
function start(){
    getCategory(function(res) {
        renderCategory(res?.data || [])
    });
}
start();

function getCategory(callback){
    fetch(categoryApiGetall)
    .then(function(response){
        return response.json();
    }).then(callback);

}

function renderCategory(data){
    var listCategory = document.querySelector('#list-category');
    var htmls = data.map(function(elem){
        return `
        <li><a href="#">${elem.name}</a></li>`;
    }).join("");
    listCategory.innerHTML = htmls;
}
function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    // headers.append('Access-Control-Allow-Origin','application/json');
    headers.append('Accept', 'application/json');
    // headers.append('Authorization', 'Basic ' + base64.encode(username + ":" +  password));
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();
