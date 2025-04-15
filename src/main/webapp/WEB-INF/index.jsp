<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link rel="stylesheet" href="javascript:void(0)">
</head>
<body>
	<h1>災害情報投稿</h1>
	<form method="post" action="<%=request.getContextPath()%>/" enctype="multipart/form-data">
		<div>
			<label for="text">投稿内容:</label><br>
			<textarea id="text" name="text" rows="3" cols="50"></textarea>
		</div>
		<div>
			<label for="image">画像 (任意):</label> <input type="file" id="image" name="image" accept="image/*">
		</div>
		<input type="hidden" id="latitude" name="latitude"> <input type="hidden" id="longitude" name="longitude">
		<button type="submit">投稿</button>
	</form>

	<hr>

	<h2>投稿一覧</h2>
	<c:forEach var="post" items="${posts}">
		<p>
			<c:out value="${post.text}" />
		</p>
		<c:if test="${not empty post.imageUrl}">
			<img src="<%= request.getContextPath() %>/<c:out value="${post.imageUrl}" />" style="max-width: 300px;">
		</c:if>
		<c:if test="${not empty post.latitude && not empty post.longitude}">
			<p>
				位置情報: (
				<c:out value="${post.latitude}" />
				,
				<c:out value="${post.longitude}" />
				)
			</p>
		</c:if>
		<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss" var="formattedDate" />
		<p>
			投稿日時:
			<c:out value="${formattedDate}" />
		</p>
		<hr>
	</c:forEach>

	<script>
		if (navigator.geolocation) {
			navigator.geolocation
					.getCurrentPosition(
							function(position) {
								document.getElementById("latitude").value = position.coords.latitude;
								document.getElementById("longitude").value = position.coords.longitude;
							}, function(error) {
								console.error("位置情報の取得に失敗しました:", error);
							});
		} else {
			console.log("このブラウザはGeolocation APIに対応していません。");
		}
	</script>
</body>
</html>