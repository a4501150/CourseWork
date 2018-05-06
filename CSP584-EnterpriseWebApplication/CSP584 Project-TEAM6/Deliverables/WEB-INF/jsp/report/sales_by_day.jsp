<%@include file = "../partials/header.jsp" %>
<%@ page import = "java.util.*, java.text.NumberFormat, java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%@ page import = "team6.entity.Hotel, team6.entity.Order, team6.entity.RoomType" %>
<%
	Map<LocalDate, Double> mapSalesByDay = (Map<LocalDate, Double>) request.getAttribute("map-sales-by-day");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, uuuu");
%>

<h4><a href="<%=rootPath%>/report/sales/"><b>Back to menu</b></a></h4>

<table>
	<tr>
		<th>Date</th>
		<th>Total sales</th>
	</tr>
	<% for(Map.Entry<LocalDate, Double> entry: mapSalesByDay.entrySet()) { %>
		<tr>
			<td>
				<%= dtf.format(entry.getKey()) %>
			</td>
			<td>
				<%= currencyFormatter.format(entry.getValue()) %>
			</td>
		</tr>
	<% } %>
</table>

<%@include file = "../partials/footer.jsp" %>