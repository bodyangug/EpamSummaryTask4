<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>


<html>
<head>
<title>Show Order</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/manager/show_order.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />
	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<fmt:setLocale value="${locale}"/>
</head>


<body>


	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<div class="order-page">
		<div class="form-1">
			<div class="order-form">
			<form action="controller" method="post">
			<input type="hidden" name="start" value="${order.timeStart}">
			<input type="hidden" name="end" value="${order.timeEnd}">
				<h3><fmt:message key="show_order_jsp.order"/></h3>
				<p>Order № "${order.id}"</p>
				<p>User Name : ${order.userFirstName}</p>
				<p>User Second-Name : ${order.userLastName}</p>
				<p>Count : ${order.numberOfPlaces}</p>
				<p>Class Apartment : ${order.classApartment}</p>
				<p>Time Start: ${order.timeStart}</p>
				<p>Time End: ${order.timeEnd}</p>
			</form>
			<form action="controller" method="post">
			<input type="hidden" name="command" value="declineOrder">
			<button id="red" name="decline" value="${order.id}"><fmt:message key="show_order_jsp.decline_order"/></button>
			
			</form>
			 
			</div>
						</div>
	</div>


	<div class="choose">
		<div class="form-2">
			<div class="choose-form">
				<form>
				<h3><fmt:message key="show_order_jsp.choose"/></h3>
				<table>
			
			
				<form action="controller" method="post">
				<input type="hidden" name="command" value="selection" /> 
				<a><fmt:message key="show_order_jsp.selection"/></a>  
				<select name="place">
					<option value="default"><fmt:message key="show_order_jsp.selection.default"/></option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select> 
				<a> </a>
				<select name="class">
					<option value="default" ><fmt:message key="show_order_jsp.selection.default"/></option>
					<option value="Standart"><fmt:message key="show_order_jsp.selection.standart"/></option>
					<option value="Medium"><fmt:message key="show_order_jsp.selection.medium"/></option>
					<option value="Luxure"><fmt:message key="show_order_jsp.selection.luxure"/></option>
				</select> 
				<a>  </a>
				<input type="submit" value="Selection">
			</form>
				<br></br>
					<thead>
						<tr>
							<td><fmt:message key="show_order_jsp.table.number"/></td>
							<td><fmt:message key="show_order_jsp.table.place"/></td>
							<td><fmt:message key="show_order_jsp.table.class"/></td>
							<td><fmt:message key="show_order_jsp.table.price"/></td>
							<td><fmt:message key="show_order_jsp.table.action"/></td>
						</tr>
					</thead>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="chooseThatRoom">
					<c:forEach var="room" items="${room}">
						<tr>
							<td>${room.number}</td>
							<td>${room.numberOfPlace}</td>
							<td>${room.classApartment}</td>
							<td>${room.price}</td>
							<td><button name="roomNumber" value="${room.number}"><fmt:message key="show_order_jsp.button"/></button></td>
						</tr>
					</c:forEach>
					</form>
				</table>
				</form>
			</div>
		</div>
	</div>
</body>

</html>

