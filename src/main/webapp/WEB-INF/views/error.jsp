
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>エラー</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<div class="container">
		<h1>エラーが発生しました</h1>
		<p>申し訳ありませんが、処理中に問題が発生しました。</p>
		<p>エラー内容:</p>
		<pre>
        <c:choose>
            <c:when test="${not empty requestScope['jakarta.servlet.error.message']}">
                <c:out value="${requestScope['jakarta.servlet.error.message']}" />
            </c:when>
            <c:when test="${not empty requestScope.error}"> <%-- FrontControllerで設定したエラー --%>
                 <c:out value="${requestScope.error}" />
            </c:when>
            <c:when test="${not empty param.error}"> <%-- パラメータで渡されたエラー --%>
                <c:out value="${param.error}" />
             </c:when>
             <c:when test="${not empty requestScope.errorMessage}"> <%-- ShowNewFormActionなどで設定したエラーメッセージ --%>
                 <c:out value="${requestScope.errorMessage}" />
            </c:when>
            <c:otherwise>
                不明なエラーです。
            </c:otherwise>
        </c:choose>
        </pre>
		<c:if test="${pageContext.exception != null}">
			<%-- 開発時のみスタックトレースを表示（本番では削除またはログ出力のみにする） --%>
			<%--
            <p>詳細情報 (開発用):</p>
            <pre><% pageContext.getException().printStackTrace(new java.io.PrintWriter(out)); %></pre>
            --%>
		</c:if>
		<p>
			<a href="${pageContext.request.contextPath}/app/list">トップページに戻る</a>
		</p>
	</div>
</body>
</html>