var signinApi = 'http://localhost:8088/auth/signin';
var username = document.getElementById('username');
var password = document.getElementById('password');

var data = {
    user : username.value,
    pass : password.value
}
function signin(){
    fetch(signinApi,{
        method : "POST",
        headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(data)
        })
    .then(response => response.json())
    .then(data =>{
        console.log(data);
        window.location.replace("./index.html");
    })
    .catch(error =>{
        console.log("Error:",error);
    });
}
