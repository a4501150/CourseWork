$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#check-out-step-2').empty();
		$('#check-out-step-3').empty();
		$('#check-out-error').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#check-out-step-2'));
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
		$('#check-out-step-3').empty();
		$('#check-out-error').empty();
		
		$.ajax({
			url: url + "?action=getCheckOutOrder&hotel=" + hotel_id,
			method: "GET",
			success: function(responseJson) {
				if(responseJson == '') {
					$("<span style=\"color:red\">")
					.text("Hotel has no reservation for check-out today.").appendTo($("#check-out-error")).append("<br>");
				}
				else {
					var $form = $("<form id=\"choose-order\" method=\"post\">")
					.appendTo($('#check-out-step-3'));
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
					$("<button type=\"submit\">").text("Check-out").appendTo($form);
				}
			}
		});
	}
});