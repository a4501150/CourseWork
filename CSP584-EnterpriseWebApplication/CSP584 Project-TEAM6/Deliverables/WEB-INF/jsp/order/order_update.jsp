<%@include file="../partials/header.jsp" %>
<%@ page import = "team6.entity.RoomType, team6.entity.Location" %>
<%@ page import = "java.util.List" %>

<% List<Location> listLocation = (List<Location>) request.getAttribute("list-location"); %>
<%-- TODO: frontend --%>
<script src="<%= rootPath %>/resources/js/order/order_update.js" type="text/javascript"></script>
<div id="order-update-step-1">
	<form method="get">
		<h2>Update order: </h2>
		<select name="location" id="location">
			<option value="0">Choose a location: </option>
			<% for(Location l: listLocation) { %>
				<option value="<%= l.getSeqNo() %>"><%= l.getCity() %>, <%= l.getState() %> <%= l.getZip() %></option>
			<% } %>
		</select>
	</form>
</div>
<div id="order-update-step-2">
</div>
<div id="order-update-step-3">
</div>
<div id="order-update-step-4">
</div>
<div id="order-update-error">
</div>
<%@include file="../partials/footer.jsp" %>