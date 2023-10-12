var username = localStorage.getItem('username');
var token = localStorage.getItem('token');
var userActions = document.querySelector('.user-actions');
var welcomeMessage = document.getElementById('welcome-message');

function login() {
    if (username) {
        welcomeMessage.innerHTML = `Xin chào, ${username} -`;
        var logoutLink = document.createElement('a');
        logoutLink.href = './login/login.html';
        logoutLink.id = 'logout';
        logoutLink.textContent = 'Đăng xuất';
        logoutLink.addEventListener('click', logout);
        userActions.appendChild(logoutLink);
    }
}

login();

function logout() {
    localStorage.removeItem('username');
    username = null;
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    userActions.innerHTML = '';
    login();
    location.reload();
}