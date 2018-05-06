<%@include file="./partials/header.jsp" %>
<%@ page import="team6.entity.Hotel, team6.entity.RoomType" %>
<%@ page import="java.util.*, java.time.LocalDate, java.time.format.DateTimeFormatter, java.text.NumberFormat" %>
<%
	Hotel hotel = (Hotel) request.getAttribute("hotel");
	RoomType rt = (RoomType) request.getAttribute("room-type");
	LocalDate checkIn = (LocalDate) request.getAttribute("check-in-date");
	LocalDate checkOut = (LocalDate) request.getAttribute("check-out-date");
	Integer numDay = (Integer) request.getAttribute("num-day");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	
	Double total = (rt.getPrice() - rt.getDiscount()) * numDay;
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
	<div class="k2t-content no-sidebar k2t-confirm-content">
		<div class="container k2t-wrap">
			<main class="k2t-main page">
				<div class="page-entry">
					<div class="k2t-reservation-confirm">
						<div class="k2t-step-f pull-left first">
							<div class="k2t-step-done">
								<a> 1 </a>
							</div>
							<p class="k2t-p-done"> Choose Date </p>
						</div>
						<div class="k2t-step-s pull-left">
							<div class="k2t-step-done">
								<a> 2 </a>
							</div>
							<p class="k2t-p-done"> Choose Room </p>
						</div>
						<div class="k2t-step-f pull-left">
							<div class="k2t-step-current k2t-step-done">
								<a> 3 </a>
							</div>
							<p class="k2t-p-current"> Reservation </p>
						</div>
						<div class="k2t-step-fo pull-left last">
							<div class="k2t-next-step k2t-step-done">
								<a> 4 </a>
							</div>
							<p class="k2t-p-next"> Confirmation </p>
						</div>
					</div>
					<script src="<%= rootPath %>/resources/js/reservation/reservation.js" type="text/javascript"></script>
					<form method="post" id="reservation-form">
						<input type="hidden" name="total-price" value="<%= total %>">
						<input type="hidden" name="hotel-id" value="<%= hotel.getSeqNo() %>">
						<input type="hidden" name="room-type-id" value="<%= rt.getSeqNo() %>">
						<input type="hidden" name="check-in" value="<%= dtf.format(checkIn) %>">
						<input type="hidden" name="check-out" value="<%= dtf.format(checkOut) %>">
						<div class="k2t-step3-confirm w100 pull-left">
							<div class="k2t-field-info k2t-info-user pull-left">
								<p class="k2t-title-grfield"> Your Information </p>
								<div class="k2t-gr1-field pull-left">
									<div class="div-border"><input required type="text" name="first-name" placeholder="First Name" /></div>
									<div class="div-border"><input required type="email" name="email" placeholder="Email" /></div>
									<div class="div-border"><input required type="text" name="address" placeholder="Address*" /></div>
									<div class="div-border"><input required type="text" name="state" placeholder="State" /></div>
									<div class="div-border"><input required type="text" name="cc-num" size="20" placeholder="Credit Card Number" /></div>
								</div>
								<div class="k2t-gr2-field pull-right">
									<div class="div-border"><input required type="text" name="last-name" placeholder="Last Name" /></div>
									<div class="div-border"><input required type="text" name="phone" placeholder="Phone" /></div>
									<div class="div-border"><input required type="text" name="city" placeholder="City" /></div>
									<div class="div-border"><input required type="text" name="zip" placeholder="Zip Code" /></div>
									<div class="div-border"><input required type="text" name="cc-exp" placeholder="Expires(MM/YY)" /></div>
								</div>
								<div class="k2t-field-button pull-left w100">
									<!-- <a class="pull-left k2t-field-backstep" href="choose-room.jsp"> <i class="zmdi zmdi-long-arrow-left"></i> &nbsp; Back Step </a> -->
									<button id="reservation-submit" type="submit" class="pull-right k2t-field-booknow">Place Order</button>
								</div>
							</div>
							<div class="k2t-branch-confirm k2t-step3-right pull-right">
								<p class="title-confirm">Your Reservation</p>
								<p class="sub-title-confirm">Please check your information again!</p>
								<div class="k2t-entry-cr">
									<p class="k2t-confirm-p"> 
										<span class="k2t-col-f"> Hotel: </span>
										<span class="k2t-col-s">
											<%= hotel.getName() %>, <%= hotel.getAddress() %>,
											 <%= hotel.getLocation().getCity() %>, <%= hotel.getLocation().getState() %> <%= hotel.getLocation().getZip() %>   
										</span>
									</p>				
									<p class="k2t-confirm-p"> 
										<span class="k2t-col-f"> Check In: </span>
										<span class="k2t-col-s"> <%= dtf.format(checkIn) %> </span>
									</p>
									<p class="k2t-confirm-p">
										<span class="k2t-col-f"> Check Out: </span>
										<span class="k2t-col-s"> <%= dtf.format(checkOut) %> </span>
									</p>
									<p class="k2t-confirm-p"> 
										<span class="k2t-col-f"> Room: </span>
										<span class="k2t-col-s"> <%= rt.getName() %> </span>
									</p>
									<p class="k2t-confirm-p"> 
										<span class="k2t-col-f"> Max: </span>
										<span class="k2t-col-s"> <%= rt.getPeopleNo() %> People </span> 
									</p>
								</div>
								<div class="k2t-entry-cr k2t-entry-sub">
									<p class="k2t-confirm-p k2t-total"> 
										<span class="k2t-col-total"> Price </span>
										<span class="k2t-col-price k2t-col-s">
											<%= currencyFormatter.format(rt.getPrice()).replaceAll("\\$", "\\$ ") %>
										</span>
									</p>
									<% if(rt.getDiscount() > 0.0) { %>
										<p class="k2t-confirm-p k2t-vat"> 
											<span class="k2t-col-vat"> Discount </span>
											<span class="k2t-col-pvat k2t-col-s">
											 <%= currencyFormatter.format(rt.getDiscount()).replaceAll("\\$", "\\$ ") %>
											</span>
										</p>
									<% } %>
									<p class="k2t-confirm-p k2t-vat"> 
										<span class="k2t-col-vat"> Booking for: </span>
										<span class="k2t-col-pvat k2t-col-s">
										 <%= numDay %> Days
										</span>
									</p>
								</div>
								<p class="k2t-confirm-p kt2-grand"> 
									<span class="k2t-grand-total"> Grand Total </span>
									<span class="k2t-col-s">
									 <%= currencyFormatter.format(total).replaceAll("\\$", "\\$ ") %>
									 </span>
								</p>
							</div>
						</div>
					</form>
					<div id="reservation-error"></div>
				</div>
			</main>
		</div>
	</div>
</div>
<%@include file="./partials/footer.jsp" %>