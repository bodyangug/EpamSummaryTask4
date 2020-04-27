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





<%-- <body>
	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content center">
				<form id="login_form" action="controller" method="post">
					<input type="hidden" name="command" value="login" />
						<legend>Login</legend>
						<input name="login" placeholder="Login"/><br />
					<br />
						<legend>Password</legend>
						<input type="password" name="password" placeholder="Password"/>
					<br /> <input type="submit" value="Login">
				</form>
				
				<p class="message">Yoa aren`t registered? <a href="register.jsp">Register</a></p>
			</td>
			
		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table>
</body> --%>
</html>


