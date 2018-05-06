$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#room-delete-step-2').empty();
		$('#room-delete-step-3').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#room-delete-step-2'));
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
		$('#room-delete-step-3').empty();
		
		$.ajax({
			url: (url + "?action=getRoomType&hotel=" + hotel_id),
			success: function(responseJson) {
				var $form = $("<form name=\"room-delete-form\" method=\"post\">").appendTo($('#room-delete-step-3'));
				var $table = $("<table>").appendTo($form);
				$.each(responseJson, function(index, room) {
					$("<tr>").appendTo($table)
						.append($("<td>")
							.html(
									$("<input type=\"radio\" name=\"room-type-id\" value=\""
									+ room.seqNo
									+ "\">")
							))
						.append($("<td>").html($("<span>").text(room.name)))
					;
				});
				$("<button id=\"submit-delete-hotel\" type=\"submit\">").text("Delete").appendTo($form);
				
			},
			error: function(responseJson) {
				$("<span style=\"color:red\">").text("No room available for delete.").appendTo($('#room-delete-step-3'));
			}
		});
	}
});