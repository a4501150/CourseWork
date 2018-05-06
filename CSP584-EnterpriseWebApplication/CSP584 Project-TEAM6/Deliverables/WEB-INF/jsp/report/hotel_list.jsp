<%@include file = "../partials/header.jsp" %>
<%@ page import = "java.util.*, java.text.NumberFormat" %>
<%@ page import = "team6.entity.Hotel, team6.entity.RoomType" %>
<%
	Map<Hotel, List<RoomType>> mapHotelRoomType = (Map<Hotel, List<RoomType>>) request.getAttribute("map-hotel-room-type");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
%>

<h4><a href="<%=rootPath%>/report/hotel/"><b>Back to menu</b></a></h4>

<table>
	<tr>
		<th>Hotel</th>
		<th>Room type</th>
		<th>Price</th>
		<th>Discount</th>
		<th>Room numbers</th>
	</tr>
	<% for(Map.Entry<Hotel, List<RoomType>> entry: mapHotelRoomType.entrySet()) {
		Hotel h = entry.getKey();
		List<RoomType> listRoomType = entry.getValue();
		for(int i = 0; i < listRoomType.size(); i++) { 
			RoomType rt = listRoomType.get(i); %>
			<tr>
				
				<th>
					<% if(i == 0) { %>
						<span><%= h.getName() %></span>
						<br>
						<span><%= h.getAddress() %>, <%= h.getLocation().getCity() %>, <%= h.getLocation().getState() %>, <%= h.getLocation().getZip() %></span>
					<% } %>
				</th>
				
				<th><%= rt.getName() %></th>
				<th><%= currencyFormatter.format(rt.getPrice()) %></th>
				<th><%= currencyFormatter.format(rt.getDiscount()) %></th>
				<%
					StringBuilder sb = new StringBuilder();
					for(int j = 0; j < rt.getRoomList().size(); j++) {
						
						sb.append(rt.getRoomList().get(j));
						if(j < rt.getRoomList().size() - 1) {
							sb.append(", ");
						}
					}
				%>
				<th><%= sb.toString() %></th>
			</tr>
		<% }
	 } %>
</table>

<%@include file = "../partials/footer.jsp" %>