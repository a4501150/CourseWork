<%@include file="./partials/header.jsp" %>
	
	
	
	
	<!-- =========================== -->
	<div class="k2t-body">
		<div class="k2t-title-bar">
			<div class="container k2t-wrap">
				<h1 class="main-title k2t-cf7-title" style="font-size:36px;color:#283c5a;">
					Booking Room
				</h1>
			</div>
		</div><!-- .k2t-title-bar -->
		<div class="k2t-content no-sidebar k2t-confirm-content">
			<div class="container k2t-wrap">
				<main class="k2t-main page">
					<div class="page-entry">
						<div class="k2t-reservation-confirm">
							<div class="k2t-step-f pull-left first">
								<div class="k2t-step-done k2t-step-current">
									<a> 1 </a>
								</div>
								<p class="k2t-p-current"> Choose Date </p>
							</div>
							<div class="k2t-step-s pull-left">
								<div class="k2t-next-step k2t-step-done">
									<a> 2 </a>
								</div>
								<p class="k2t-p-next"> Choose Room </p>
							</div>
							<div class="k2t-step-f pull-left">
								<div class="k2t-next-step k2t-step-done">
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
						
						<div class="k2t-step1-confirm w100 pull-left">
						
							<div class="k2t-choose-date pull-left">
								<div class="k2t-field-info pull-left">
									<div class="k2t-cal-f pull-left">
								        <div class="output">
								            <div class="custom-date" id="start-date"></div>
								        </div>
								    </div>
								    <div class="k2t-cal-s pull-right">
								        <div class="output">
								            <div class="custom-date" id="end-date"></div>
								        </div>
								    </div>
								</div>
								<a href="choose-room.jsp" class="k2t-btn-step1 pull-left w100"> Next Step &nbsp; <i class="zmdi zmdi-long-arrow-right"></i></a>
							</div>
							
							<div class="k2t-branch-confirm k2t-step1-right pull-right">
								<p class="title-confirm">Book a Room</p>
								<p class="sub-title-confirm">Exclusive Online Site Offer</p>
								<div class="pull-left w100">

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
									<a href="reservation-confirm.jsp" class="k2t-btn-editbooking pull-left">Book Now</a>
								</div>
							</div>
						</div>
					</div><!-- page entry -->
				</main>
			</div>
		</div>
	</div>


<%@include file="./partials/footer.jsp" %>
