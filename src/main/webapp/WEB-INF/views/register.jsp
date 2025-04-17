<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width:device-width, initial-scale=1">
<title>新規登録</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<style>
.container-register {
    max-width: 400px;
    margin: 20px auto;
    padding: 20px;
    font-family: sans-serif;
    background-color: #f5f8fa;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.container-register h1 {
    text-align: center;
    font-size: 1.5em;
    margin-bottom: 20px;
    color: #1da1f2;
}

.form-group {
    margin-bottom: 15px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #14171a;
}

.form-group input {
    width: 100%;
    padding: 10px;
    border: 1px solid #e6ecf0;
    border-radius: 5px;
    box-sizing: border-box; /* はみ出し対策で追加 */
}

.btn-primary {
    background-color: #1da1f2;
    color: white;
    border: none;
    padding: 10px 15px;
    border-radius: 20px;
    cursor: pointer;
    font-size: 1em;
    display: block;
    margin: 20px auto;
    width: 100px;
    text-align: center;
}

.btn-primary:hover {
    background-color: #0c85d0;
}

.error-message {
    color: #f44336;
    font-size: 0.9em;
    margin-top: 5px;
}

.login-link {
    text-align: center;
    display: block;
    margin-top: 10px;
    color: #1da1f2;
}
</style>
<script>
function validatePassword() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordMismatchError = document.getElementById('passwordMismatchError');

    if (password !== confirmPassword) {
        passwordMismatchError.textContent = 'パスワードと確認用パスワードが一致しません。';
        return false;
    } else {
        passwordMismatchError.textContent = '';
        return true;
    }
}
</script>
</head>
<body>
    <div class="container-register">
        <h1>新規登録</h1>
        <c:if test="${not empty error}">
            <p class="error-message">${error}</p>
        </c:if>
        <form action="${pageContext.request.contextPath}/app/register" method="post" onsubmit="return validatePassword()">
            <div class="form-group">
                <label for="username">ユーザー名:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">パスワード:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirmPassword">パスワード確認:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
                <p id="passwordMismatchError" class="error-message"></p>
            </div>
            <div class="form-group">
                <label for="email">メールアドレス:</label>
                <input type="email" id="email" name="email">
            </div>
            <button type="submit" class="btn btn-primary">登録</button>
            <a href="${pageContext.request.contextPath}/app/login" class="login-link">ログインはこちら</a>
        </form>
    </div>
</body>
</html>
<script>
function validatePassword() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordMismatchError = document.getElementById('passwordMismatchError');

    if (