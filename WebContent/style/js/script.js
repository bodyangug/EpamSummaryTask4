var minTime = document.getElementById("minTime").value;
	var maxTime = document.getElementById("maxTime").value;
	
		$("#StartDate").datepicker({ dateFormat: 'yy-mm-dd' , minDate: minTime });
		$("#EndDate").datepicker({ dateFormat: 'yy-mm-dd' , minDate: maxTime });

		$("#EndDate").change(function() {
			var startDate = document.getElementById("StartDate").value;
			var endDate = document.getElementById("EndDate").value;

			if ((Date.parse(endDate) <= Date.parse(startDate))) {
				alert("End date should be greater than Start date");
				document.getElementById("EndDate").value = "";
			}
		});
		
		$("#StartDate").change(function() {
			var startDate = document.getElementById("StartDate").value;
			var endDate = document.getElementById("EndDate").value;

			if ((Date.parse(endDate) <= Date.parse(startDate))) {
				alert("End date should be greater than Start date");
				document.getElementById("EndDate").value = "";
			}
		});