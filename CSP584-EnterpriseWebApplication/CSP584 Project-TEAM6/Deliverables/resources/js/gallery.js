(function($) {

    /*  [ Init Function ]
	- - - - - - - - - - - - - - - - - - - */
    $(document).ready(function() {

        /* Filter
        ------------------------------------ */
        $( '#cd-dropdown' ).dropdown( {
            gutter : 5,
            stack : false,
            delay : 100,
            slidingIn : 100
        } );

        /* Style hover 2
        ------------------------------------ */
        $(' .mask-hover-2 article ').each( function() { $(this).hoverdir({
            hoverDelay : 75
        }); } );

    	/* Popup
        ------------------------------------ */
        if ($().magnificPopup) {
            /* general */
            $('.k2t-popup-link').magnificPopup({ 
				type: 'image',
			}); // magnificPopup
			
			$('.k2t-video-popup-link').magnificPopup({ 
				type: 'iframe',
			}); // magnificPopup

			$('.k2t-audio-popup-link').magnificPopup({ 
				type:'inline',
	  			midClick: true
			}); // magnificPopup

            /* gallery */
            $('.k2t-popup-gallery').magnificPopup({
                delegate: 'a', // the selector for gallery item
                type: 'image',
                gallery: {
                    enabled: true
                }
            });

        }; // if magnificPopup exists

		if ( $().masonry && $().isotope && $().imagesLoaded ) {
			
			$('.k2t-isotope-wrapper').each(function(){
											 
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


                    $this.find('.filter-list').find('li').eq(0).addClass('active');
                    $this.find('.filter-list').find('li').on('click',function(){
                        if(jQuery(this).hasClass('active')){
                            return false;
                        }
                        var selector = $(this).attr('class');
                        $container.isotope({
                            filter: selector,
                        });
                        $(this).parent().find('li').removeClass('active');
                        $(this).addClass('active')
                    });
					
				}); // imagesLoaded
				
			}); // each .k2t-isotope-wrapper
		} // if isotope

    }); // ready
	
})(jQuery);

