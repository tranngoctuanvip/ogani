var resetPasswordApi = 'http://localhost:8088/auth/resetPassword';

var userName = document.getElementById('username');
var otp = document.getElementById('otp');
var password = document.getElementById('password');
var newPassword = document.getElementById('newPassword');
var resetForm = document.getElementById('reset-form');

function resetPassword(){
    resetForm.addEventListener('submit',function(event){
        event.preventDefault();
        if (userName.value === ''){
            alert('Vui lòng nhập tên đăng nhập');
        }
        else if (otp.value === ''){
            alert('Vui lòng nhập mã otp');
        }
        else if (password.value === ''){
            alert('Vui long nhập mật khẩu');
        }
        else if (password.value !== newPassword.value){
            alert('Mật khẩu không khớp');
        }
        else {
            var url = `${resetPasswordApi}?username=${userName.value}&OTP=${otp.value}&newPassword=${password.value}`;
            fetch(url,{
                method: 'POST'
            }).then(response => response.json())
            .then(data => {
                if (data.status === 'SUCCESS'){
                    console.log(data);
                    alert('Đổi mật khẩu thành công');
                    window.location.href = './login.html';
                }
                else{
                    alert(data.message);
                }
            }).catch(error =>{
                console.log(error);
            })
        } 
    })
}