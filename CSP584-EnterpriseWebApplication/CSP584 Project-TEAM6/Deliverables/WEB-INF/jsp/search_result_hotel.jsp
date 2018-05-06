<%@include file="./partials/header.jsp" %>
<%@ page import = "team6.entity.Hotel" %>
<%@ page import = "java.util.Map, java.time.LocalDate, java.time.format.DateTimeFormatter" %>

<%
	Map<Hotel, Boolean> mapHotel = (Map<Hotel, Boolean>) request.getAttribute("search-result");
	String location = (String) request.getAttribute("query-location");
	LocalDate checkInDate = (LocalDate) request.getAttribute("query-check-in");
	LocalDate checkOutDate = (LocalDate) request.getAttribute("query-check-out");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM, yyyy");
%>
<%-- TODO: frontend --%>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/gh/kenwheeler/slick@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/gh/kenwheeler/slick@1.8.1/slick/slick-theme.css"/>
<style>
	.slick-prev:before {
	  color: red;
	}
	.slick-next:before {
	  color: red;
	}
</style>
<span>You searched for: <b><%= location %></b>: <b><%= checkInDate.format(dtf) %></b> - <b><%= checkOutDate.format(dtf) %></b></span>

<% if(mapHotel == null) { %>
	<br>
	<span style="color:red">No hotel found in this area.</span>
<% }
else { %>
	<% for(Map.Entry<Hotel, Boolean> entry: mapHotel.entrySet()) {
		request.setAttribute("hotel", entry.getKey());
		request.setAttribute("is-available", entry.getValue());
		request.setAttribute("query-check-in", checkInDate);
		request.setAttribute("query-check-out", checkOutDate);
	%>
		<jsp:include page="/WEB-INF/jsp/partials/hotel_description.jsp"/>
	<% }
} %>
<script type="text/javascript" src="//cdn.jsdelivr.net/gh/kenwheeler/slick@1.8.1/slick/slick.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('.single-item').slick();
	});
</script>
<%@include file="./partials/footer.jsp" %>