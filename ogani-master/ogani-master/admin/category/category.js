var categoryApi = 'http://localhost:8088/category/getNameAndCode';
var searchCode = document.getElementById('search-input');
var searchName = document.getElementById('search');
var searchButton = document.getElementById('search-button');
function start() {
  getCategory(function (res) {
      selectCategory(res?.data || []);
  });
}
start();



function search(){
    getCategory(function (res) {
        searchButton.addEventListener('click', function(){
            selectCategory(res?.data || []);
        })
    });
}


function getCategory(callback) {
var code = searchCode.value;
var name = searchName.value;
var url = categoryApi;
  if (code !== "" && name !== "") {
    url += `?code=${encodeURIComponent(code)}&name=${encodeURIComponent(name)}`;
  } else if (code !== "") {
    url += `?code=${encodeURIComponent(code)}`;
  } else if (name !== "") {
    url += `?name=${encodeURIComponent(name)}`;
  } 
  else if (code == "" && name == "") {
    url = categoryApi;
  }
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(callback);
}

function selectCategory(data) {
  var category = document.querySelector("#category");
  var htmls = data
    .map(function (elem) {
      var statusValue = elem.status == 1 ? "Hoạt động" : "Không hoạt động";
      return `<tr>
        <td style="text-align: center;">${elem.id}</td>
        <td>${elem.code}</td>
        <td>${elem.name}</td>
        <td>${statusValue}</td>
        <td></td>
        <td>
        <div class="actions">
            <button id="edit-button" onlick="">Sửa</button>
            <button id="delete-button">Xóa</button>
        </div>
    </tr>`;
    })
    .join("");
  category.innerHTML = htmls;
}
