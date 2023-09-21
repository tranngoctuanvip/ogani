let currentPage = 0; // Trang hiện tại
const pageSize = 6; // Kích thước trang

function start() {
  getBlog(currentPage, pageSize, 'desc', 'id', function(res) {
    const total = res.total;
    // const data = res.data || [];
    // getAllBlog(data);
    loadItem(res?.data || []);
    listPage(currentPage, pageSize, total);
  });
}
start();

function getBlog(page, size, sortType, sortBy, callback) {
  const url = `http://localhost:8088/blog/getBlog?page=${page}&size=${size}&sortType=${sortType}&sortBy=${sortBy}`;
  fetch(url)
    .then(function(response) {
      return response.json();
    })
    .then(function(data) {
      callback(data);
    })
    .catch(function(error) {
      console.log(error);
    });
}

function loadItem(data) {
  var list = document.querySelector('#getBlog'); 
  var htmls = data.map(function(elem) {
    return `<div class="col-lg-6 col-md-6 col-sm-6">
        <div class="blog__item">
            <div class="blog__item__pic">
                <img src="${elem.image}" alt="">
            </div>
            <div class="blog__item__text">
                <ul>
                    <li><i class="fa fa-calendar-o"></i>${elem.code}</li>
                    <li><i class="fa fa-comment-o"></i> 5</li>
                </ul>
                <h5><a href="#">${elem.title}</a></h5>
                <p>${elem.content}</p>
                <a href="#" class="blog__btn">READ MORE <span class="arrow_right"></span></a>
            </div>
        </div>
    </div>`;
  });
  list.innerHTML = htmls.join('');
}

function listPage(currentPage, pageSize, total) {
  const pageCount = Math.ceil(total / pageSize);
  const paginationContainer = document.querySelector('#pagination-container');
  paginationContainer.innerHTML = '';

  if (currentPage >= 0) {
    createPageLink('PREV', currentPage - 1);
  }

  for (let i = 1; i <= pageCount; i++) {
    createPageLink(i, i);
  }

  if (currentPage < pageCount) {
    createPageLink('NEXT', currentPage + 1);
  }

  function createPageLink(text, page) {
    const pageLink = document.createElement('li');
    pageLink.classList.add('page-link');
    const link = document.createElement('a');
    link.innerText = text;
    link.href = '#';
    link.setAttribute('onclick', `changePage(${page})`);
    if (currentPage === page) {
      pageLink.classList.add('active');
    }
    pageLink.appendChild(link);
    paginationContainer.appendChild(pageLink);
  }
}

function changePage(page) {
  currentPage = page;
  start();
}

// function getAllBlog(data) {
   
//   }