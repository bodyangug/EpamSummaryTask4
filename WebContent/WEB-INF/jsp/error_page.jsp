<%@ page isErrorPage="true"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<html>

<head>
<title>Error</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/error_page_404.css" />
	<fmt:setLocale value="${locale}"/>
</head>


<body>

	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>404</h1>
			</div>
			<h2><fmt:message key="error_page_jsp"/></h2>
			<c:choose>
				<c:when test="${not empty errorMessage}">
					<h3>${errorMessage}</h3>
				</c:when>
				<c:otherwise>
					<h3>This page not found!</h3>
				</c:otherwise>
			</c:choose>


			<a href="login.jsp">Go To Back</a>
		</div>
	</div>


</body>
</html>