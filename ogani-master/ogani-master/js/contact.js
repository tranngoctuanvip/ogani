var messageApi = "http://localhost:8088/comment/create";

var token = localStorage.getItem('token');
var names = document.getElementById("name");
var emails = document.getElementById("email");
var message = document.getElementById("message");
var contact = document.getElementById("contact");

function getMessage() {
    contact.addEventListener('submit', function (event) {
    if (token) {
      event.preventDefault();
      var getDatas = {
        name: names.value,
        email: emails.value,
        comment: message.value,
      };
      if (names.value === "" || emails.value === "" || message.value === "") {
        alert("Bạn chưa điền đầy đủ thông tin");
      }
      fetch(messageApi, {
        method: "POST",
        headers: {
          Authorization : "Bearer " + token,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(getDatas),
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          alert('Send message successfull');
        })
        .catch((error) => {
          // Xử lý lỗi tại đây
          console.error(error);
        });
    }
    else {
        alert("Bạn chưa đăng nhập");
      }
    })
}
  
