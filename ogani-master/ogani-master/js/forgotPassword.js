var forgotPasswordApi = 'http://localhost:8088/auth/sendEmail?email=';

var email = document.getElementById('email');
var emailForm = document.getElementById('email-form');


function checkEmail(emails) {
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(emails);
}

function sendEmail(){
    emailForm.addEventListener('submit',function(event){
        event.preventDefault();
       if(!checkEmail(email.value)){
        alert('Email không hợp lệ');
       }
       else if(email.value === ''){
            alert('Email không được để trống');
        }
        else {
            var url = `${forgotPasswordApi}${email.value}`;
            fetch(url,{
                method: 'POST'
            }).then(response => {
                response.json();
                if (response.ok){
                    alert('Đã gửi mã OTP');
                    window.location.href = './resetPassword.html';
                }
                else{
                    alert('Gửi mã OTP thất bại');
                }
            })
            .then(data => {
                console.log(data);
            }).catch(error => console.log(error));
        }
    })
        
}