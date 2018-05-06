$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-update-step-2').empty();
		$('#room-update-step-3').empty();
		$('#room-update-step-4').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#room-update-step-2'));
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
		$('#room-update-step-3').empty();
		$('#room-update-step-4').empty();
		
		$.ajax({
			url: (url + "?action=getRoomType&hotel=" + hotel_id),
			success: function(responseJson) {
				var $table = $("<table>").appendTo($('#room-update-step-3'));
				$.each(responseJson, function(index, roomType) {
					$("<tr>").appendTo($table)
						.append($("<td>")
							.html(
									$("<input type=\"radio\" name=\"room-type-id\" value=\""
									+ roomType.seqNo
									+ "\">")
							))
						.append($("<td>").html($("<span>").text(roomType.name)))
					;
				});
				$('input[type=radio][name=room-type-id]').on("change", function() {onChooseRoom(responseJson)});
				
			},
			error: function(responseJson) {
				$("<span style=\"color:red\">").text("No room available for update.").appendTo($('#room-update-step-3'));
			}
		});
	}
	
	function onChooseRoom(jsonRoom) {
		$('#room-update-step-4').empty();
		var checked = $("input[type=radio][name=room-type-id]:checked").val();
		var roomObject = null;
		for(var i = 0; i < jsonRoom.length; i++) {
			if(jsonRoom[i].seqNo == checked) {
				roomObject = jsonRoom[i];
			}
		}
		var url = window.location.href;
		var $bedType = null;
		$.ajax({
			url: (url + "?action=getBedType"),
			async: false,
			success: function(responseJson) {
				if(responseJson == "") {
					return;
				}
				$bedType = buildSelectBedType(responseJson, roomObject);
			}
		});
		
		var $form = $("<form name=\"room-update-form\" id=\"room-update-form\" method=\"post\">")
			.appendTo($("#room-update-step-4"));
		$("<input type=\"hidden\" name=\"room-id\" value=\"" + checked + "\">").appendTo($form);
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Name")))
			.append($("<td>").append($("<input required name=\"name\" type=\"text\" value=\"" + roomObject.name + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Bed type")))
			.append($("<td>").html($bedType));
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Bed amount")))
			.append($("<td>").append($("<input required name=\"bed-amount\" type=\"text\" size=\"4\" value=\"" + roomObject.bedAmount + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Max people")))
			.append($("<td>").append($("<input required name=\"people-no\" type=\"text\" size=\"4\" value=\"" + roomObject.peopleNo + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("View")))
			.append($("<td>").append($("<input required name=\"view\" type=\"text\" size=\"4\" value=\"" + roomObject.view + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").text("Has wifi?"))
			.append($("<td>").html($("<input type=\"checkbox\" name=\"is-wifi\"" + (roomObject.isWifi == true ? " checked" : "") + ">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Has TV?"))
			.append($("<td>").html($("<input type=\"checkbox\" name=\"is-tv\"" + (roomObject.isTV == true ? " checked" : "") + ">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Price"))
			.append($("<td>").html($("<input required type=\"text\" name=\"price\" value=\"" + roomObject.price + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("Discount"))
			.append($("<td>").html($("<input required type=\"text\" name=\"discount\" value=\"" + roomObject.discount + "\">")));
		$("<tr>").appendTo($table)
			.append($("<td>").text("List room(e.g. 413,414)"))
			.append($("<td>").html($("<input required type=\"text\" name=\"room-list\" value=\"" + roomObject.roomList + "\">")));
		$("<button id=\"submit-add-hotel\" type=\"submit\">").text("Submit").appendTo($form);
		$('#room-update-form').on("submit", onSubmit);
	}
	
	function onSubmit() {
		var nameVal = document.forms['room-update-form'].elements['name'].value;
		var bedTypeVal = document.forms['room-update-form'].elements['bed-type'].value;
		var bedAmountVal = document.forms['room-update-form'].elements['bed-amount'].value;
		var peopleVal = document.forms['room-update-form'].elements['people-no'].value;
		var priceVal = document.forms['room-update-form'].elements['price'].value;
		var discountVal = document.forms['room-update-form'].elements['discount'].value;
		var roomListVal = document.forms['room-update-form'].elements['room-list'].value;
		var roomListValSplit = document.forms['room-update-form'].elements['room-list'].value.split(",");
		var hotelId = document.forms['room-update-form'].elements['hotel-id'].value;
		var url = window.location.href;
		$("#room-update-error").empty();
		var isError = false;
		
		if(bedTypeVal == '0') {
			$("<span style=\"color:red\">")
			.text("Please choose bed type.").appendTo($("#room-update-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(bedAmountVal)) || bedAmountVal <= 0) {
			$("<span style=\"color:red\">")
			.text("Invalid bed amount.").appendTo($("#room-update-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(peopleVal)) || peopleVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid number of people.").appendTo($("#room-update-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(priceVal)) || priceVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid price value.").appendTo($("#room-update-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(discountVal)) || discountVal < 0) {
			$("<span style=\"color:red\">")
			.text("Invalid discount value.").appendTo($("#room-update-error")).append("<br>");
			isError = true;
		}
		for(var i = 0; i < roomListValSplit.length; i++) {
			if(!($.isNumeric(roomListValSplit[i])) || roomListValSplit[i] < 0) {
				$("<span style=\"color:red\">")
				.text("Invalid room list. Value must be separated by ','").appendTo($("#room-update-error")).append("<br>");
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
					.text("Room type already exists.").appendTo($("#room-update-error"));
					isError = true;
				}
				else if(responseJson != "false") {
					$("<span style=\"color:red\">")
					.text("Error adding room.").appendTo($("#room-update-error"));
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
				.text("Room " + roomExist + " have already exists.").appendTo($("#room-update-error"));
				isError = true;
			}
		});
		if(isError) {
			return false;
		}
		
		return true;
	}
	
	function buildSelectBedType(responseJson, roomObject) {
		$result = $("<select name=\"bed-type\" id=\"bed-type\">");
		$.each(responseJson, function(index, bedType) {
			var $bedTypeId = bedType.toUpperCase();
			$bedTypeId = $bedTypeId.replace(' ', '_');
			var selected = "";
			if($bedTypeId == roomObject.bedType) {
				selected = " selected";
			}
			$("<option value=\"" + $bedTypeId + "\"" + selected + ">").text(bedType).appendTo($result);
		});
		return $result;
	}
});