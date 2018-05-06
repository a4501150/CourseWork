<%@include file="../partials/header.jsp" %>
<%@ page import = "team6.entity.RoomType, team6.entity.Location" %>
<%@ page import = "java.util.List" %>

<% List<Location> listLocation = (List<Location>) request.getAttribute("list-location"); %>
<%-- TODO: frontend --%>
<script src="<%= rootPath %>/resources/js/room/check_out.js" type="text/javascript"></script>
<div id="check-out-step-1">
	<form method="get">
		<h2>Check-out room for customer: </h2>
		<select name="location" id="location">
			<option value="0">Choose a location: </option>
			<% for(Location l: listLocation) { %>
				<option value="<%= l.getSeqNo() %>"><%= l.getCity() %>, <%= l.getState() %> <%= l.getZip() %></option>
			<% } %>
		</select>
	</form>
</div>
<div id="check-out-step-2">
</div>
<div id="check-out-step-3">
</div>
<div id="check-out-error">
</div>
<%@include file="../partials/footer.jsp" %>