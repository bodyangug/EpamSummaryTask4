<%@ page pageEncoding="UTF-8"%>
<%@ page import="ua.nure.aseev.SummaryTask4.db.RoomStatus"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>
<head>
<title>List Order</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/client/ListMenu.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/datapicker.css" />
<fmt:setLocale value="${locale}"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script> 

</head>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>


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


		<form action="controller" method="post">
			<input type="hidden" name="command" value="makeOrder" />
			<input type="hidden" id="minTime" value="${minTime}">
			<input type="hidden" id="maxTime" value="${maxTime}">
			<div class="block-time">
				<div class="choose">
					<div id="datepicker">
					
					<label><fmt:message key="list_menu_jsp.timeStart"/></label> 
					<input required type="text" id="StartDate" name="start" readonly class="form_datetime"/>
					 </br> </br>
					  <label><fmt:message key="list_menu_jsp.timeEnd"/></label>
					   <input required type="text" id="EndDate" name="end" readonly class="form_datetime" />
					   </div>
				</div>
			</div>
			<div class="block">
				<div class="table-room">
					<table>
						<thead>
							<tr>
								<td><fmt:message key="list_menu_jsp.table.number"/></td>
								<td><fmt:message key="list_menu_jsp.table.status"/></td>
								<td><fmt:message key="list_menu_jsp.table.place"/></td>
								<td><fmt:message key="list_menu_jsp.table.class"/></td>
								<td><fmt:message key="list_menu_jsp.table.price"/></td>
								<td><fmt:message key="list_menu_jsp.table.order"/></td>
							</tr>
						</thead>

						<c:forEach var="room" items="${room}">
							<tr>
								<td>${room.number}</td>
								<td>${RoomStatus.getStatus(room.status)}</td>
								<td>${room.numberOfPlace}</td>
								<td>${room.classApartment}</td>
								<td>${room.price}</td>
								<td><c:choose>
										<c:when
											test="${RoomStatus.getStatus(room.status) == RoomStatus.getStatus(0) }">
											<button name="roomNumber" value="${room.number}"><fmt:message key="list_menu_jsp.table.button"/></button>
										</c:when>
										<c:otherwise>
										<fmt:message key="list_menu_jsp.table.no_operation"/>
									</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</form>
	</div>
	
	<script src="style/js/script.js"></script>
</body>



</html>