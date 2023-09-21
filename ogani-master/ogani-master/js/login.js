var signinApi = 'http://localhost:8088/auth/signin';
var username1 = document.getElementById('username');
var password1 = document.getElementById('password');
var loginForm = document.getElementById('login-form');
var user = document.getElementById('login');
var authContainer = document.querySelector('.header__top__right__auth');

function signin() {
    loginForm.addEventListener('click',function(event){
      event.preventDefault();
        var usernameValue = username1.value.trim();
        var passwordValue = password1.value.trim();
        var datalogin = {
            username: usernameValue,
            password: passwordValue
          };
          fetch(signinApi, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(datalogin)
          })
          .then(response =>{
            if (response.ok) {
              return response.json();
            }
            else {
              throw new Error('Đăng nhập thất bại');
            }
          })
          .then(data =>{
              // Lưu thông tin token và ID vào Local Storage
              sessionStorage.setItem('token', data.token);
              // sessionStorage.setItem('username',);
              window.location.href = './index.html';
          })
            .catch(error => {
              console.log("Error:", error);
            })
    })
  }

