<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新規登録</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<script>
function validatePassword() {
	const password = document.getElementById('password').value;
	const confirmPassword = document.getElementById('confirmPassword').value;
	const passwordMismatchError = document.getElementById('passwordMismatchError');

	if (password !== confirmPassword) {
		passwordMismatchError.textContent = 'パスワードと確認用パスワードが一致しません。';
		return false; // フォームの送信を阻止
	} else {
		passwordMismatchError.textContent = ''; // エラーメッセージをクリア
		return true; // フォームの送信を許可
	}
}
</script>
</head>
<body>
	<div class="container">
		<h1>ユーザー登録</h1>
		<c:if test="${not empty error}">
			<p class="error-message">${error}</p>
		</c:if>
		<form action="${pageContext.request.contextPath}/app/register" method="post" onsubmit="return validatePassword()">
			<div class="form-group">
				<label for="username">ユーザー名:</label> <input type="text" id="username" name="username" required>
			</div>
			<div class="form-group">
				<label for="password">パスワード:</label> <input type="password" id="password" name="password" required>
			</div>
			<div class="form-group">
				<label for="confirmPassword">パスワード確認:</label> <input type="password" id="confirmPassword" name="confirmPassword"
					required>
				<%-- エラーメッセージ表示 --%>
				<p id="passwordMismatchError" class="error-message"></p>
			</div>
			<div>
				<label for="email">メールアドレス:</label><input type="email" id="email" name="email">
			</div>
			<button type="submit" class="btn btn-primary">登録</button>
			<p>
				<a href="${pageContext.request.contextPath}/app/login">ログインはこちら</a>
			</p>
		</form>
	</div>
</body>
</html>