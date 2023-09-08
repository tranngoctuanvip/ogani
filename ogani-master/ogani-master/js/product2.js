var productApiGetall = 'http://localhost:8088/product/getAll';
function start(){
    getProduct(function(res) {
        listAllProduct(res?.data || [])
    });
    getProductPage(function(res){

    })
}
start();

function getProduct(callback){
    fetch(productApiGetall)
    .then(function(response){
        return response.json();
    }).then(callback);

}

function listAllProduct(data){
    var listAllProduct1 = document.querySelector('#listAll-product');
    var htmls = data.map(function(elem){
        return `<div class="col-lg-4 col-md-6 col-sm-6">
        <div class="product__item">
            <div class="product__item__pic set-bg" data-setbg="${elem.image}" style="background-image: url(${elem.image})">
                <ul class="product__item__pic__hover">
                    <li><a href="#"><i class="fa fa-heart"></i></a></li>
                    <li><a href="#"><i class="fa fa-retweet"></i></a></li>
                    <li><a href="#"><i class="fa fa-shopping-cart"></i></a></li>
                </ul>
            </div>
            <div class="product__item__text">
                <h6><a href="#">${elem.name}</a></h6>
                <h5>${elem.price}.00$</h5>
            </div>
        </div>
    </div>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
}




function performSignIn() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('Accept', 'application/json');
    headers.append('Access-Control-Allow-Origin','http://localhost:8088');
}
performSignIn();
