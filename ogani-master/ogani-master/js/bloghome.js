var selectTop3Api = 'http://localhost:8088/blog/selectTop3';

function start(){
    selectTop3(function(res){
        getSelectTop3(res?.data || []);
    })
}
start();

function selectTop3(callback){
    fetch(selectTop3Api)
    .then(function(response){
        return response.json();
    }).then(callback);
}

function getSelectTop3(data){
    var selectTop3 = document.querySelector('#selectTop3');
    var htmls = data.map(function(elem){
        return `<div class="col-lg-4 col-md-4 col-sm-6">
        <div class="blog__item">
            <div class="blog__item__pic">
                <img src="${elem.image}" alt="">
            </div>
            <div class="blog__item__text">
                <ul>
                    <li><i class="fa fa-calendar-o"></i>${elem.create_at}</li>
                    <li><i class="fa fa-comment-o"></i> 5</li>
                </ul>
                <h5><a href="#">${elem.title}</a></h5>
                <p>${elem.content}</p>
            </div>
        </div>
    </div>`
    });
    selectTop3.innerHTML = htmls.join('');
}