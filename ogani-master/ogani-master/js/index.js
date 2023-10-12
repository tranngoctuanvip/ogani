var names = localStorage.getItem('name');
console.log(names);
var token = localStorage.getItem('token');
var logins = document.getElementById('login');
var logoutButton = document.getElementById('logout');

function login(){
    if(names){
        logins.innerHTML = `<i class="fa fa-user"> Xin chào, ${names}`;
        logoutButton.style.display = 'inline';
    }
    else{
        logins.innerHTML = `<i class="fa fa-user"> Login`;
        logoutButton.style.display = 'none';
    }
}
login();

function logout() {
    localStorage.removeItem('name'); // Xóa username từ Local Storage
    names = null; // Đặt giá trị username thành null
    localStorage.removeItem('token');
    login(); // Cập nhật giao diện người dùng
    location.reload();
  }
  
  logoutButton.addEventListener('click', logout); // Gắn sự kiện click cho nút đăng xuất