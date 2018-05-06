$(document).ready(function(){
	$("#choose-order").on("change", onChooseOrder);
	
	function onChooseOrder() {
		var orderId = $("input[name=\"orderId\"]:checked").val();
		$("#review-div").empty();
		var url = window.location.href;
		
		$.ajax({
			url: url + "?action=getOrder&id=" + orderId,
			method: "GET",
			success: function(responseJson) {
				var $form = $("<form name=\"review-form\" id=\"review-form\" method=\"post\">")
				.appendTo($('#review-div'));
				$("<input type=\"hidden\" name=\"order-id\" value=\"" + orderId + "\">").appendTo($form);
				var $table = $("<table>").appendTo($form);
				
				var $tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Hotel"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"hotel\" size=\"50\" type=\"text\" value=\"" + responseJson.hotel.name + "\">")))
				.appendTo($tr);
				
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Address"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"address\" size=\"50\" type=\"text\" value=\"" + responseJson.hotel.address + "\">")))
				.appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("City"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"city\" size=\"20\" type=\"text\" value=\"" + responseJson.hotel.location.city + "\">")))
				.appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("State"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"state\" size=\"5\" type=\"text\" value=\"" + responseJson.hotel.location.state + "\">")))
				.appendTo($tr);
				
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Customer name"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"customer-name\" size=\"20\" type=\"text\" value=\""
						+ responseJson.customer.firstName + " " + responseJson.customer.lastName  
						+ "\">")))
				.appendTo($tr);
				
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Customer city"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"customer-city\" size=\"20\" type=\"text\" value=\"" + responseJson.customer.city + "\">")))
				.appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Checked in"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"check-in-date\" size=\"20\" type=\"text\" value=\""
						+ responseJson.checkInDateTime.date.month + "/" + responseJson.checkInDateTime.date.day + "/" + responseJson.checkInDateTime.date.year 
						+ "\">")))
				.appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span>").text("Checked out"))).appendTo($tr);
				($("<td>").append($("<input disabled name=\"check-out-date\" size=\"20\" type=\"text\" value=\""
						+ responseJson.checkOutDateTime.date.month + "/" + responseJson.checkOutDateTime.date.day + "/" + responseJson.checkOutDateTime.date.year
						+ "\">")))
				.appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span style=\"color:chocolate\">").html("<b>Rating</b>"))).appendTo($tr);
				($("<td>").append($("<input required name=\"rating\" size=\"10\" type=\"text\" placeholder=\"Out of 5\">"))).appendTo($tr);
				$tr = $("<tr>").appendTo($table);
				($("<td>").append($("<span style=\"color:chocolate\">").html("<b>Comment</b>"))).appendTo($tr);
				($("<td>").append($("<textarea name=\"comment\" placeholder=\"Comment here...\">"))).appendTo($tr);
				($("<button type=\"submit\">").text("Submit")).appendTo($form);
				
				$('#review-form').on("submit", onSubmit);
			}
		});
	}
	
	function onSubmit() {
		var ratingVal = document.forms['review-form'].elements['rating'].value;
		var url = window.location.href;
		$("#review-error").empty();
		var isError = false;
		
		if(!($.isNumeric(ratingVal)) || ratingVal <= 0) {
			$("<span style=\"color:red\">")
			.text("Invalid rating.").appendTo($("#review-error")).append("<br>");
			isError = true;
		}
		else if(ratingVal > 5) {
			$("<span style=\"color:red\">")
			.text("Rating must be from 0 to 5.").appendTo($("#review-error")).append("<br>");
			isError = true;
		}
		
		if(isError) {
			return false;
		}
		return true;
	}
});