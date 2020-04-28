<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/directive/page.jspf"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>


<html>

  <head>
    <title>Make own order</title>

    <link rel="stylesheet" type="text/css" media="screen" href="style/client/ownRequest.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="style/header.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="style/datapicker.css" />
 

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.ui/1.10.3/jquery-ui.min.js"></script> 

	<fmt:setLocale value="${locale}"/>

  </head>

  <body>
	<%@ include file="/WEB-INF/jspf/header.jspf"%>

    <div class="ownOrder">
      <div class="form-out">
        <div class="fill-back">
          <h4 id="fill">Make OWN request</h4>
        </div>

        <form class="order" action="controller" method="post">
          <input type="hidden" name="command" value="makeOwnOrder" />
          <input type="hidden" id="minTime" value="${minTime}">
          <input type="hidden" id="maxTime" value="${maxTime}">


          <a><fmt:message key="personal_office_jsp.table.numberOfPlaces"/></a> <select required name="numberOfPlaces">
            <option></option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select> <br><br> <label for="classApartment"><fmt:message key="personal_office_jsp.table.classApartmet"/></label> <select required name="classApartment">
            <option></option>
            <option value="Standart"><fmt:message key="request_jsp.statusStandart"/></option>
            <option value="Medium"><fmt:message key="request_jsp.statusMedium"/></option>
            <option value="Luxure"><fmt:message key="request_jsp.statusLuxure"/></option>
          </select> <br><br>
          <label for="timeStart"><fmt:message key="request_jsp.timeStart"/></label>
          <input required type="text" id="StartDate" name="timeStart" readonly class="form_datetime" />
          <br><br>
          <label for="timeEnd"><fmt:message key="request_jsp.timeEnd"/></label>
          <input required type="text" id="EndDate" name="timeEnd" readonly class="form_datetime" /> <br><br>
          <button class="btn btn-primary">Ok</button>
        </form>
      </div>
    </div>
    
 <script src="style/js/script.js"></script>
  </body>


</html>




