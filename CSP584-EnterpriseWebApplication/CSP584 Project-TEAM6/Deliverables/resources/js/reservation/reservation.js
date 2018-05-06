$(document).ready(function(){
	$('#reservation-form').on("submit", onSubmit);
	
	// Input validation
	function onSubmit() {
		var email = documents.form['reservation-form'].elements['email'].value;
		var ccNum = document.forms['reservation-form'].elements['cc-num'].value;
		var phone = document.forms['reservation-form'].elements['phone'].value;
		var state = document.forms['reservation-form'].elements['state'].value;
		var ccExp = document.forms['reservation-form'].elements['cc-exp'].value;
		var zip = document.forms['reservation-form'].elements['zip'].value;
		var url = window.location.href;
		$("#reservation-error").empty();
		var isError = false;
		
		var patternExpDate = /^\s*(0?[1-9]|1[0-2])\/(\d\d|\d{4})\s*$/;

		if(state.length != 2) {
			$("<span style=\"color:red\">")
			.text("Please use state abbreviation (CA, IL,...)").appendTo($("#reservation-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(ccNum)) || ccNum.length != 16) {
			$("<span style=\"color:red\">")
			.text("Invalid credit card number.").appendTo($("#reservation-error")).append("<br>");
			isError = true;
		}
		if(!($.isNumeric(zip))) {
			$("<span style=\"color:red\">")
			.text("Invalid ZIP code.").appendTo($("#reservation-error")).append("<br>");
			isError = true;
		}
		if(!ccExp.match(patternExpDate)) {
			$("<span style=\"color:red\">")
			.text("Invalid expiration date.").appendTo($("#reservation-error")).append("<br>");
			isError = true;
		}
		
		if(isError) {
			return false;
		}
		
		return true;
	}
});