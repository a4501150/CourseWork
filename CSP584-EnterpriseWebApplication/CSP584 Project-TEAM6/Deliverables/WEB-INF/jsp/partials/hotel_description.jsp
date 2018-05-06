<%@ page import = "team6.entity.Hotel" %>
<%@ page import = "java.time.LocalDate, java.time.format.DateTimeFormatter" %>
<%
	String rootPath = request.getContextPath();
	Hotel hotel = (Hotel) request.getAttribute("hotel");
	Boolean isAvail = (Boolean) request.getAttribute("is-available");
	LocalDate checkInDate = (LocalDate) request.getAttribute("query-check-in");
	LocalDate checkOutDate = (LocalDate) request.getAttribute("query-check-out");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyy");
	
%>


<div class="hotel-description">
  <div class="col-1">
  	<div class="single-item">
  		<% for(String image: hotel.getListImage()) { %>
	  		<div>
	    		<img class="hotel-images" src="<%= rootPath %>/resources/images/upload/hotel/<%= hotel.getSeqNo() %>/<%= image %>">
	    	</div>
    	<% } %>
    </div>
  </div>
   <div class="col-2">
    <span>
    	<a href="<%= rootPath %>/hotel/<%= hotel.getSeqNo() %>">
    		<%= hotel.getName() %>
   		</a>
 	</span>
    <p><%= hotel.getDescription() %></p>
  </div>
  <div class="col-3">
  	<% if(isAvail) { %>
  	<form method="get" action="<%= rootPath %>/reserve">
  		<input type="hidden" name="action" value="chooseRoom">
  		<input type="hidden" name="hotel" value="<%= hotel.getSeqNo() %>">
  		<input type="hidden" name="checkIn" value="<%= dtf.format(checkInDate) %>">
  		<input type="hidden" name="checkOut" value="<%= dtf.format(checkOutDate) %>">
  		<button type="submit">Choose</button>
  	</form>
	<% }
  	else { %>
  		<span style="color:red">Sold out for these dates</span>
	<% } %>
  </div>
</div>

