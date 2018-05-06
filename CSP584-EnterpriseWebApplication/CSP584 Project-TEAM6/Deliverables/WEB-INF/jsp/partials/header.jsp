<%@ page import="team6.entity.User, team6.entity.Role"%>

<!DOCTYPE html>

<% 
//this is to check if user is logged in
String rootPath = request.getContextPath();
User currentUser = (User) session.getAttribute("current-user");
%>

<html>
<head>
<title>Hotel Reservation</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link sizes="16x16" href="images/upload/favicon.png" rel="icon" />
<link rel="stylesheet" type="text/css" href="<%= rootPath %>/resources/css/main.css" />
<link rel="stylesheet" type="text/css" href="<%= rootPath %>/resources/css/popular.css" />
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/css/material-design-iconic-font.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/css/js_composer.css" />
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/css/responsive.css" />
<link href="<%= rootPath %>/resources/css/jquery-ui.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css" href="<%= rootPath %>/resources/css/event.css" />
<script src="<%= rootPath %>/resources/js/jquery.min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/main.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.mousewheel.min.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/imagesloaded.pkgd.min.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/isotope.pkgd.min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.cbpBGSlideshow.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.imageloaded.min.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.infinitescroll.min.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.stellar.min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery.waypoints.min.js"
	type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/jquery-easing.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/owl.carousel.min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/ripple.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/sequence.jquery.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/sequence.jquery-min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/datepicker.min.js" type="text/javascript"></script>
<script src="<%= rootPath %>/resources/js/zoom-effect.js" type="text/javascript"></script>
<!-- SLIDER REVOLUTION 5.x SCRIPTS  -->
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/jquery.themepunch.tools.min.js?rev=5.0"></script>
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/jquery.themepunch.revolution.min.js?rev=5.0"></script>
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/extensions/revolution.extension.slideanims.min.js"></script>
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/extensions/revolution.extension.layeranimation.min.js"></script>
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/extensions/revolution.extension.navigation.min.js"></script>
<script type="text/javascript"
	src="<%= rootPath %>/resources/revolution/js/extensions/revolution.extension.parallax.min.js"></script>
<!-- SLIDER REVOLUTION 5.x CSS SETTINGS -->
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/revolution/css/layers.css">
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/revolution/css/navigation.css">
<link rel="stylesheet" type="text/css"
	href="<%= rootPath %>/resources/revolution/css/settings.css">
</head>
<body class="offcanvas-type-default offcanvas-right">
	<div id="loader-wrapper">
		<div class="loader">
			<div class="spinner">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
		</div>
	</div>
	<a href="#" class="k2t-btt" style="display: block;"></a>
	<header class="k2t-header full">
		<div class="k2t-header-mid ">
			<div class="k2t-wrap">
				<div class="k2t-row">
					<div class="col-2 left col-find-hotel"></div>
					<div class="col-8 center">

						<div class="h-element ">
							<a href="<%= rootPath %>" rel="home" class="k2t-logo">
								<h2>CS584</h2>
							</a>
						</div>


						<ul class="h-element k2t-menu " id="menu-menu-1">
							<li
								class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
								id="menu-item-1876"><a class="btn-ripple"
								href="<%= rootPath %>"> <span class="k2t-title-menu">HOME</span></a>
							</li>
						</ul>


						<ul class="h-element k2t-menu " id="menu-menu-2">

							<% if(currentUser != null) { %>
								<% if(currentUser.getRole().equals(Role.MANAGER)) { %>
									<li
										class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
										id="menu-item-1876"
									>
										<a class="btn-ripple" href="<%= rootPath %>/trending">
											<span class="k2t-title-menu">Trending</span>
										</a>
									</li>
								<% } %>
								<li
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
									id="menu-item-1876"
								>
									<a class="btn-ripple" href="<%= rootPath %>/account">
										<span class="k2t-title-menu">Account</span>
									</a>
								</li>
	
								<li
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
									id="menu-item-1876"
								>
									<a class="btn-ripple" href="<%= rootPath %>/logout">
										<span id='logout' class="k2t-title-menu">
											Logout
										</span>								
									</a>
								</li>
							<% } %>

							<% if(currentUser == null) { %>
								<li
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
									id="menu-item-1876"><a class="btn-ripple" href="<%= rootPath %>/login">
										<span class="k2t-title-menu">LOGIN</span>
								</a></li>
	
								<li
									class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1876"
									id="menu-item-1876"><a class="btn-ripple"
									href="<%= rootPath %>/register"> <span class="k2t-title-menu">REGISTER</span>
								</a></li>
							<% } %>

						</ul>
					</div>
					<div class="col-2 right col-right-language"></div>
				</div>
				<!-- .row -->
			</div>
			<!-- .k2t-wrap -->
		</div>
		<!-- .k2t-header-mid -->

		<!-- .k2t-header-m -->
	</header>