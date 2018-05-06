$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#order-add-step-2').empty();
		$('#order-add-step-3').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#order-add-step-2'));
					$(
						"<input type=\"hidden\" name=\"location\" value=\""
						+ location_id + "\">"
					).appendTo($form);
					$form = $("<table>").appendTo($form);
					
					$.each(responseJson, function(index, hotel) {
						$("<tr>").appendTo($form)
							.append($("<td>")
								.html(
										$("<input type=\"radio\" name=\"hotel\" value=\""
										+ hotel.seqNo
										+ "\">")
								))
							.append($("<td>").html($("<span>").text(hotel.name)))
						;
					});
					$form.append($("<br>"));
					
					$("#choose-hotel").on("change", onChooseHotelChange);
				}
			});
		}
	}
	
	function onChooseHotelChange() {
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var url = window.location.href;
		$('#order-add-step-3').empty();
		
		var $roomType = null;
		$.ajax({
			url: (url + "?action=getRoomType&hotel=" + hotel_id),
			async: false,
			success: function(responseJson) {
				if(responseJson == "") {
					return;
				}
				
				$roomType = buildSelectRoomType(responseJson);
			}
		});
		
		var $form = $("<form name=\"order-form\" id=\"order-form\" method=\"post\">")
		.appendTo($('#order-add-step-3'));
		$(
			"<input type=\"hidden\" name=\"hotel-id\" value=\""
			+ hotel_id + "\">"
		).appendTo($form);
		
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer First Name"))
			.append($("<td>").html($("<input required type=\"text\" name=\"first-name\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Last Name"))
			.append($("<td>").html($("<input required type=\"text\" name=\"last-name\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Email"))
			.append($("<td>").html($("<input required type=\"email\" name=\"email\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Phone"))
			.append($("<td>").html($("<input required type=\"text\" name=\"phone\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Address"))
			.append($("<td>").html($("<input required type=\"text\" name=\"address\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("City"))
			.append($("<td>").html($("<input required type=\"text\" name=\"city\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("State"))
			.append($("<td>").html($("<input required type=\"text\" name=\"state\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Zip"))
			.append($("<td>").html($("<input required type=\"text\" name=\"zip\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Credit Card Number"))
			.append($("<td>").html($("<input required type=\"text\" name=\"cc-num\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Expiration Date"))
			.append($("<td>").html($("<input required type=\"text\" name=\"cc-exp\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Room Type"))
			.append($("<td>").html($roomType));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Check in date (MM/DD/YYYY)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"check-in\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Check out date (MM/DD/YYYY)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"check-out\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Price"))
			.append($("<td>").html($("<input required type=\"text\" name=\"price\">")));
		$("<button id=\"submit-add-hotel\" type=\"submit\">").text("Submit").appendTo($form);
		$('#order-form').on("submit", onSubmit);
	}
	
	function onSubmit() {
		var priceVal = document.forms['order-form'].elements['price'].value;
		var roomTypeVal = document.forms['order-form'].elements['room-type'].value;
		var hotelId = document.forms['order-form'].elements['hotel-id'].value;
		var url = window.location.href;
		$("#order-add-error").empty();
		var isError = false;
		
		if(roomTypeVal == '0') {
			$("<span style=\"color:red\">")
			.text("Please choose bed type.").appendTo($("#order-add-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(priceVal)) || priceVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid price value.").appendTo($("#order-add-error")).append("<br>");
			isError = true;
		}
		
		if(isError) {
			return false;
		}
		
		return true;
	}
	
	function buildSelectRoomType(responseJson) {
		$result = $("<select name=\"room-type\" id=\"room-type\">");
		$("<option value=\"0\">").text("Select room type").appendTo($result);
		$.each(responseJson, function(index, roomType) {
			$("<option value=\"" + roomType.seqNo + "\">").text(roomType.name).appendTo($result);
		});
		return $result;
	}
});