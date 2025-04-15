<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>ログイン</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<style>
.error {
	color: red;
}

.success {
	color: green;
}
</style>
</head>
<body>
	<div class="container">
		<h1>ログイン</h1>

		<c:if test="${not empty error}">
			<p class="error">
				<c:out value="${error}" />
			</p>
		</c:if>
		<c:if test="${not empty successMessage}">
			<p class="success">
				<c:out value="${successMessage}" />
			</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/app/login"
			method="post">
			<div>
				<label for="username">ユーザー名:</label><br> <input type="text"
					id="username" name="username" required>
			</div>
			<br>
			<div>
				<label for="password">パスワード:</label><br> <input type="password"
					id="password" name="password" required>
			</div>
			<br>
			<button type="submit">ログイン</button>
		</form>
		<p>
			まだ登録していない方は <a href="${pageContext.request.contextPath}/app/register">こちら</a>
		</p>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>