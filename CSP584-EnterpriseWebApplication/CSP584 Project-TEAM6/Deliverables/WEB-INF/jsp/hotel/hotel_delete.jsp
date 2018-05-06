<%@include file="../partials/header.jsp" %>
<%@ page import = "team6.entity.RoomType, team6.entity.Location" %>
<%@ page import = "java.util.List" %>

<% List<Location> listLocation = (List<Location>) request.getAttribute("list-location"); %>
<%-- TODO: frontend --%>
<script src="<%= rootPath %>/resources/js/hotel/hotel_delete.js" type="text/javascript"></script>
<div id="hotel-delete-step-1">
	<form method="get">
		<h2>Update hotel: </h2>
		<select name="location" id="location">
			<option value="0">Choose a location: </option>
			<% for(Location l: listLocation) { %>
				<option value="<%= l.getSeqNo() %>"><%= l.getCity() %>, <%= l.getState() %> <%= l.getZip() %></option>
			<% } %>
		</select>
	</form>
</div>
<div id="hotel-delete-step-2">
</div>
<div id="hotel-delete-step-3">
</div>
<div id="hotel-delete-error">
</div>
<%@include file="../partials/footer.jsp" %>