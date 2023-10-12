var signinApi = "http://localhost:8088/auth/signin";
var username1 = document.getElementById("username");
var password1 = document.getElementById("password");
var loginForm = document.getElementById("login-form");

function signin() {
  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    var usernameValue = username1.value.trim();
    var passwordValue = password1.value.trim();
    var datalogin = {
      username: usernameValue,
      password: passwordValue,
    };
    fetch(signinApi, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(datalogin),
    })
      .then(function (response) {
        return response.json();
      })
      .then(data => {
        if (data.status === "SUCCESS") {
          var roles = data.data.role.map(function (role) {
            return role.authority;
          });
          if (roles.includes("ADMIN") || roles.includes("STAFF")) {
            // Lưu thông tin token và ID vào Local Storage
          localStorage.setItem("token", data.data.token);
          localStorage.setItem("username", data.data.username);
          localStorage.setItem("role", JSON.stringify(roles));
            window.location.href =
              "http://127.0.0.1:5500/ogani-master/admin/home.html";
          } 
          else if (roles.includes("USER")) {
            alert("Bạn không có quyền truy cập vào đường link này");
          }
        }
        else{
          alert(data.message);
        }
      })
      .catch((error) => {
        console.log("Error:", error);
      });
  });
}
