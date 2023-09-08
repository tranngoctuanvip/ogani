var blogGetallApi = 'http://localhost:8088/blog/getAll';
function start(){
    getBlog(function(res){
        getAllBlog(res?.data || [])
    });
}
start();

function getBlog(callback){
    fetch(blogGetallApi)
    .then(function(response){
        return response.json();
    }).then(callback);
}

function getAllBlog(data){
    var getBlog = document.querySelector('#getBlog');
    var htmls = data.map(function(elem){
        return `<div class="col-lg-6 col-md-6 col-sm-6">
        <div class="blog__item">
            <div class="blog__item__pic">
                <img src="${elem.image}" alt="">
            </div>
            <div class="blog__item__text">
                <ul>
                    <li><i class="fa fa-calendar-o"></i>${elem.createAt}</li>
                    <li><i class="fa fa-comment-o"></i> 5</li>
                </ul>
                <h5><a href="#">${elem.title}</a></h5>
                <p>${elem.content}</p>
                <a href="#" class="blog__btn">READ MORE <span class="arrow_right"></span></a>
            </div>
        </div>
    </div>`
    });
    getBlog.innerHTML = htmls.join('');
}