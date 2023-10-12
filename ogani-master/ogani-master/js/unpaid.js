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