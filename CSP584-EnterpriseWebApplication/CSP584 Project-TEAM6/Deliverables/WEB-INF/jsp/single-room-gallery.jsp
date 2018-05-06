<%@include file="./partials/header.jsp" %>

		<script type="text/javascript">
			$(window).load(function() {
			    var sync1 = $(".k2t-thumb-gallery");
			    var sync2 = $("#inavigation");
			    sync1.owlCarousel({
			        navigationText: [
			            "<i class='k2t k2t-arrow-prev'></i>",
			            "<i class='k2t k2t-arrow-next'></i>"
			        ],
			        items: 1,
			        autoPlay: 3000,
			        singleItem: true,
			        slideSpeed: 1000,
			        navigation: true,
			        pagination: true,
			        afterAction: syncPosition,
			        responsiveRefreshRate: 200,
			    });
			    sync2.owlCarousel({
			        loop: true,
			        items: 6,
			        autoPlay: 3000,
			        margin: 10,
			        itemsDesktop : [1000,6],
			        itemsDesktopSmall : [900,6],
			        itemsTablet: [600,6],
			        itemsMobile : [480,6],
			        center: true,
			        pagination: false,
			        responsiveRefreshRate: 100,
			        afterInit: function(el) {
			            el.find(".owl-item").eq(0).addClass("synced");
			        }
			    });

			    function syncPosition(el) {
			        var current = this.currentItem;
			        $("#inavigation")
			            .find(".owl-item")
			            .removeClass("synced")
			            .eq(current)
			            .addClass("synced")
			        if ($("#inavigation").data("owlCarousel") !== undefined) {
			            center(current)
			        }
			    }

			    $("#inavigation").on("click", ".owl-item", function(e) {
			        e.preventDefault();
			        var number = $(this).data("owlItem");
			        sync1.trigger("owl.goTo", number);
			    });

			    function center(number) {
			        var sync2visible = sync2.data("owlCarousel").owl.visibleItems;
			        var num = number;
			        var found = false;
			        for (var i in sync2visible) {
			            if (num === sync2visible[i]) {
			                var found = true;
			            }
			        }

			        if (found === false) {
			            if (num > sync2visible[sync2visible.length - 1]) {
			                sync2.trigger("owl.goTo", num - sync2visible.length + 2)
			            } else {
			                if (num - 1 === -1) {
			                    num = 0;
			                }
			                sync2.trigger("owl.goTo", num);
			            }
			        } else if (num === sync2visible[sync2visible.length - 1]) {
			            sync2.trigger("owl.goTo", sync2visible[1])
			        } else if (num === sync2visible[0]) {
			            sync2.trigger("owl.goTo", num - 1)
			        }
			    }
			});
		</script>




		<div class="k2t-body">
			<div class="k2t-content k2t-entry-event no-sidebar">
				<div class="k2t-wrap">
					<main class="k2t-main">
						<div class="post-2089 post-k-event type-post-k-event status-publish has-post-thumbnail hentry k-event-category-3-grid k-event-category-mix-grid" id="main-col">
							<div class="k2t-row k2t-rows-inner k2t-room-detail">
								<div class="entry-content pull-left">
									<div class="k2t-title-room pull-left w100">
										<!-- Title -->
										<div class="event-title">
											<h3 class="h">King Suite Room						
												<span class="price pull-right">
												<span class="amount">$&nbsp;1,350</span>
												<span class="amount">/ night</span>
												</span>
											</h3>
										</div>
										<!-- END Title -->
									</div>
									<div class="k2t-event-gallery pull-left w100">
										<div class="k2t-thumb-gallery">
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/Dusk-Exterior.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/500____college-hotelp1diapo1_718.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/58447-room-type-adina-apartment-hotel-sydney-one-bedroom-queen.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/7030704-casa-monica-hotel.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/mos2-14209716938.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/Moscow_Izmailovo_hotel_complex_evening_14575121847.jpg" />
											</div>
											<div class="item">
												<img width="710" height="455" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="attachment-thumb_710x455 wp-post-image" src="<%= rootPath %>/resources/images/upload/7030704-casa-monica-hotel.jpg" />
											</div>
										</div>
										<div id="inavigation">
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/Dusk-Exterior-v2-23024px1-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/500____college-hotelp1diapo1_7181-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/58447-room-type-adina-apartment-hotel-sydney-one-bedroom-queen1-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/7030704-casa-monica-hotel1-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/mos2-14209716938-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/Moscow_Izmailovo_hotel_complex_evening_14575121847-150x150.jpg" />
											</div>
											<div class="item">
												<img width="110" height="110" alt="eiffel-tower-paris-las-vegas-hotel-casino-wallpaper-1056" class="" src="<%= rootPath %>/resources/images/upload/7030704-casa-monica-hotel1-150x150.jpg" />
											</div>
										</div>
									</div>
									<h3 class="event-item-title"> Services &amp; Functions </h3>
									<div class="col-6">
										<p class="attribute">
											<i class="zmdi zmdi-check"></i>
											<b>Bed:</b> 1 King Bed
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-check"></i>
											<b>Max:</b> 4 People
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-check"></i>
											<b>View:</b> City
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-check"></i>
											<b>Room size:</b> 82 sqm
										</p>
									</div>
									<div class="col-6">
										<p class="attribute">
											<i class="zmdi zmdi-check"></i>
											<b>Room:</b> 3
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-check"></i> <b>Bathroom:</b> 2
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-wifi-alt"></i>
											Free wifi
										</p>
										<p class="attribute">
											<i class="zmdi zmdi-tv"></i>
											TV in room
										</p>
									</div>
									<!-- Description -->
									<div class="event-description pull-left">
										<h3 class="h event-item-title">Description</h3>
										<div class="content">
											<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit apnim id est laborum.
											</p>
										</div>
									</div>
									<!-- Event Calendar Import -->
									<div class="btn btn-booking pull-left">
										<a class="event-link" target="_blank" href="reservation.jsp">Booking This Room</a>
									</div>
								</div>
								<div class="event-entry-meta pull-right">
									<div class="k2t-branch-confirm w100 k2t-step1-right pull-right">
										<p class="title-confirm">Book a Room</p>
										<p class="sub-title-confirm">Exclusive Online Site Offer</p>
										<div class="pull-left w100">
											<span class="relative pull-left w100 k2t-step1-span">
												<select class="k2t-step1-select pull-left w100">
													<option>Hotel Branches</option>
													<option>London</option>
													<option>Paris</option>
													<option>New York</option>
												</select>
											</span>
											<span class="pull-left w100 relative k2t-step1-input">
											<input class="k2t-select-checkin pull-left w100" placeholder="Check In" />
											</span>
											<span class="pull-left w100 relative k2t-step1-input">
											<input class="k2t-select-checkout pull-left w100" placeholder="Check Out" />
											</span>
											<span class="relative pull-left w100 k2t-step1-span">
												<select class="k2t-step1-select pull-left w100 relative">
													<option>Room</option>
													<option>1</option>
													<option>2</option>
													<option>3</option>
												</select>
											</span>
											<div class="pull-left w100 k2t-select-people">
												<span class="relative pull-left k2t-step1-span">
													<select class="k2t-step1-select pull-left w100">
														<option>Adult</option>
														<option>0</option>
														<option>1</option>
														<option>2</option>
														<option>3</option>
														<option>4</option>
														<option>5</option>
														<option>6</option>
													</select>
												</span>
												<span class="relative pull-right k2t-step1-span">
													<select class="k2t-step1-select pull-right w100">
														<option>Children</option>
														<option>0</option>
														<option>1</option>
														<option>2</option>
														<option>3</option>
														<option>4</option>
														<option>5</option>
														<option>6</option>
													</select>
												</span>
											</div>
										</div>
										<div class="pull-left w100" style="padding:0 20%">
											<a href="choose-room.jsp" class="k2t-btn-editbooking pull-left">Book Now</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- #main-col -->
					</main>
					<!-- .k2t-blog -->
				</div>
				<!-- .k2t-wrap -->
			</div>
			<!-- .k2t-content -->
		</div>

		
		
<%@include file="./partials/footer.jsp" %>