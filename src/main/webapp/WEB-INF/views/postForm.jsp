
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>新規投稿</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<style>
.error {
	color: red;
}
</style>
</head>
<body>
	<div class="container">
		<h1>新規投稿</h1>

		<c:if test="${not empty error}">
			<p class="error">
				<c:out value="${error}" />
			</p>
		</c:if>

		<form action="${pageContext.request.contextPath}/app/create" method="post" enctype="multipart/form-data">
			<div>
				<label for="message">メッセージ:</label><br>
				<textarea id="message" name="message" rows="5" cols="50" required></textarea>
			</div>
			<br>
			<div>
				<label for="image">画像 (任意):</label><br> <input type="file" id="image" name="image" accept="image/jpeg, image/png">
					<br><small>JPEG/PNG形式。位置情報(Exif)が含まれていると地図に表示されます。</small>
			</div>
			<br>
			<button type="submit">送信</button>
		</form>
		<p class="buttons">
			<a href="${pageContext.request.contextPath}/app/list">一覧に戻る</a>
		</p>
	</div>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>