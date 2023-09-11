function toggleForm() {
    var form = document.getElementById('product-form');
    if (form.style.display === 'none') {
      form.style.display = 'block';
    } else {
      form.style.display = 'none';
    }
  }

  document.getElementById('product-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Ngăn chặn chuyển hướng trang sau khi submit

    var form = event.target;
    var formData = new FormData(form);

    fetch('http://localhost:8088/product/create', {
      method: 'POST',
      body: formData
    }).then(function(response) {
      if (response.ok) {
        // Xử lý thành công
        console.log('Dữ liệu đã được gửi thành công!');
      } else {
        // Xử lý lỗi
        console.error('Gửi dữ liệu thất bại!');
      }
    }).catch(function(error) {
      // Xử lý lỗi mạng
      console.error('Lỗi kết nối: ' + error.message);
    });
  });