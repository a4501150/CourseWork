/**
 * Custom script for Lyon
 *
 * @package Lyon
 * @author  SunriseTheme
 * @link	http://www.sunrisetheme.com
 */
 
(function($) {
	"use strict";

	$(function() {

		// remove envato bar 
		if (top != self) top.location.replace(self.location.href);

		/*  [ Detecting Mobile Devices ]
		- - - - - - - - - - - - - - - - - - - - */
		var isMobile = {
			Android: function() {
				return navigator.userAgent.match(/Android/i);
			},
			BlackBerry: function() {
				return navigator.userAgent.match(/BlackBerry/i);
			},
			iOS: function() {
				return navigator.userAgent.match(/iPhone|iPad|iPod/i);
			},
			Opera: function() {
				return navigator.userAgent.match(/Opera Mini/i);
			},
			Windows: function() {
				return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
			},
			Desktop: function() {
				return window.innerWidth <= 960;
			},
			any: function() {
				return ( isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows() || isMobile.Desktop() );
			}
		}

		/*  [ Button click effect ]
		- - - - - - - - - - - - - - - - - - - - */
		var element, ripple, d, x, y;
		var i = 1;
		var queue = [];
		var mainParams = {"offcanvas_turnon":"1","sticky_menu":"sticky_mid","vertical_menu":"0"};
		$(document).on('click', '.btn-ripple', function (e) {
			element = $(this);

			// remove old items from queue and DOM
			// allow max 5 to be loaded
			if (queue.length > 5) {
				$('._' + queue.shift()).remove();
			}

			// Assume user can't click more than 1000 times / second
			//terrible overflow protection.
			if (i > 1000) {
				i = 0;
			}

			// add next item to queue
			i++;
			queue.push(i);

			// Build element
			element.append('<span class="ripple _' + i + '"></span>');
			ripple = element.find('._' + i);

			// Make it big enough to cover whole parent
			if(!ripple.height() && !ripple.width()) {
				d = Math.max(element.outerWidth(), element.outerHeight());
				ripple.css({height: d, width: d});
			}

			// Get origin
			x = e.pageX - element.offset().left - ripple.width() / 2;
			y = e.pageY - element.offset().top - ripple.height() / 2 ;

			// Set location and animate
			ripple.css({top: y+'px', left: x+'px'}).addClass('animate');
		});

		/*  [ Vertical header ]
		- - - - - - - - - - - - - - - - - - - - */
		$('#showPushMenu').on('click', function() {
			if ( mainParams.vertical_menu == '1' ){
				$('body').toggleClass('vertical-close');
			}
			return false;
		});

		/*  [ Custom RTL Menu ]
		- - - - - - - - - - - - - - - - - - - - */
		if ( ! isMobile.any() ) {
			$( '.sub-menu li' ).on( 'hover', function () {
				var sub_menu = $( this ).find( ' > .sub-menu' );
				if ( sub_menu.length ) {
					if ( sub_menu.outerWidth() > ( $( window ).outerWidth() - sub_menu.offset().left ) ) {
						$( this ).addClass( 'menu-rtl' );
					}
				}
			});
		}

		/*  [ Back to top ]
		- - - - - - - - - - - - - - - - - - - - */
		$( '.k2t-btt' ).on('click',function () {
			$("html, body").animate({
				scrollTop: 0
			}, 500);
			return false;
		});


		/*  [ Offcanvas Sidebar ]
		- - - - - - - - - - - - - - - - - - - - */
		$( '.open-sidebar' ).on( 'click', function() {
			if ( mainParams.offcanvas_turnon == '1' ){
				$( 'body' ).toggleClass( 'offcanvas-open' );
				$( '.offcanvas-sidebar' ).toggleClass( 'is-open' );
				$(this).toggleClass( 'close-sidebar' );
			}
			return false;
		});

		$( '.k2t-body' ).on( 'click', function(e) {
			if ($(e.target).hasClass( 'open-sidebar' ) || $(e.target).closest( '.open-sidebar' ).length > 0 ) {
				return;
			}
			$( 'body' ).removeClass( 'offcanvas-open' );
			$( '.offcanvas-sidebar' ).removeClass( 'is-open' );
			$( '.open-sidebar' ).removeClass( 'close-sidebar' );
		});

		$('.offcanvas-sidebar .k2t-sidebar ul li').on('click',function(){
			if ($(this).find('ul') && $(this).find('ul').hasClass('k2t-active')){
				$(this).find('ul').removeClass('k2t-active');
				$(this).removeClass('k2t-active');
			}else {
				$(this).find('ul').addClass('k2t-active');
				$(this).addClass('k2t-active');
			}
		});

		/*  [ Search Box ]
		- - - - - - - - - - - - - - - - - - - - */

		/*  [ Menu Responsive ]
		- - - - - - - - - - - - - - - - - - - - */
		// Mobile - open lateral menu clicking on the menu icon

		/*  [ Menu Responsive ]
		- - - - - - - - - - - - - - - - - - - - */
		jQuery('.mobile-menu-toggle').on('click',function(e) {
	        jQuery('body').toggleClass('enable-mobile-menu');
			jQuery('body').removeClass('scroll-mobile-menu');
	    });

		// Isotope
		if ( $().masonry && $().isotope && $().imagesLoaded ) {
			
			$( '.k2t-isotope-wrapper' ).each( function() {

				var $this = $(this);
				var $container = $this.find('.k2t-isotope-container');
				
				// initialize Isotope + Masonry after all images have loaded  
				$this.imagesLoaded( function() {

					$container.addClass('loaded').find('.isotope-selector').find('.article-inner');
					var isotope_args = {
						itemSelector: '.isotope-selector',
						transitionDuration	: '.55s',
						masonry: {
							gutter	: '.gutter-sizer',
							//columnWidth: 
						},
					};
					if ($this.hasClass('isotope-grid')) {
						isotope_args['layoutMode'] = 'fitRows';
					}
					if ($this.hasClass('isotope-no-padding')) {
						delete isotope_args.masonry.gutter; //true
					}
					if ($this.hasClass('isotope-free')) {
						isotope_args.masonry['columnWidth'] = '.width-1';
					}
					var $grid = $container.isotope(isotope_args);
					
					// animation
					var animation = $grid.data('animation');
					if (animation = true) {
						$container.find('.isotope-selector').find('.article-inner').each(function(){
							var $this=$(this);
							$this.parent().one('inview', function(event, isInView, visiblePartX, visiblePartY) {
								if (isInView) {
									$this.addClass('run_animation');
								} // inview						  
							});// bind
						}); // each
							
					} // endif animation
					
					// filter items when filter link is clicked
					$this.find('.cd-dropdown').find('span').on('click',function(){
						if ( $(this).parent().parent().parent().hasClass('cd-active') ){
							var selector = $(this).attr('class');
							$container.isotope({
								filter: selector,
							});
						}
					});
					
				}); // imagesLoaded
				
			}); // each .k2t-isotope-wrapper
		} // if isotope

		/*  [ Performs a smooth page scroll to an anchor ]
		- - - - - - - - - - - - - - - - - - - - */
		$('.scroll').on('click',function() {
			if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
				var target = $(this.hash),
				headerH = $('.k2t-header').outerHeight();

				target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
				if (target.length) {
					$('html,body').animate({
						scrollTop: target.offset().top - 170 + "px"
					}, 1200);
					return false;
			   }
		   }
		});

		var $logoImg = $('.k2t-logo img');
		if ( $logoImg.css( 'min-height' ) == '1px' ) {
			$logoImg.attr( 'src', $logoImg.attr( 'src' ).replace( 'logo.png', 'logo@2x.png' ) );
		}

		if(jQuery('#commentform > p').length){
			jQuery('#commentform > p').each(function(){
				$('#commentform > p input').focus(function(){
					$(this).closest('p').addClass('focus');
				});
				$('#commentform > p textarea').focus(function(){
					$(this).closest('p').addClass('focus');
				});
				$('#commentform > p input').blur(function(){
					if($(this).val() == ''){
						$(this).closest('p').removeClass('focus');
					}
				});
				$('#commentform > p textarea').blur(function(){
					if($(this).val() == ''){
						$(this).closest('p').removeClass('focus');
					}
				});
			});
		}

		$(".slider-entry").owlCarousel({
      		autoPlay: false, //Set AutoPlay to 3 seconds
      		items : 2,
      		pagination: true,
      		navigation: false,
      		itemsDesktop : [1199,2],
      		itemsDesktopSmall : [979,2],
      		itemsTablet: [600,2],
		    itemsMobile : [480,1]
  		});

		$('#start-date').datepicker({
    		dayNamesMin: [ "S", "M", "T", "W", "T", "F", "S" ],
    		firstDay: 1,
		});
		$('#end-date').datepicker({
    		dayNamesMin: [ "S", "M", "T", "W", "T", "F", "S" ],
    		firstDay: 1,
		});
		$('.k2t-select-checkin').datepicker({});
		$('.k2t-select-checkout').datepicker({});

		$(".k2t-thumb-gallerys").owlCarousel({
      		autoPlay: 3000, //Set AutoPlay to 3 seconds
      		items : 1,
      		pagination: false,
			navigationText:[
				"<i class='zmdi zmdi-arrow-left btn-ripple'></i>",
				"<i class='zmdi zmdi-arrow-right btn-ripple'></i>"
			],
      		navigation: true,
      		itemsDesktop : [1199,1],
      		itemsDesktopSmall : [979,1],
      		itemsTablet: [600,1],
		    itemsMobile : [480,1]
  		});

	});

	$(window).load(function() {

		/*  [ Check scroll ]
		- - - - - - - - - - - - - - - - - - - - */
		i();
		$(document).mousewheel(function(event, delta) {
            if ($(this).scrollTop() > 50) {
				$('.k2t-btt').fadeIn('slow');
				$('body').addClass('scroll-dow');
			} else {
				$('.k2t-btt').fadeOut('slow');
				$('body').removeClass('scroll-dow');
			}
			var scrollBottom = $(document).height() - $(window).height() - $(window).scrollTop();
			if (scrollBottom < 50) {
				$('body').addClass('scroll-bottom');
			} else {
				$('body').removeClass('scroll-bottom');
			}
			i();
			set_current_menu_for_scroll();
        });

		/*  [ Page loader effect ]
		- - - - - - - - - - - - - - - - - - - - */
		$( '#loader' ).delay(100).fadeOut();
		$( '#loader-wrapper' ).delay(100).fadeOut( 'slow' );
		setTimeout(function(){
			$( '#loader-wrapper' ).remove();
		}, 200);

		
		/*  [ Menu One Page ]
		- - - - - - - - - - - - - - - - - - - - */
		var headerH = $(".k2t-header-mid").height();
		var adminbar = $("#wpadminbar").height();
		if (!adminbar) adminbar = 0;
		function i() {
			var e = "";
			var t = "";
			$(".k2t-header .k2t-menu > li").each(function(e) {
				var n = $(this).find("a").attr("href");
				var r = $(this).find("a").attr("data-target");
				if ($(r).length > 0 && $(r).position().top - headerH <= $(document).scrollTop()) {
					t = r
				}
			});
		}
		function set_current_menu_for_scroll(){
			var menu_arr = [];
			var i =  0;
			$(".k2t-header .k2t-menu > li").each(function(e) {
				var n = $(this).find("a").attr("href");
				if (n.charAt(0) == "#" && n.length > 2) {
					menu_arr[i] = n.substr(1, n.length - 1);
					i++;
				}
			});
			if (menu_arr.length > 0){
				jQuery.each( menu_arr, function(){
					var offset = $("#" + this).offset();
					var posY = offset.top - $(window).scrollTop();
					var posX = offset.left - $(window).scrollLeft(); 
					if(posY > 0){
						var new_active = "#" + this;
						if( jQuery(".k2t-header .k2t-menu > li.active > a").attr("href") == new_active  )
						{}else{
							jQuery(".k2t-header .k2t-menu > li.active").removeClass("active");
							jQuery("[href=#" + this + "]").parent("li").addClass("active");
						}
						return false;
					}
				});
			}
		}
		var n = 1e3;
		var r = "#" + $(".k2t-content").attr("id");
		$("body").on("click", ".k2t-header .k2t-menu > li > a", function() {
			var e = $(this).attr("href");
			var i = $(this).attr("data-target");

			$(".k2t-header .k2t-menu > li").each(function(){
				$(this).removeClass("active");
			});
			$(this).parent("li").addClass("active");
			if (e.charAt(0) == "#") {
				i = e
			}
			if ($(i).length > 0) {
				if (e == r) {
					$("html,body").animate({
						scrollTop: 0
					}, n, "easeInOutQuart")
				} else {
					$("html,body").animate({
						scrollTop: $(i).offset().top - headerH - adminbar
					}, n, "easeInOutQuart")
				}
				return false
			}
		});
	});

	var __k2t_check_updown = 0;
	function scrollFunc(e) {
		if ( typeof scrollFunc.x == 'undefined' ) {
			scrollFunc.x=window.pageXOffset;
			scrollFunc.y=window.pageYOffset;
		}
		var diffX=scrollFunc.x-window.pageXOffset;
		var diffY=scrollFunc.y-window.pageYOffset;

		if( diffX<0 ) {
			// Scroll right
		} else if( diffX>0 ) {
			// Scroll left
		} else if( diffY<0 ) {
			// Scroll down
			__k2t_check_updown = -1;
		} else if ( window.pageYOffset == 0 ) {
			__k2t_check_updown = 0;
		}else if( diffY>0 ) {
			// Scroll up
			__k2t_check_updown = 1;
		} else {
			__k2t_check_updown = 0;
			// First scroll event
		}
		scrollFunc.x=window.pageXOffset;
		scrollFunc.y=window.pageYOffset;
	}

})(jQuery);

