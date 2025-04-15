<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>自分の投稿一覧</title>
<link rel="stylesheet" href="<c:url value='/css/style.css' />">
<style>
.container-lists {
    max-width: 960px;
    margin: 20px auto; /* 上下のマージンも追加して中央に配置 */
    padding: 20px;
    font-family: sans-serif; /* シンプルなフォントに変更 */
}

.container-lists h1 {
    text-align: center;
    font-size: 1.5em; /* 少し小さく */
    margin-bottom: 20px;
}

.header-buttons {
    text-align: right; /* 右寄せに変更 */
    margin-bottom: 10px;
}

.header-buttons a {
    display: inline-block;
    padding: 8px 12px; /* 少し小さく */
    margin-left: 8px;
    text-decoration: none;
    background-color: #1da1f2; /* Twitterの青色 */
    color: white;
    border-radius: 20px; /* 丸みを帯びさせる */
    font-size: 0.9em;
    border: none; /* ボーダーを削除 */
}

.header-buttons a:hover {
    background-color: #0c85d0; /* ホバー時の色を少し暗く */
}

.buttons {
    text-align: center;
    margin-top: 20px;
    margin-bottom: 20px;
}

.buttons a {
    display: inline-block;
    padding: 10px 20px;
    text-decoration: none;
    background-color: #f5f8fa; /* 薄い背景色 */
    color: #1da1f2; /* Twitterの青色 */
    border: 1px solid #1da1f2; /* 青色のボーダー */
    border-radius: 20px;
    font-size: 0.9em;
    margin: 0 5px;
}

.buttons a:hover {
    background-color: #e1f5fe; /* ホバー時の背景色を少し明るく */
}

.post-item {
    padding: 15px 0;
    border-bottom: 1px solid #e6ecf0; /* 薄い区切り線 */
    display: flex; /* Flexbox を使用して要素を横並びにする */
    justify-content: space-between; /* 左右に配置 */
    align-items: flex-start; /* 上端で揃える */
}

.post-item > div:first-child { /* 投稿内容部分 */
    flex-grow: 1; /* 残りのスペースを埋める */
}

.post-item:last-child {
    border-bottom: none; /* 最後の投稿の区切り線を削除 */
}

.username {
    font-weight: bold;
    margin-bottom: 5px;
    color: #14171a; /* 濃いグレー */
}

.post-meta {
    font-size: 0.8em;
    color: #657786; /* 薄いグレー */
    margin-top: 5px;
}

.post-image {
    cursor: pointer;
    max-width: 50%; /* 親要素に合わせて最大幅を設定 */
    height: auto; /* 高さ自動調整 */
    display: block;
    margin-top: 10px;
    border-radius: 10px; /* 画像を少し丸く */
    box-shadow: 1px 1px 5px #ccc; /* 影を追加 */
}

.post-meta span {
    display: block; /* 改行して表示 */
    margin-top: 5px;
}

.delete-button {
    background-color: #f44336;
    color: white;
    border: none;
    padding: 8px 12px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 0.9em;
    cursor: pointer;
    border-radius: 20px;
    margin-left: 10px; /* 投稿内容との間にスペース */
    white-space: nowrap;
}
</style>
</head>
<body>
	<div class="container-lists">
		<h1>自分の投稿一覧</h1>
		<p class="buttons">
			<a href="${pageContext.request.contextPath}/app/mypage">マイページに戻る</a>
		</p>
		<hr>

		<c:if test="${empty myPosts}">
			<p>まだ投稿がありません。</p>
		</c:if>

		<c:forEach var="post" items="${myPosts}">
			<div class="post-item">
				<div>
					<p>
						<c:out value="${post.message}" />
					</p>
					<c:if test="${not empty post.imagePath}">
						<c:choose>
							<c:when test="${post.latitude != null && post.longitude != null}">
								<img src="${pageContext.request.contextPath}/${post.imagePath}" alt="投稿画像" class="post-image"
									onclick="showMap(${post.latitude}, ${post.longitude}, '${pageContext.request.contextPath}/${post.imagePath}')">
								<span class="post-meta">画像をクリックすると地図を表示します</span>
							</c:when>
							<c:otherwise>
								<img src="${pageContext.request.contextPath}/${post.imagePath}" alt="投稿画像 (位置情報なし)"
									style="max-width: 200px; max-height: 200px; display: block; margin-top: 5px;">
								<span class="post-meta">この画像には位置情報がありません</span>
							</c:otherwise>
						</c:choose>
					</c:if>
					<p class="post-meta">
						投稿日時:<fmt:formatDate value="${post.postedAt}" pattern="yyyy/MM/dd HH:mm:ss" />
						<c:if test="${post.latitude != null && post.longitude != null}">
                            | 位置情報: (<fmt:formatNumber value="${post.latitude}" pattern="#.######" />, <fmt:formatNumber
								value="${post.longitude}" pattern="#.######" />)
                        </c:if>
					</p>
				</div>
				<form action="${pageContext.request.contextPath}/app/deletepost" method="post">
					<input type="hidden" name="postId" value="${post.id}">
					<button type="submit" class="delete-button" onclick="return confirm('本当にこの投稿を削除しますか？')">削除</button>
				</form>
			</div>
		</c:forEach>
	</div>

	<script>
        function showMap(lat, lon, imagePath) {
        	// 同じタブで地図ページを開く
            window.location.href = '${pageContext.request.contextPath}/app/map?lat=' + lat + '&lon=' + lon + '&imagePath=' + imagePath;
        }
    </script>
	<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>