$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#check-in-step-2').empty();
		$('#check-in-step-3').empty();
		$('#check-in-step-4').empty();
		$('#check-in-error').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#check-in-step-2'));
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
					
					$("#choose-hotel").on("change", function() { onChooseHotelChange(responseJson); });
				}
			});
		}
	}
	
	function onChooseHotelChange(jsonHotel) {
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var url = window.location.href;
		$('#check-in-step-3').empty();
		$('#check-in-step-4').empty();
		$('#check-in-error').empty();
		
		$.ajax({
			url: url + "?action=getCheckInOrder&hotel=" + hotel_id,
			method: "GET",
			success: function(responseJson) {
				if(responseJson == '') {
					$("<span style=\"color:red\">")
					.text("Hotel has no reservation for today.").appendTo($("#check-in-error")).append("<br>");
				}
				else {
					var $form = $("<form id=\"choose-order\" method=\"get\">")
					.appendTo($('#check-in-step-3'));
					var $table = $("<table>").appendTo($form);
					
					$.each(responseJson, function(index, order) {
						var $tr = $("<tr>").appendTo($table);
						($("<td>").append($("<input name=\"order-id\" type=\"radio\" value=\"" + order.seqNo + "\">")))
						.appendTo($tr);
						var $text = "Order: " + order.customer.firstName + " " + order.customer.lastName + " for room: " + order.roomType.name;
						($("<td>").append($("<span>").text($text)))
						.appendTo($tr);
					});
					$("<br>").appendTo($form);
					$("#choose-order").on("change", onChooseOrderChange);
				}
			}
		});
	}
	
	function onChooseOrderChange() {
		var order_id = $("input[name=\"order-id\"]:checked").val();
		var url = window.location.href;
		$('#check-in-step-4').empty();
		$('#check-in-error').empty();
		
		$.ajax({
			url: url + "?action=getAvailableRoomNumber&order=" + order_id,
			method: "GET",
			success: function(responseJson) {
				console.log(responseJson);
				var $form = $("<form id=\"assign-room\" method=\"post\">")
				.appendTo($('#check-in-step-4'));
				$("<input type=\"hidden\" name=\"order-id\" value=\"" + order_id + "\">").appendTo($form);
				var $select = $("<select name=\"room-num\" id=\"room-num\">").appendTo($form);
				
				$.each(responseJson, function(index, roomNum) {
					($("<option value=\"" + roomNum + "\">").text("Room " + roomNum)).appendTo($select);
				});
				$("<br>").appendTo($form);
				$("<button type=\"submit\">").text("Assign this room").appendTo($form);
			}
		});
	}
});