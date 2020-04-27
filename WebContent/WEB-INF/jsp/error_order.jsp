<%@ page isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>

<head>
<title>Error</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/error-page.css" />
<fmt:setLocale value="${locale}"/>
</head>
<body>

	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>500</h1>
			</div>
			<h2>Oops! Something went wrong...</h2>
			
			<c:if test="${not empty requestScope.errorMessage}">
				<h3>${requestScope.errorMessage}</h3>
			</c:if>
			
			<c:choose>
				<c:when test="${forward = true}">
					<form action="controller" method="post">
						<input type="hidden" name="command" value="errorPage">
						<button>Go Back</button>
					</form>

				</c:when>
				<c:otherwise>
					<form action="controller" method="post">
						<input type="hidden" name="command" value="logout">
						<button>Login</button>
					</form>

				</c:otherwise>
			</c:choose>

		</div>
	</div>

</body>
</html>