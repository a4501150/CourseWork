$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-add-step-2').empty();
		$('#room-add-step-3').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#room-add-step-2'));
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
		var location_id = $('#location').val();
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var url = window.location.href;
		$('#room-add-step-3').empty();
		
		var $bedType = null;
		$.ajax({
			url: (url + "?action=getBedType"),
			async: false,
			success: function(responseJson) {
				if(responseJson == "") {
					return;
				}
				
				$bedType = buildSelectBedType(responseJson);
			}
		});
		
		var $form = $("<form name=\"room-form\" id=\"room-form\" method=\"post\" enctype=\"multipart/form-data\">")
		.appendTo($('#room-add-step-3'));
		$(
			"<input type=\"hidden\" name=\"hotel-id\" value=\""
			+ hotel_id + "\">"
		).appendTo($form);
		
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").text("Name"))
			.append($("<td>").html($("<input required type=\"text\" name=\"name\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Bed type"))
			.append($("<td>").html($bedType));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Bed amount"))
			.append($("<td>").html($("<input required type=\"text\" size=\"4\" name=\"bed-amount\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Max people"))
			.append($("<td>").html($("<input required type=\"text\" size=\"4\" name=\"people-no\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("View"))
			.append($("<td>").html($("<input required type=\"text\" size=\"4\" name=\"view\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Has wifi?"))
			.append($("<td>").html($("<input type=\"checkbox\" name=\"is-wifi\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Has TV?"))
			.append($("<td>").html($("<input type=\"checkbox\" name=\"is-tv\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Price"))
			.append($("<td>").html($("<input required type=\"text\" name=\"price\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Discount"))
			.append($("<td>").html($("<input required type=\"text\" name=\"discount\" value=\"0\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("List room(e.g. 413,414)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"room-list\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Image"))
			.append($("<td>").html($("<input required type=\"file\" name=\"image\">")));
		$("<button id=\"submit-add-hotel\" type=\"submit\">").text("Submit").appendTo($form);
		$('#room-form').on("submit", onSubmit);
	}
	
	function onSubmit() {
		var nameVal = document.forms['room-form'].elements['name'].value;
		var bedTypeVal = document.forms['room-form'].elements['bed-type'].value;
		var bedAmountVal = document.forms['room-form'].elements['bed-amount'].value;
		var peopleVal = document.forms['room-form'].elements['people-no'].value;
		var priceVal = document.forms['room-form'].elements['price'].value;
		var discountVal = document.forms['room-form'].elements['discount'].value;
		var roomListVal = document.forms['room-form'].elements['room-list'].value;
		var roomListValSplit = document.forms['room-form'].elements['room-list'].value.split(",");
		var hotelId = document.forms['room-form'].elements['hotel-id'].value;
		var url = window.location.href;
		$("#room-add-error").empty();
		var isError = false;
		
		if(bedTypeVal == '0') {
			$("<span style=\"color:red\">")
			.text("Please choose bed type.").appendTo($("#room-add-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(bedAmountVal)) || bedAmountVal <= 0) {
			$("<span style=\"color:red\">")
			.text("Invalid bed amount.").appendTo($("#room-add-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(peopleVal)) || peopleVal <= 0) {
			$("<span style=\"color:red\">")
			.text("Invalid number of people.").appendTo($("#room-add-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(priceVal)) || priceVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid price value.").appendTo($("#room-add-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(discountVal)) || discountVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid discount value.").appendTo($("#room-add-error")).append("<br>");
			isError = true;
		}
		for(var i = 0; i < roomListValSplit.length; i++) {
			if(!($.isNumeric(roomListValSplit[i])) || roomListValSplit[i] < 0) {
				$("<span style=\"color:red\">")
				.text("Invalid room list. Value must be separated by ','").appendTo($("#room-add-error")).append("<br>");
				isError = true;
				break;
			}
		}
		
		if(isError) {
			return false;
		}
		
		$.ajax({
			url: (url + "?action=isRoomTypeExist&hotel=" + hotelId + "&roomName=" + (nameVal.replace(/ /g, '+'))),
			method: 'GET',
			async: false,
			success: function(responseJson) {
				if(responseJson == "true") {
					$("<span style=\"color:red\">")
					.text("Room type already exists.").appendTo($("#room-add-error"));
					isError = true;
				}
				else if(responseJson != "false") {
					$("<span style=\"color:red\">")
					.text("Error adding room.").appendTo($("#room-add-error"));
					isError = true;
				}
			}
		});
		$.ajax({
			url: (url + "?action=isRoomExist&hotel=" + hotelId + "&roomList=" + (roomListVal.replace(/,/g, '+'))),
			method: 'GET',
			async: false,
			success: function(responseJson) {
				var roomExist = "";
				$.each(responseJson, function(index, roomNum) {
					roomExist = roomExist + roomNum;
					if(index < responseJson.length - 1) {
						roomExist = roomExist + ", ";
					}
				});
				$("<span style=\"color:red\">")
				.text("Room " + roomExist + " have already exists.").appendTo($("#room-add-error"));
				isError = true;
			}
		});
		if(isError) {
			return false;
		}
		return true;
	}
	
	function buildSelectBedType(responseJson) {
		$result = $("<select name=\"bed-type\" id=\"bed-type\">");
		$("<option value=\"0\">").text("Select bed type").appendTo($result);
		$.each(responseJson, function(index, bedType) {
			var $bedTypeId = bedType.toUpperCase();
			$bedTypeId = $bedTypeId.replace(' ', '_');
			$("<option value=\"" + $bedTypeId + "\">").text(bedType).appendTo($result);
		});
		return $result;
	}
});