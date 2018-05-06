<%@include file="./partials/header.jsp" %>
<%@ page import="java.time.LocalTime" %>
<%
	LocalTime checkInTime = (LocalTime) request.getAttribute("check-in-time");
	LocalTime checkOutTime = (LocalTime) request.getAttribute("check-out-time");
%>
		
		<div class="k2t-body">
			<div class="k2t-title-bar">
				<div class="container k2t-wrap">
					<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
						Booking Room
					</h1>
				</div>
			</div>
			<!-- .k2t-title-bar -->
			<div class="k2t-content no-sidebar k2t-reservation-content">
				<div class="container k2t-wrap">
					<main class="k2t-main page">
						<div class="page-entry">
							<div class="k2t-reservation-completed">
								<p class="reservation-done">Reservation Completed!</p>
							</div>
							<div class="k2t-reservation-info w100 pull-left">
								<p class="k2t-reservation-feedback">Your reservation details have just been sent to your email. If you have any question, please don't hesitate to contact us.</p>
								<p class="k2t-reservation-feedback">NOTE: The check-in time is at <%= checkInTime.toString() %>, and the check-out time is at <%= checkOutTime.toString() %>.</p>
								<p class="k2t-reservation-feedback" id="k2t-thanks">Thank you!</p>
								<p class="k2t-reservation-phone"><i class="zmdi zmdi-phone"></i> &nbsp; Hotline: +123 868 6868 </p>
								<p class="k2t-reservation-email"><i class="zmdi zmdi-email"></i> &nbsp; admin@CS584.com</p>
							</div>
						</div>
					</main>
				</div>
			</div>
		</div>
		
		
		
		
<%@include file="./partials/footer.jsp" %>