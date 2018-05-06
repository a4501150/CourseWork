<%@include file="../partials/header.jsp" %>
	<%-- TODO: frontend --%>
		<h2>Account management</h2>
        <br>
        <p><a href="<%= rootPath %>/account/createAccount">Create account</a></p>
        <br>
        <h2>Order management</h2>
        <p><a href="<%= rootPath %>/order/add"> Add order</a></p>
        <p><a href="<%= rootPath %>/order/update"> Update order</a></p>
        <p><a href="<%= rootPath %>/order/delete"> Delete order</a></p>
        <br>
        <h2>Staff function</h2>
        <p><a href="<%= rootPath %>/room/checkin">Customer check-in</a></p>
        <p><a href="<%= rootPath %>/room/checkout">Customer check-out</a></p>
		<br>
        <h2>Customer function</h2>
        <p><a href="<%= rootPath %>/order/view">My Orders</a></p>
        <p><a href="<%= rootPath %>/review">Write Review</a></p>
<%@include file="../partials/footer.jsp" %>