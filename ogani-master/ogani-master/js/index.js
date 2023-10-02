var username = localStorage.getItem('username');
var token = localStorage.getItem('token');
var logins = document.getElementById('login');
var logoutButton = document.getElementById('logout');

function login(){
    if(username){
        logins.innerHTML = `<i class="fa fa-user"> Xin chào, ${username}`;
        logoutButton.style.display = 'inline';
    }
    else{
        logins.innerHTML = `<i class="fa fa-user"> Login`;
        logoutButton.style.display = 'none';
    }
}
login();

function logout() {
    localStorage.removeItem('username'); // Xóa username từ Local Storage
    username = null; // Đặt giá trị username thành null
    localStorage.removeItem('token');
    login(); // Cập nhật giao diện người dùng
    location.reload();
  }
  
  logoutButton.addEventListener('click', logout); // Gắn sự kiện click cho nút đăng xuất