<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>


<html>
<head>
<title>Order List</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/manager/personal_order.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<fmt:setLocale value="${locale}"/>
</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="order-page">
		<c:choose>
			<c:when test="${fn:length(orderOwnList) == 0}">
				<div class="no-order">    
                <fmt:message key="quick_order_jsp.no_order"/>
                </div>
			</c:when>

			<c:otherwise>
				<form action="controller" method="post">
					<input type="hidden" name="command" value="showOrder">
					<a id="a1"><fmt:message key="header_jspf.manager.personalOrder"/></a>
					<table id="list_own_order_table">
						<thead>
							<tr>
								<td>№</td>
								<td><fmt:message key="quick_order_jsp.table.id_order"/></td>
								<td><fmt:message key="quick_order_jsp.table.first_name"/></td>
								<td><fmt:message key="quick_order_jsp.table.last_name"/></td>
								<td><fmt:message key="quick_order_jsp.table.action"/></td>
							</tr>
						</thead>

						<c:set var="k" value="0" />
						<c:forEach var="bean" items="${orderOwnList}">
							<c:set var="k" value="${k+1}" />
							<tr>
								<td><c:out value="${k}" /></td>
								<td>${bean.id}</td>
								<td>${bean.userFirstName}</td>
								<td>${bean.userLastName}</td>
								<td><button name="id" value="${bean.id}"><fmt:message key="personal_order_jsp.table.show_order"/></button></td>
							</tr>
						</c:forEach>
					</table>



				</form>
			</c:otherwise>
		</c:choose>

	</div>
</body>
</html>