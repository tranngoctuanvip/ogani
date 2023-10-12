var topRateProductApi = 'http://localhost:8088/product/topRatedProduct';
 var token = localStorage.getItem('token');

function start(){
    getTopRatedProduct(function(res){
        topRatedProduct(res?.data || [])
    })
}

start();

var unpaidApi = 'http://localhost:8088/cart/unpaid';
var token = localStorage.getItem('token');
function unpaids(){
    unpaid(function(res){
        selectUnpaid(res?.data || [])
    })
}
unpaids();

function unpaid(callback){
    if(token){
        fetch(unpaidApi,{
            headers: {'Authorization' : 'Bearer ' + token }
        })
        .then(function(response){
            return response.json();
        })
        .then(callback)
    }
   
}

function selectUnpaid(data){
    var selectUnpaids = document.querySelector('#unpaid');
    var htmls = data.map(function(elem){
        return `<i class="fa fa-shopping-bag"></i> <span>${elem.unpaid}</span>`;
    }).join('');
    selectUnpaids.innerHTML = htmls;
}

function getTopRatedProduct(callback){
    fetch(topRateProductApi)
    .then(function(response){
        return response.json();
    })
    .then(callback);
}

function topRatedProduct(data){
    var relatedProduct = document.querySelector('#related-product');
    var htmls = data.map(function(elem){
        return ` <div class="col-lg-3 col-md-4 col-sm-6">
        <div class="product__item" style="width: 250px; flex-wrap: wrap;">
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
    </div>`;
    }).join('');
    relatedProduct.innerHTML = htmls;
}