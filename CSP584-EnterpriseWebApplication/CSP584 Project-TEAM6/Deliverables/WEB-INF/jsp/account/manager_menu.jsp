<%@include file="../partials/header.jsp" %>
	<%-- TODO: frontend --%>
		<h2>Account management</h2>
        <br>
        <p><a href="<%= rootPath %>/account/createAccount">Create account</a></p>
        <br>
        <h2>Hotel management</h2>
        <br>
        <p><a href="<%= rootPath %>/hotel/add"> Add hotel </a></p>
        <p><a href="<%= rootPath %>/hotel/update"> Update hotel </a></p>
        <p><a href="<%= rootPath %>/hotel/delete"> Delete hotel </a></p>
        <br>
        <h2>Room type management</h2>
        <p><a href="<%= rootPath %>/room/add"> Add room type</a></p>
        <p><a href="<%= rootPath %>/room/update"> Update room type</a></p>
        <p><a href="<%= rootPath %>/room/delete"> Delete room type</a></p>
        <br>
        <h2>Order management</h2>
        <p><a href="<%= rootPath %>/order/add"> Add order</a></p>
        <p><a href="<%= rootPath %>/order/update"> Update order</a></p>
        <p><a href="<%= rootPath %>/order/delete"> Delete order</a></p>
        <br>
        <h2>Analytics</h2>
        <p><a href="<%= rootPath %>/report/hotel"> Hotels report</a></p>
        <p><a href="<%= rootPath %>/report/sales"> Sales report</a></p>
        <br>
        <h2>Staff function</h2>
        <p><a href="<%= rootPath %>/room/checkin">Customer check-in</a></p>
        <p><a href="<%= rootPath %>/room/checkout">Customer check-out</a></p>
		<br>
        <h2>Customer function</h2>
        <p><a href="<%= rootPath %>/order/view">My Orders</a></p>
        <p><a href="<%= rootPath %>/review">Write Review</a></p>
<%@include file="../partials/footer.jsp" %>