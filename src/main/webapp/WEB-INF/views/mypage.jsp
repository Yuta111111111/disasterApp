<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>マイページ</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
	<div class="container">
		<h1>マイページ</h1>

		<c:if test="${not empty loggedInUser}">
			<p>
				ユーザー名:
				<c:out value="${loggedInUser.username}" />
			</p>
			<c:if test="${not empty loggedInUser.email}">
				<p>
					メールアドレス:
					<c:out value="${loggedInUser.email}" />
				</p>
			</c:if>
			<p class="buttons"><a href="${pageContext.request.contextPath}/app/list" class="buttons a">投稿一覧に戻る</a></p>
			<p class="buttons"><a href="${pageContext.request.contextPath}/app/myposts">自分の投稿一覧</a>
			<p class="buttons"><a href="${pageContext.request.contextPath}/app/logout" class="buttons a">ログアウト</a></p>
		</c:if>

		<c:if test="${empty loggedInUser}">
			<p>ログインしていません。</p>
			<p>
				<a href="${pageContext.request.contextPath}/app/login">ログイン</a>
			</p>
		</c:if>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>