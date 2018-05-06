<%@include file="./partials/header.jsp" %>
<%@ page import="java.util.*, java.text.NumberFormat" %>
<%@ page import="team6.entity.RoomType, team6.entity.Deal, team6.entity.Hotel" %>

<%
	Map<RoomType, Boolean> mapRoomTypeAvailable = (Map<RoomType, Boolean>) request.getAttribute("map-room-type");
	String hotelId = (String) request.getAttribute("hotel-id");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	String checkInDate = (String) request.getAttribute("check-in");
	String checkOutDate = (String) request.getAttribute("check-out");
	Map<Deal, Hotel> mapDeal = (Map<Deal, Hotel>) application.getAttribute("map-deal");
	Deal deal = null;
	if(mapDeal != null) {
		for(Map.Entry<Deal, Hotel> entry: mapDeal.entrySet()) {
			if(entry.getValue().getSeqNo().equals(Integer.valueOf(hotelId))) {
				deal = entry.getKey();
				break;
			}
		}
	}
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
									<div class="k2t-step-done k2t-step-current">
										<a> 2 </a>
									</div>
									<p class="k2t-p-current"> Choose Room </p>
								</div>
								<div class="k2t-step-f pull-left">
									<div class="k2t-step-done k2t-next-step">
										<a> 3 </a>
									</div>
									<p class="k2t-p-next"> Reservation </p>
								</div>
								<div class="k2t-step-fo pull-left last">
									<div class="k2t-next-step k2t-step-done">
										<a> 4 </a>
									</div>
									<p class="k2t-p-next"> Confirmation </p>
								</div>
							</div>
							<% if(deal != null) { %>
								<div class="k2t-content no-sidebar">
									<span style="font-size:150%;color:red">FOUND DEAL MATCH:</span>
									<br>
									<span style="font-size:125%;color:chocolate"><%= deal.getTweet() %></span>
								</div>
							<% } %>
							<div class="k2t-step-select-room w100 pull-left">
								<div class="event-listing-masonry-wrapper">
									<div class="event-listing-masonrys columns-3" style="position: relative; height: 1530px;">
										<% for(Map.Entry<RoomType, Boolean> entry: mapRoomTypeAvailable.entrySet()) {
											RoomType rt = entry.getKey();
											Boolean isAvail = entry.getValue();%>
											<article class="masonry-item event-grid">
												<div class="inner">
													<div class="post-thumbnails">
														<img width="370" height="370"
															 src="<%= rootPath %>/resources/images/upload/room/<%= rt.getSeqNo() %>/<%= rt.getImage() %>" 
															 class="attachment-thumb_330x330 wp-post-image" 
															 alt="<%= rt.getName() %>" />					
														<div class="info">
															<div class="product-price">
																<span class="price">
																	<% if(rt.getDiscount() > 0.0) { %>
																		<span class="amount">
																			<del><%= currencyFormatter.format(rt.getPrice()).replaceAll("\\$", "\\$&nbsp;") %></del>
																		</span>
																		<span class="amount">
																			<%= currencyFormatter.format(rt.getPrice() - rt.getDiscount()).replaceAll("\\$", "\\$&nbsp;") %>
																		</span>
																	<% }
																	else { %>
																		<span class="amount">
																			<%= currencyFormatter.format(rt.getPrice()).replaceAll("\\$", "\\$&nbsp;") %>
																		</span>
																	<% } %>
																</span>
															</div>
															<h3 class="title title-room">
																<a 
																href="<%= rootPath %>/reserve?action=reservation&hotel=<%= hotelId %>&room=<%= rt.getSeqNo() %>
																	&checkIn=<%= checkInDate %>&checkOut=<%= checkOutDate %>">
																<%= rt.getName() %>
																</a>
															</h3>
															<p class="attribute">
																<i class="zmdi zmdi-check"></i>
																<b>Bed:</b>&nbsp; <%= rt.getBedAmount() %> <%= rt.getBedType().getDisplayString() %>
															</p>
															<p class="attribute">
																<i class="zmdi zmdi-check"></i>
																<b>Max:</b>&nbsp; <%= rt.getPeopleNo() %> People
															</p>
															<p class="attribute">
																<i class="zmdi zmdi-check"></i>
																<b>View:</b>&nbsp; <%= rt.getView() %>							
															</p>
															<% if(isAvail) { %>
																<div class="div-read-more">
																	<a class="" 
																	href="<%= rootPath %>/reserve?action=reservation&hotel=<%= hotelId %>&room=<%= rt.getSeqNo() %>
																	&checkIn=<%= checkInDate %>&checkOut=<%= checkOutDate %>"
																	 title="<%= rt.getName()%>">Book This Room</a>
																</div>
															<% }
															else { %>
																<div class="div-read-more">
																	<b style="color:red">Sold out for those dates.</b>
																</div>
															<% } %>
														</div>
													</div>
												</div>
											</article>
										<% } %>
									</div>
									<!--event-listing-masonry-->
								</div>
								<div class="k2t-field-button pull-left w100">
									<a class="pull-left w100 k2t-field-backstep" href="choose-date.jsp"> <i class="zmdi zmdi-long-arrow-left"></i> &nbsp; Back Step </a>
								</div>
							</div>
						</div>
					</main>
				</div>
			</div>
		</div>

		
<%@include file="./partials/footer.jsp" %>
