<%@include file="./partials/header.jsp" %>
<%@ page import = "java.util.Map" %>

<%
    Map<String, Double> topFiveHotelByRating = (Map<String, Double>) request.getAttribute("top-five-hotel-rating");
    Map<String, Integer> topZipByReview = (Map<String, Integer>) request.getAttribute("top-five-zip");
    Map<String, Integer> topFiveHotelByOrder = (Map<String, Integer>) request.getAttribute("top-five-hotel-amount");
%>

<br>
<h4>Top five rated hotel: </h4>
<% for(Map.Entry<String, Double> entry: topFiveHotelByRating.entrySet()) { %>
    <span><%= entry.getKey() %>: Average rating of <%= entry.getValue() %>/5.0<br></span>
<% } %>
<br>
<h4>Top five zip: </h4>
<% for(Map.Entry<String, Integer> entry: topZipByReview.entrySet()) { %>
    <span><%= entry.getKey() %>: <%= entry.getValue() %> reviews<br></span>
<% } %>
<br>
<h4>Top five most visited hotels: </h4>
<% for(Map.Entry<String, Integer> entry: topFiveHotelByOrder.entrySet()) { %>
    <span><%=entry.getKey() %>: <%=entry.getValue()%> visited<br></span>
<% } %>
<%@include file="./partials/footer.jsp" %>