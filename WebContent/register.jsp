<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<c:set var="title" value="Login" />

<head>

<title>Registration</title>

<link rel="stylesheet" type="text/css" media="screen"
	href="style/LoginRegister.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="style/js/style.js"></script>
</head>

<body>
	<div class="login-page">
		<div class="form">
			<form class="register-form" action="controller" method="post">
				<input required type="hidden" name="command" value="register" />
				 <input required type="text" name="f_name" placeholder="Firs name" /> 
				 <input required type="text" name="s_name" placeholder="Second name" /> 
				 <input required name="login" name="login" placeholder="Login" />
				 <input required type="password" name="password" placeholder="password" /> 
				  <input required type="email" name="mail" placeholder="mail.com/gmail.com" pattern="^[a-z0-9](\.?[a-z0-9]){5,}@g(oogle)?mail\.com$"/>
				<button>create</button>
				<p class="message">
					Already registered? <a href="login.jsp">Sign In</a>
				</p>
			</form>
		</div>
	</div>


</body>
</html>







<%-- 	<table id="main-container">
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<tr>
			<td class="content center">
				<form id="login_form" action="controller" method="post">
					<input type="hidden" name="command" value="register" />
					<legend>First_Name</legend>
					<input name="f_name" placeholder="F-Name" />
					<br />
					<br />
					<legend>Second_Name</legend>
					<input name="s_name" placeholder="S-Name" />
					<br />
					<br />
					<legend>Create Login</legend>
					<input name="login" placeholder="Login" />
					<br /> <br />
					<legend>Create Password</legend>
					<input type="password" name="password" placeholder="Password" />
					 <br />
					<input type="submit" value="Register">
				</form>

				<p class="message">You are registered? <a href="login.jsp">Sign_UP</a></p>
			</td>

		</tr>
		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	</table> --%>