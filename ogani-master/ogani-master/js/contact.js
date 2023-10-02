var messageApi = 'http://localhost:8088/comment/create';

var token = localStorage.getItem('token');

var names = document.getElementById('name');
var emails = document.getElementById('email');
var message = document.getElementById('message');
var contact = document.getElementById('contact');
var data = {
    name : names.value,
    email : emails.value,
    comment : message.value
}

function getMessage(){
    if(token){
    contact.addEventListener('click',function(event){
        event.preventDefault();
            fetch(messageApi,{
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'},
                body: JSON.stringify(data)
            }).then(response => response.json())
         }).catch(error => {
            // Xử lý lỗi tại đây
            console.error(error);
        })
    }
        else{
            alert('Bạn chưa đăng nhập');
        }
    
git 