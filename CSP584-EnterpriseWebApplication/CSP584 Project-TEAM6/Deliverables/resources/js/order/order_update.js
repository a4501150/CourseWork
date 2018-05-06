$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#order-update-step-2').empty();
		$('#order-update-step-3').empty();
		$('#order-update-step-4').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#order-update-step-2'));
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
		$('#order-update-step-3').empty();
		$('#order-update-step-4').empty();
		
		$.ajax({
			url: (url + "?action=getOrder&hotel=" + hotel_id),
			success: function(responseJson) {
				var $selectOrder = buildSelectOrder(responseJson);
				$selectOrder.appendTo($('#order-update-step-3'));
				$('#order').on("change", function() {onChooseOrder(responseJson)});
			},
			error: function(responseJson) {
				$("<span style=\"color:red\">").text("No order available for update.").appendTo($('#order-update-step-3'));
			}
		});
	}
	
	function onChooseOrder(jsonOrder) {
		$('#order-update-step-4').empty();
		
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var order_id = $("#order").val();
		var orderObject = null;
		if(order_id == 0) {
			return;
		}
		for(var i = 0; i < jsonOrder.length; i++) {
			if(jsonOrder[i].seqNo == order_id) {
				orderObject = jsonOrder[i];
				break;
			}
		}
		
		var $roomType = null;
		var url = window.location.href;
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
		
		var $orderStatus = null;
		$.ajax({
			url: (url + "?action=getOrderStatus"),
			async: false,
			success: function(responseJson) {
				if(responseJson == "") {
					return;
				}
				$orderStatus = buildSelectStatus(responseJson);
			}
		});
		
		buildOrder(orderObject, $roomType, $orderStatus);
		
	}
	
	function buildOrder(order, roomType, orderStatus) {
		var $form = $("<form name=\"order-form\" id=\"order-form\" method=\"post\">")
		.appendTo($('#order-update-step-4'));
		$(
			"<input type=\"hidden\" name=\"order-id\" value=\""
			+ order.seqNo + "\">"
		).appendTo($form);
		$(
			"<input type=\"hidden\" name=\"hotel-id\" value=\""
			+ order.hotel.seqNo + "\">"
		).appendTo($form);
		$(
			"<input type=\"hidden\" name=\"order-date\" value=\""
			+ toDateString(order.orderDate) + "\">"
		).appendTo($form);
		
		roomType.val(order.roomType.seqNo);
		orderStatus.val(order.status);
		
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer First Name"))
			.append($("<td>").html($("<input required type=\"text\" name=\"first-name\" value=\"" + order.customer.firstName + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Last Name"))
			.append($("<td>").html($("<input required type=\"text\" name=\"last-name\" value=\"" + order.customer.lastName + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Email"))
			.append($("<td>").html($("<input required type=\"email\" name=\"email\" value=\"" + order.customer.email + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Customer Phone"))
			.append($("<td>").html($("<input required type=\"text\" name=\"phone\" value=\"" + order.customer.phone + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Address"))
			.append($("<td>").html($("<input required type=\"text\" name=\"address\" value=\"" + order.customer.address + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("City"))
			.append($("<td>").html($("<input required type=\"text\" name=\"city\" value=\"" + order.customer.city + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("State"))
			.append($("<td>").html($("<input required type=\"text\" name=\"state\" value=\"" + order.customer.state + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Zip"))
			.append($("<td>").html($("<input required type=\"text\" name=\"zip\" value=\"" + order.customer.zip + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Credit Card Number"))
			.append($("<td>").html($("<input required type=\"text\" name=\"cc-num\" value=\"" + order.customer.creditCardNum + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Expiration Date"))
			.append($("<td>").html($("<input required type=\"text\" name=\"cc-exp\" value=\"" + order.customer.expirationDate + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Room Type"))
			.append($("<td>").html(roomType));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Check in date (MM/DD/YYYY)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"check-in\" value=\"" + toDateString(order.checkInDateTime.date) + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Check out date (MM/DD/YYYY)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"check-out\" value=\"" + toDateString(order.checkOutDateTime.date) + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Price"))
			.append($("<td>").html($("<input required type=\"text\" name=\"price\" value=\"" + order.price + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Order Status"))
			.append($("<td>").html(orderStatus));
		$("<button id=\"submit-update-order\" type=\"submit\">").text("Submit").appendTo($form);
	}
	
	function toDateString(date) {
		var result = "";
		var day = date.day;
		var month = date.month;
		var year = date.year;
		if(month < 10) {
			result = result + "0";
		}
		result = result + month + "/";
		if(day < 10) {
			result = result + "0";
		}
		result = result + day + "/" + year;
		
		return result;
	}
	
	function buildSelectOrder(responseJson) {
		$result = $("<select name=\"order\" id=\"order\">");
		if(responseJson == "") {
			$("<option value=\"0\">").text("No order found.").appendTo($result);
		}
		else {
			$("<option selected value=\"0\">").text("Select order below:").appendTo($result);
			$.each(responseJson, function(index, order) {
				$("<option value=\"" + order.seqNo + "\">").text("Order #" + order.seqNo + " for " + order.customer.firstName + " " + order.customer.lastName).appendTo($result);
			});
		}
		return $result;
	}
	
	function buildSelectRoomType(responseJson) {
		$result = $("<select name=\"room-type\" id=\"room-type\">");
		$("<option value=\"0\">").text("Select room type").appendTo($result);
		$.each(responseJson, function(index, roomType) {
			$("<option value=\"" + roomType.seqNo + "\">").text(roomType.name).appendTo($result);
		});
		return $result;
	}
	
	function buildSelectStatus(responseJson) {
		$result = $("<select name=\"order-status\" id=\"order-status\">");
		for(var i = 0; i < responseJson.length; i++) {
			$("<option value=\"" + responseJson[i] + "\">").text(responseJson[i]).appendTo($result);
		}
		return $result;
	}
});