<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>


<html>
<head>
<title>Order List</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/manager/quick_order.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<fmt:setLocale value="${locale}"/>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="order-page">
		<c:choose>
			<c:when test="${fn:length(orderList) == 0}">
            	<div class="no-order">    
                <fmt:message key="quick_order_jsp.no_order"/>
                </div>
            </c:when>

			<c:otherwise>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="updateSucsess" /> <input
						type="hidden" name="idOrder" value=""> 
						<a id="a1">Quick Order</a>
					<table id="list_order_table">
						<thead>
							<tr>
								<td>№</td>
								<td><fmt:message key="quick_order_jsp.table.id_order"/></td>
								<td><fmt:message key="quick_order_jsp.table.first_name"/></td>
								<td><fmt:message key="quick_order_jsp.table.last_name"/></td>
								<td><fmt:message key="quick_order_jsp.table.room_number"/></td>
								<td><fmt:message key="quick_order_jsp.table.number_of_places"/></td>
								<td><fmt:message key="quick_order_jsp.table.class_apartment"/></td>
								<td><fmt:message key="quick_order_jsp.table.time_start"/></td>
								<td><fmt:message key="quick_order_jsp.table.time_end"/></td>
								<td><fmt:message key="quick_order_jsp.table.price"/></td>
								<td><fmt:message key="quick_order_jsp.table.action"/></td>
							</tr>
						</thead>

						<c:set var="k" value="0" />
						<c:forEach var="bean" items="${orderList}">
							<c:set var="k" value="${k+1}" />
							<tr>
								<td><c:out value="${k}" /></td>
								<td>${bean.id}</td>
								<td>${bean.userFirstName}</td>
								<td>${bean.userLastName}</td>
								<td>${bean.roomNumber}</td>
								<td>${bean.numberOfPlaces}</td>
								<td>${bean.classApartment}</td>
								<td>${bean.timeStart}</td>
								<td>${bean.timeEnd}</td>
								<td>${bean.price}</td>
								
								<td><button name="value_yes" value="${bean.id}"><fmt:message key="quick_order_jsp.table.button_yes"/></button>
									<button name="value_no" value="${bean.id}"><fmt:message key="quick_order_jsp.table.button_no"/></button>
								</td>
							</tr>
						</c:forEach>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
    
</body>
</html>