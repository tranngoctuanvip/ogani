var signinApi = 'http://localhost:8088/auth/signin';
var username1 = document.getElementById('username');
var password1 = document.getElementById('password');
var loginForm = document.getElementById('login-form');
var user = document.getElementById('login');
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
          }).then(function(response){
            return response.json();
          })
          .then(data =>{
              // Lưu thông tin token và ID vào Local Storage
              localStorage.setItem('token', data.data.token);
              localStorage.setItem('username',data.data.username);
              window.location.href = './index.html';
          })
            .catch(error => {
              console.log("Error:", error);
            })
    })
  }

