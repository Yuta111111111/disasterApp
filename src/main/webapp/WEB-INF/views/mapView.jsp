
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>地図表示</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"
	integrity="sha512-xodZBNTC5n17Xt2atTPuE1HxjVMSvLVW9ocqUKLsCC5CXdbqCmblAshOMAS6/keqq/sMZMZ19scR4PsZChSR7A=="
	crossorigin="" />
<script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"
	integrity="sha512-XQoYMqMTK8LvdxXYG3nZ448hOEQiglfqkJs1NOQV44cWnUrBc8PkAOcXy20w0vlaXaVUearIOBhiXZ5V3ynxwA=="
	crossorigin=""></script>
<style>
html, body {
	height: auto;
	margin: 0;
	padding: 0;
}

#map-container { /* 新しいコンテナ */
    width: 100%;
    height: 80vh; /* 地図の高さを調整 (例: 80% のビューポートの高さ) */
}

#mapid {
	height: 100%;
	width: 100%;
}

.back-button { /* 戻るボタン用のスタイル (必要に応じて) */
    display: block;
    width: 200px;
    margin: 10px auto;
    padding: 10px;
    text-align: center;
    background-color: #f0f0f0;
    border: 1px solid #ccc;
    cursor: pointer;
}
</style>
</head>
<body>
	<c:if test="${latitude != null && longitude != null}">
		<div id="map-container">
			<div id="mapid"></div>
			<script>
			var lat = ${latitude};
			var lon = ${longitude};
			var imagePath = "${imagePath}"; // サーブレットから渡された画像パスを取得

			console.log("mapView.jsp - Latitude: " + lat);
			console.log("mapView.jsp - Longitude: " + lon);
			console.log("mapView.jsp - Image Path: " + imagePath);

			var mymap = L.map('mapid').setView([ lat, lon ], 15); // 初期ズームレベルを調整
			console.log("mapView.jsp - Map initialized with:", lat, lon);

			L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
			    maxZoom : 19,
			    attribution : '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
			}).addTo(mymap);

			var popupContent = '<img src="' + imagePath + '" alt="投稿画像" style="width:100px; height:auto;"><br>投稿された場所'; // ポップアップに表示するHTML
			L.marker([ lat, lon ]).addTo(mymap).bindPopup(popupContent).openPopup();
			</script>
		</div>
	</c:if>
	<c:if test="${latitude == null || longitude == null}">
		<p>有効な位置情報がありません。</p>
		<p>
			<a href="javascript:window.close();">ウィンドウを閉じる</a>
		</p>
	</c:if>
	<p class="back-button" onclick="window.location.href='${pageContext.request.contextPath}/app/list'" style="cursor: pointer;">
		投稿一覧に戻る
	</p>
</body>
</html>