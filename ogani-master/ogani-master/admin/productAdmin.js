var productAPi = 'http://localhost:8088/product/getProduct';
const productsPerPage = 6; // Số sản phẩm hiển thị trên mỗi trang
let currentPage = 0; // Trang hiện tại
function start(){
    getProduct(function(res){
        getData(res?.data || [])
    })
}
start();
function getProduct(callback){
    const url = `${productAPi}?page=${currentPage}&size=${productsPerPage}&sorType=desc&sortBy=id`;
    fetch(url)
    .then(function(response){
        return response.json();
    })
    .then(callback);
}
function getData(data){
    var listAllProduct1 = document.querySelector('#product-table');
    var htmls = data.map(function(elem){
        return`<tr><td>${elem.id}</td>
        <td>${elem.name}</td>
        <td><img src="${elem.image}" style="width: 150px; height: 100px; text-align:center" alt="Ảnh" /></td>
        <td>${elem.price}</td>
        <td>
            <div class="actions">
                <button id="edit-button">Sửa</button>
                <button id="delete-button">Xóa</button>
            </div>
        </td></tr>`
    }).join('');
    listAllProduct1.innerHTML = htmls;
    // updatePagination();
}

 // Hàm cập nhật phân trang
//  function updatePagination() {
//     const pagination = document.querySelector('.product__pagination');
//     pagination.innerHTML = '';
  
//     // Gửi yêu cầu HTTP HEAD để lấy thông tin về số lượng tổng sản phẩm
//     fetch(apiUrl, { method: 'HEAD' })
//       .then(response => {
//         const totalProducts = response.headers.get('X-Total-Count');
//         const totalPages = Math.ceil(totalProducts / productsPerPage);
  
//         for (let i = 1; i <= totalPages; i++) {
//           const pageLink = document.createElement('button');
//           pageLink.href = '#';
//           pageLink.textContent = i;
//           if (i === currentPage) {
//             pageLink.classList.add('active');
//           }
  
//           pageLink.addEventListener('click', () => {
//             currentPage = i;
//             getProducts();
//           });
  
//           pagination.appendChild(pageLink);
//         }
//       })
//       .catch(error => {
//         console.error('Error:', error);
//       });
//   }
  // Gọi hàm để lấy sản phẩm và cập nhật phân trang ban đầu
//   getProducts();

