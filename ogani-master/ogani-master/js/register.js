var signupApi = 'http://localhost:8088/auth/signup';

var username = document.getElementById('username');
var email = document.getElementById('email');
var names = document.getElementById('name');
var password = document.getElementById('password');
var signupForm = document.getElementById('signup-form');
var enterPassword = document.getElementById('enterPassword');

var errorUsername = document.getElementById('errorUsername');

function signup(){
    signupForm.addEventListener('submit',function(event){
        event.preventDefault();
        var errorElement = document.getElementById('error');
        if (password.value !== enterPassword.value){
            errorElement.textContent = 'Mật khẩu không khớp';
        }
        else {
            var getDatas = {
                userName : username.value,
                password : password.value,
                email : email.value,
                name : names.value
            }
            fetch(signupApi,{
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(getDatas)
            }).then(response => response.json())
            .then(data => {
                if(data.status === 'SUCCESS'){
                    alert(data.message);
                    window.location.href = './login.html';
                }
                else{
                    alert(data.message);
                }
            })
            .catch(error =>{
                console.log(error);
            })
        }
    })
}