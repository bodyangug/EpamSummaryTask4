<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>


<html>
<head>
<title>Look Order</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/client/look_order.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fmt:setLocale value="${locale}" />
</head>
<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


	<div class="block-1">
		<div class="order">
			<div class="text-order">
				<form>
					<div class="text-center">
						<h3><fmt:message key="look_order_jsp.clientOrder"/></h3>
					</div>
					<p>Order № : "${own.id}"</p>
					<p>User Name : ${own.userFirstName}</p>
					<p>User Second-Name : ${own.userLastName}</p>
					<p>Count : ${own.numberOfPlaces}</p>
					<p>Class Apartment : ${own.classApartment}</p>
					<p>Time Start: ${own.timeStart}</p>
					<p>Time End: ${own.timeEnd}</p>
				</form>
			</div>
		</div>
	</div>

	<div class="block-2">
		<div class="manager">
			<form action="controller" method="post">
				<input type="hidden" name="command" value="offer">
				<div class="text-center">
					<h3><fmt:message key="look_order_jsp.managerOrder"/></h3>
				</div>

				<c:choose>
					<c:when test="${manager_order.roomNumber == 0}">
						<div class="text-no"><fmt:message key="look_order_jsp.no_order"/></div>
					</c:when>

					<c:otherwise>
						<p>Room Number № "${manager_order.roomNumber}"</p>
						<p>Number Of Places: ${manager_order.numberOfPlaces}</p>
						<p>Class Apartment: ${manager_order.classApartment}</p>
						<p>Price: ${manager_order.price}</p>
						<c:choose>
							<c:when test="${manager_order.agree == false}">
								<button class="button-yes" name="vote" value="yes"><fmt:message key="quick_order_jsp.table.button_yes"/></button>
								<button class="button-no" name="vote" value="no"><fmt:message key="quick_order_jsp.table.button_no"/></button>
								<input type="hidden" name="id" value="${manager_order.id}">
							</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>

			</form>
			<c:choose>
				<c:when test="${own.payedStatus == true}">
					<div class="center-room">
						<a><fmt:message key="look_order_jsp.yourRoom"/></a>
					</div>
				</c:when>
				<c:when test="${manager_order.agree == true}">
					<form action="controller" method="get">
						<input type="hidden" name="command" value="payManager"> <input
							type="hidden" name="roomNumber"
							value="${manager_order.roomNumber}">
						<button class="button-pay" name="orderId"
							value="${manager_order.id}"><fmt:message key="look_order_jsp.pay"/></button>
					</form>
				</c:when>



			</c:choose>
		</div>
	</div>
</body>

</html>