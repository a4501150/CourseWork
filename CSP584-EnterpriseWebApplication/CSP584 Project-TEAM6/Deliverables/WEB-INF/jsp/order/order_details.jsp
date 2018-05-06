<%@include file="../partials/header.jsp" %>
<%@ page import = "java.util.*, java.time.format.DateTimeFormatter, java.time.LocalDate, java.text.NumberFormat" %>
<%@ page import = "team6.entity.Order, team6.entity.Hotel" %>
<%
	Order order = (Order) request.getAttribute("queried-order");
	DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM dd, uuuu");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, uuuu hh:mm a");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	Hotel hotel = order.getHotel();
%>

<table>
	<tr>
		<td>Date ordered</td>
		<td><p><%= order.getOrderDate().format(df) %></p></td>
	</tr>
	<tr>
		<td>Room</td>
		<td><p><%= order.getRoomType().getName() %></p></td>
	</tr>
	<tr>
		<td>Hotel</td>
		<td><p><%= hotel.getName() %></p></td>
	</tr>
	<tr>
		<td>Address</td>
		<td><p><%= hotel.getAddress() %>, <%= hotel.getLocation().getCity() %>, <%= hotel.getLocation().getState() %> <%= hotel.getLocation().getZip() %></p></td>
	</tr>
	<tr>
		<td>Reservation Period</td>
		<td><p><%= order.getCheckInDateTime().toLocalDate().format(df) %> - <%= order.getCheckOutDateTime().toLocalDate().format(df) %></p></td>
	</tr>
		<td>Price</td>
		<td><p><%= currencyFormatter.format(order.getPrice()) %></p></td>
	</tr>
	<tr>
		<td>Status</td>
		<td><p><%= order.getStatus().toString() %></p></td>
	</tr>	
</table>
<form method="post" action="<%= rootPath %>/order/cancel/<%= order.getSeqNo()%>">
	<button type="submit">Cancel Order</button>
</form>
<%@include file="../partials/footer.jsp" %>