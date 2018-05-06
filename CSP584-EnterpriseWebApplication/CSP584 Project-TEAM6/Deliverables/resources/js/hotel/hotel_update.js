$(document).ready(function(){
	$('#location').change(onChooseLocationChange);
	
	function onChooseLocationChange() {
		var location_id = $('#location').val();
		var url = window.location.href;
		$('#hotel-update-step-2').empty();
		$('#hotel-update-step-3').empty();
		
		if(location_id > 0) {
			$.ajax({
				url: (url + "?action=getHotel&location=" + location_id),
				success: function(responseJson) {
					if(responseJson == "") {
						return;
					}
					
					var $form = $("<form id=\"choose-hotel\" method=\"get\">")
						.appendTo($('#hotel-update-step-2'));
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
		var location_id = $('#location').val();
		var location_text = $("#location option[value=\"" + location_id + "\"]").text();
		var hotel_id = $("input[name=\"hotel\"]:checked").val();
		var url = window.location.href;
		$('#hotel-update-step-3').empty();
		
		console.log(jsonHotel);
		var hotelObject = null;
		for(var i = 0; i < jsonHotel.length; i++) {
			if(jsonHotel[i].seqNo == hotel_id) {
				hotelObject = jsonHotel[i];
				break;
			}
		}
		
		var $form = $("<form method=\"post\" name=\"hotel-update-form\" id=\"hotel-update-form\">")
			.appendTo($('#hotel-update-step-3'));
		$("<input type=\"hidden\" name=\"hotel-id\" value=\"" + hotel_id + "\">").appendTo($form);
		var $table = $("<table>").appendTo($form);
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Hotel name")))
			.append($("<td>").append($("<input required size=\"50\" name=\"name\" type=\"text\" value=\"" + hotelObject.name + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("Address")))
			.append($("<td>").append($("<input required size=\"50\" name=\"address\" type=\"text\" value=\"" + hotelObject.address + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("City")))
			.append($("<td>").append($("<input required name=\"city\" type=\"text\" value=\"" + hotelObject.location.city + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("State")))
			.append($("<td>").append($("<input required size=\"2\" name=\"state\" type=\"text\" value=\"" + hotelObject.location.state + "\">")))
		;
		$("<tr>").appendTo($table)
			.append($("<td>").append($("<span>").text("ZIP Code")))
			.append($("<td>").append($("<input required size=\"5\" name=\"zip\" type=\"text\" value=\"" + hotelObject.location.zip + "\">")))
		;
		$("<tr>").appendTo($table)
		.append($("<td>").append($("<span>").text("Description")))
		.append($("<td>").append(($("<textarea required name=\"description\">").text(hotelObject.description))))
		;
		$("<button id=\"submit-update-hotel\" type=\"submit\">").text("Submit").appendTo($form);
		$('#hotel-update-form').on("submit", onSubmit);
		
	}
	
	function onSubmit() {
		var hotelId = documents.form['hotel-update-form'].elements['hotel-id'].value;
		var address = document.forms['hotel-update-form'].elements['address'].value;
		var city = document.forms['hotel-update-form'].elements['city'].value;
		var state = document.forms['hotel-update-form'].elements['state'].value;
		var zip = document.forms['hotel-update-form'].elements['zip'].value;
		var url = window.location.href;
		$("#hotel-update-error").empty();
		var isError = false;
		
		$.ajax({
			url: (url + "?action=getHotel&city=" + city + "&state=" + state + "&zip=" + zip),
			method: "GET",
			async: false,
			success: function() {
				$.each(responseJson, function(index, hotel) {
					if(hotel.seqNo != hotelId) {
						isError = true;
						$("<span style=\"color:red\">")
							.text("Another hotel already exists with given address.")
							.appendTo($("#hotel-update-error"));
					}
				});
			}
		});
		
		if(isError) {
			return false;
		}
		
		return true;
	}
});