var getUerApi = 'http://localhost:8088/auth/getUser';

var currentPage = 0;
const curentSize = 5;
var sortType = 'desc';
var sortBy = 'id';
var names = document.getElementById('search-name');
var searchButton = document.getElementById('search-button');
function start(){
    getUser(function(res){
        selectUser(res?.data || [])
    })
}

start();

function search(){
    searchButton.addEventListener('click',function(event){
        event.preventDefault();
        getUser(function(res){
            selectUser(res?.data || [])
        });
    })
}

function getUser(callback){
    var url = `${getUerApi}?page=${currentPage}&size=${curentSize}&sortType=${sortType}&sortBy=${sortBy}`;
    if(names.value === ''){
        fetch(url)
        .then(function(response){
            return response.json();
        }).then(callback)
    }
    else{
         url = `${getUerApi}?page=${currentPage}&size=${curentSize}&sortType=${sortType}&sortBy=${sortBy}&name=${names.value}`;
        fetch(url)
        .then(function(response){
            return response.json();
        }).then(callback)
    }
}

function selectUser(data) {
    var userTable = document.querySelector('#user-table');
    var k = 1;
    var htmls = data.map(function(elem) {
      var status = elem.status ? 'Hoạt động' : 'Không hoạt động';
      // Tạo chuỗi roleString
      var roleString = "";
      for (var i = 0; i < elem.roleDtos.length; i++) {
        var roleDto = elem.roleDtos[i];
        var role = roleDto.role;
        
        // Nếu không phải role đầu tiên, thêm dấu phẩy và khoảng trắng vào chuỗi
        if (i !== 0) {
          roleString += ", ";
        }
  
        // Thêm role vào chuỗi
        roleString += role;
      }
      return `<tr>
        <td>${k++}</td>
        <td>${elem.username}</td>
        <td>${elem.name}</td>
        <td>${roleString}</td>
        <td>${status}</td>
        <td>
          <div class="actions">
            <button id="updateButton-${elem.id}">Sửa</button>
            <button id="${elem.id}" onclick="return deleted(this.id)">Xóa</button>
          </div>
        </td>
      </tr>`;
    }).join('');
    userTable.innerHTML = htmls;
  }
function toggleForm(){
    window.location.replace('./create.html');
}