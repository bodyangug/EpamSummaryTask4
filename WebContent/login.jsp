<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Login" />

<head>

<title>Login</title>

<link rel="stylesheet" type="text/css" media="screen"
	href="style/LoginRegister.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


</head>

<body>
	<div class="login-page">
		<div class="form">
			<form class="login-form" action="controller" method="post">
				<input type="hidden" name="command" value="login" /> <input
					required type="text" name="login" placeholder="userlogin" /> <input
					required type="password" name="password" placeholder="password" />
				<button>login</button>
				<p class="message">
					Not registered? <a href="register.jsp">Create an account</a>
				</p>
			</form>
		</div>
	</div>
</body>
</html>


