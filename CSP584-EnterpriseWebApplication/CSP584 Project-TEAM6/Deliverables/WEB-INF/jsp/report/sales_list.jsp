<%@include file = "../partials/header.jsp" %>
<%@ page import = "java.util.*, java.text.NumberFormat" %>
<%@ page import = "team6.entity.Hotel, team6.entity.Order, team6.entity.RoomType" %>
<%
	Map<Hotel, List<Order>> mapHotelOrder = (Map<Hotel, List<Order>>) request.getAttribute("map-hotel-order");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
%>

<h4><a href="<%=rootPath%>/report/sales/"><b>Back to menu</b></a></h4>

<table>
	<tr>
		<th>Hotel</th>
		<th>Number of reservations</th>
		<th>Total sales</th>
	</tr>
	<% for(Map.Entry<Hotel, List<Order>> entry: mapHotelOrder.entrySet()) {
		Hotel h = entry.getKey();
		List<Order> listOrder = entry.getValue(); %>
		<tr>
			<td>
				<span><%= h.getName() %></span>
				<br>
				<span><%= h.getAddress() %>, <%= h.getLocation().getCity() %>, <%= h.getLocation().getState() %>, <%= h.getLocation().getZip() %></span>
			</td>
			<td>
				<%= listOrder.size() %>				
			</td>
			<%
				double totalSales = 0;
				for(Order o: listOrder) {
					totalSales += o.getPrice().doubleValue();
				}
			%>
			<td>
				<%= currencyFormatter.format(totalSales) %>
			</td>
		</tr>
	<% } %>
</table>

<%@include file = "../partials/footer.jsp" %>