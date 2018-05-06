<%@include file = "../partials/header.jsp" %>
<%@ page import = "java.util.*, java.text.NumberFormat" %>
<%@ page import = "team6.entity.Hotel, team6.entity.RoomType" %>
<%
	Map<String, Double> mapHotelSales = (Map<String, Double>) request.getAttribute("map-hotel-sales");
%>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
    // Load the Visualization API and the corechart package.
    google.charts.load('current', {packages: ['corechart', 'bar']});

    // Set a callback to run when the Google Visualization API is loaded.
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {

        // Create the data table.
        var data = google.visualization.arrayToDataTable([
            ['Hotel', 'Sales',],
            <% for(Map.Entry<String, Double> entry: mapHotelSales.entrySet()) { %>
                ['<%= entry.getKey() %>', <%= entry.getValue() %>],
            <% } %>
        ]);
        
        var dataHeight = data.getNumberOfRows() * 30;
        var options = {
            title: 'Sales Chart',
            height: dataHeight,
            chartArea: {width: '50%'},
            hAxis: {
              title: 'Hotel',
              minValue: 0
            },
            vAxis: {
              title: 'Sales'
            }
          };
    
          var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    
          chart.draw(data, options);
    }
</script>

<h4><a href="<%=rootPath%>/report/sales/"><b>Back to menu</b></a></h4>
<div id="chart_div"></div>

<%@include file = "../partials/footer.jsp" %>