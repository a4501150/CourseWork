$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#hotel-delete-step-2').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"post\">")
						.appendTo($('#hotel-delete-step-2'));
					$form = $("<table>").appendTo($form);
					
					$.each(responseJson, function(index, hotel) {
						var hotelText = hotel.name + ", " + hotel.address + ", " + hotel.location.city
										+ ", "	+ hotel.location.state + ", " + hotel.location.zip
						;
						$("<tr>").appendTo($form)
							.append($("<td>")
								.html(
										$("<input type=\"radio\" name=\"hotel-id\" value=\""
										+ hotel.seqNo
										+ "\">")
								))
							.append($("<td>").html($("<span>").text(hotelText)))
						;
					});
					$form.append($("<br>"));
					$("<button id=\"submit-delete-hotel\" type=\"submit\">").text("Delete").appendTo($form);
				}
			});
		}
	}
});