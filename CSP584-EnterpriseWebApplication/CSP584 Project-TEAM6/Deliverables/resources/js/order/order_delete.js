$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#order-delete-step-2').empty();
		$('#order-delete-step-3').empty();
		$('#order-delete-step-4').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#order-delete-step-2'));
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
		$('#order-delete-step-3').empty();
		
		$.ajax({
			url: (url + "?action=getOrder&hotel=" + hotel_id),
			success: function(responseJson) {
				if(responseJson == "") {
					$("<span style=\"color:red\">").text("No order available for delete.").appendTo($('#order-delete-step-3'));
					return;
				}
				var $form = $("<form id=\"order-form\" method=\"post\">")
				.appendTo($('#order-delete-step-3'));
				var $selectOrder = buildSelectOrder(responseJson);
				
				$selectOrder.appendTo($form);
				$("<button id=\"submit-delete-order\" type=\"submit\">").text("Submit").appendTo($form);
			},
		});
	}
	
	function buildSelectOrder(responseJson) {
		$result = $("<select name=\"order-id\" id=\"order-id\">");
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
});