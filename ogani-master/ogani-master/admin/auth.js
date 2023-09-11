var signinApi = 'http://localhost:8088/auth/signin';
var username1 = document.getElementById("username");
var password1 = document.getElementById("password");

// var data = {
//     username : username1.value,
//     password : password1.value
// }
function signin(event){
    event.preventDefault();
    var encodedUsername = encodeURIComponent(username1.value);
      var encodedPassword = encodeURIComponent(password1.value); 
    fetch(signinApi,{
        method : "POST",
        headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({username : encodedUsername, password : encodedPassword})
        })
    .then(response => response.json())
    .then(data =>{
        console.log(data);
        if(data.SUCCESS){
            window.location.href = '/admin/home.html';
        }
    })
    .catch(error =>{
        console.log("Error:",error);
    });
}
