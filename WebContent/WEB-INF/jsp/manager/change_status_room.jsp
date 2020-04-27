<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>
<%@ page import="ua.nure.aseev.SummaryTask4.db.RoomStatus"%>


<html>
<head>
<title>Change Room Status</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/manager/change_status_room.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<fmt:setLocale value="${locale}"/>
<style type="text/css">
.CheckTime {
	position: absolute;
	top: 22%;
	left: 55%;
}

</style>

</head>
<body>

	<%@ include file="/WEB-INF/jspf/header.jspf"%>
	
	
	<form action="controller" method="post">
	<input type="hidden" name="command" value="checkTime">
	<div class="CheckTime">
        <button><fmt:message key="change_status_room_jsp.deleteBad"/></button>
        </div>
	</form>
	
	<div class="general">
		<div class="block">
			<div class="sort">
			<form action="controller" method="post">
					<input type="hidden" name="command" value="sort" /> <a><fmt:message key="list_menu_jsp.sort"/></a> <select name="sort">
						<option value="def"><fmt:message key="list_menu_jsp.sort.Standart"/></option>
						<optgroup label="Price"><fmt:message key="list_menu_jsp.sort.price"/>
						</optgroup>
						<option value="low-max-price"><fmt:message key="list_menu_jsp.sort.price.maxMin"/></option>
						<option value="max-low-price"><fmt:message key="list_menu_jsp.sort.price.minMax"/></option>
						<optgroup label="Count"><fmt:message key="list_menu_jsp.sort.сont"/>
						</optgroup>
						<option value="low-max-count"><fmt:message key="list_menu_jsp.sort.сont.minMax"/></option>
						<option value="max-low-count"><fmt:message key="list_menu_jsp.sort.сont.maxMin"/></option>
						<optgroup label="Class"><fmt:message key="list_menu_jsp.sort.class"/>
						</optgroup>
						<option value="luxure"><fmt:message key="list_menu_jsp.sort.class.luxure"/></option>
						<option value="standart"><fmt:message key="list_menu_jsp.sort.class.standart"/></option>
						<option value="medium"><fmt:message key="list_menu_jsp.sort.class.medium"/></option>
						<optgroup label="Status"><fmt:message key="list_menu_jsp.table.status"/>
						</optgroup>
						<option value="free"><fmt:message key="list_menu_jsp.sort.free"/></option>
						<option value="booked"><fmt:message key="list_menu_jsp.sort.booked"/></option>
						<option value="locked"><fmt:message key="list_menu_jsp.sort.locked"/></option>
						<option value="unable"><fmt:message key="list_menu_jsp.sort.unable"/></option>
					</select> <input type="submit" value="Sort">
				</form>
			</div>
		</div>


		
			<div class="block">
				<div class="table-room">
					<table>
						<thead>
							<tr>
								<td><fmt:message key="change_status_room_jsp.table.number"/></td>
								<td><fmt:message key="change_status_room_jsp.table.status"/></td>
								<td><fmt:message key="change_status_room_jsp.table.place"/></td>
								<td><fmt:message key="change_status_room_jsp.table.class"/></td>
								<td><fmt:message key="change_status_room_jsp.table.price"/></td>
								<td><fmt:message key="change_status_room_jsp.table.selectStatus"/></td>
								<td><fmt:message key="change_status_room_jsp.table.action"/></td>
							</tr>
						</thead>

						<c:forEach var="room" items="${room}">
							<tr>
								<td>${room.number}</td>
								<td>${RoomStatus.getStatus(room.status)}</td>
								<td>${room.numberOfPlace}</td>
								<td>${room.classApartment}</td>
								<td>${room.price}</td>

								<form action="controller" method="get">
								<input type="hidden" name="command" value="changeStatusRoom">
								<td>
								<select name="chooseStatus">
										<option value="3">Locked</option>
										<option value="4">Unable</option>
										<option value="0">Free</option>
								</select>
								</td>

								<td>
								<c:if test="${not empty RoomStatus.getStatus(room.status)}">
										<c:choose>


											<c:when test="${RoomStatus.getStatus(room.status) != RoomStatus.getStatus(2)  && RoomStatus.getStatus(room.status) != RoomStatus.getStatus(1) }">
												
												<button name="roomNumber" value="${room.number}"><fmt:message key="change_status_room_jsp.table.button"/></button>
												
											</c:when>

											<c:otherwise>
												<a><fmt:message key="change_status_room_jsp.table.notOperation"/></a>
											</c:otherwise>

										</c:choose>

									</c:if>
									</td>
								</form>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		
	</div>



</body>
</html>