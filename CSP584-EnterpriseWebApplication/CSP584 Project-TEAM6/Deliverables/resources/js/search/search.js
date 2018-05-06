var searchJQ = jQuery;
searchJQ.noConflict();

searchJQ(document).ready(function(){
	searchJQ('#search-input').on("change", onChangeSearchInput);
	
	function onChangeSearchInput() {
		searchJQ("#auto-completion-search-location").empty();
		var searchVal = searchJQ("#search-input").val();
		var url = window.location.href;
		
		searchJQ.ajax({
			url: (url + "?action=searchLocation&input=" + searchVal),
			success: function(responseJson) {
				console.log("response: " + responseJson);
			}
		});
	}
});