<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<html>


<head>
<title>Personal Office</title>
<link rel="stylesheet" type="text/css" media="screen"
	href="style/client/personal.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="style/header.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fmt:setLocale value="${locale}"/>
</head>

<body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

	<div class="page">
        
      <div class="back-one">
         <div class="text-one">
        <h2><fmt:message key="personal_office_jsp.quickOrder"/></h2>
          </div>
        <form action="controller" method="post">
          <input type="hidden" name="command" value="pay" />
          <table>
            <thead>
              <tr>
                <td><fmt:message key="personal_office_jsp.table.roomNumber"/></td>
                <td><fmt:message key="personal_office_jsp.table.numberOfPlaces"/></td>
                <td><fmt:message key="personal_office_jsp.table.classApartmet"/></td>
                <td><fmt:message key="personal_office_jsp.table.price"/></td>
                <td><fmt:message key="personal_office_jsp.table.timeStart"/></td>
                <td><fmt:message key="personal_office_jsp.table.timeEnd"/></td>
                <td><fmt:message key="personal_office_jsp.table.payedStatus"/></td>
              </tr>
            </thead>

            <c:forEach var="req" items="${req}">
              <tr>
                <td>${req.roomNumber}</td>
                <td>${req.numberOfPlaces}</td>
                <td>${req.classApartment}</td>
                <td>${req.price}</td>
                <td>${req.timeStart}</td>
                <td>${req.timeEnd}</td>
                <td>${req.payedStatus}</td>

                <c:if test="${not empty req.sucsess}">
                  <c:choose>
                    <c:when test="${req.payedStatus == 'true'}">
                      <td><fmt:message key="personal_office_jsp.table.payed"/></td>
                    </c:when>

                    <c:when test="${req.sucsess == 'true' }">

                      <td><button name="orderId" value="${req.id}"><fmt:message key="look_order_jsp.pay"/></button>
                      </td>
                    </c:when>
                    <c:when test="${req.sucsess == 'false' }">
                      <td><fmt:message key="personal_office_jsp.wait"/></td>
                    </c:when>
                  </c:choose>
                </c:if>
              </tr>
            </c:forEach>
          </table>
        </form>
      </div>

      <div class="back-two">
          <div class="text-two">
        <h2><fmt:message key="personal_office_jsp.ownOrder"/></h2>
          </div>
        <form action="controller" method="post">
          <input type="hidden" name="command" value="lookOrder">
          <table>
            <thead>
              <tr>
                <td><fmt:message key="personal_office_jsp.table.numberOfPlaces"/></td>
                <td><fmt:message key="personal_office_jsp.table.classApartmet"/></td>
               	<td><fmt:message key="personal_office_jsp.table.timeStart"/></td>
                <td><fmt:message key="personal_office_jsp.table.timeEnd"/></td>
                <td><fmt:message key="personal_office_jsp.table.payedStatus"/></td>
              </tr>
            </thead>
            <c:forEach var="ownReq" items="${ownReq}">
              <tr>
                <td>${ownReq.numberOfPlaces}</td>
                <td>${ownReq.classApartment}</td>
                <td>${ownReq.timeStart}</td>
                <td>${ownReq.timeEnd}</td>
                <td>${ownReq.payedStatus}</td>
                <td><button name="id" value="${ownReq.id}"><fmt:message key="personal_office_jsp.detail"/></button>
                </td>
              </tr>

            </c:forEach>

          </table>
        </form>
      </div>
      </div>
</body>


</html>