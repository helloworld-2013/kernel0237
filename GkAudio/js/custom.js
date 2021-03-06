/*-----------------------------------------------------------------------------------
/*
/* Custom JS
/*
-----------------------------------------------------------------------------------*/
/* Start Document */

(function($) {
		  $(document).ready(function(){
									 
/*----------------------------------------------------*/
/*	Accordion
/*----------------------------------------------------*/
	$('.accordion-toggle').click(function(){
	
		$('.accordion').find('.accordion-toggle').removeClass('active');		
		$(this).addClass('active');

	});
	
/*----------------------------------------------------*/
/*	Toggle
/*----------------------------------------------------*/	
	$('.toggler').click(function(){
		
		if($(this).hasClass('active'))
			$(this).removeClass('active')
		else
			$(this).addClass('active')
	});

/*----------------------------------------------------*/
/*	Move To Top
/*----------------------------------------------------*/

	$("#movetotop").hide();
	$(function () {
		$(window).scroll(function () {
			if ($(this).scrollTop() > 100) {
				$('#movetotop').fadeIn();
			} else {
				$('#movetotop').fadeOut();
			}
		});

		// scroll body to 0px on click
		$('#movetotop a').click(function () {
			$('body,html').animate({
				scrollTop: 0
			}, 500);
			return false;
		});
	});

/*----------------------------------------------------*/
/*	Responsive Selectnav
/*----------------------------------------------------*/

selectnav('menu-lamp', {
    		nested: true,
    		label: '',
			indent: '-'
		});	
	
/*----------------------------------------------------*/
/*	Tabs
/*----------------------------------------------------*/

$('.nav-tabs a').click(function (e) {
	e.preventDefault();
	$(this).tab('show');
})

/*----------------------------------------------------*/
/*	Flickr Feed
/*----------------------------------------------------*/
jQuery('#flickr').jflickrfeed({
	limit: 8,
	qstrings: {
		id: 'Flickr_Gallery_Id'
	},
	itemTemplate: '<li><a href="{{image_s}}" data-rel="prettyPhoto"><img alt="{{title}}" src="{{image_s}}" /></a></li>'
});


/*----------------------------------------------------*/
/*	Element Fadein On Scroll Page
/*----------------------------------------------------*/
effect = $(".effect-loaded").fadeTo(0, 1);

$(window).load( function () {  				  
						  
	  effect.each(function(i) {
						 
        a = $(this).offset().top + $(this).height();
        b = $(window).scrollTop() + $(window).height();
        if (a < b) $(this).fadeTo(300,1);
		
    });  
});  
$(window).scroll(function() {
   if($(window).scrollTop() + $(window).height() <= $(document).height()) {
		
	 effect.each(function(i) {
						 
        a = $(this).offset().top + $(this).height();
        b = $(window).scrollTop() + $(window).height();
        if (a < b) $(this).fadeTo(500,1);
		
    });
	
   }
});

/*----------------------------------------------------*/
/*	PrettyPhoto
/*----------------------------------------------------*/

		$("a[data-rel^='prettyPhoto']").prettyPhoto({
			animation_speed: 'fast', /* fast/slow/normal */
			slideshow: 5000, /* false OR interval time in ms */
			autoplay_slideshow: false, /* true/false */
			opacity: 0.80, /* Value between 0 and 1 */
			show_title: true, /* true/false */
			allow_resize: true, /* Resize the photos bigger than viewport. true/false */
			default_width: 500,
			default_height: 344,
			counter_separator_label: '/', /* The separator for the gallery counter 1 "of" 2 */
			theme: 'pp_default', /* light_rounded / dark_rounded / light_square / dark_square / facebook */
			horizontal_padding: 20, /* The padding on each side of the picture */
			hideflash: false, /* Hides all the flash object on a page, set to TRUE if flash appears over prettyPhoto */
			wmode: 'opaque', /* Set the flash wmode attribute */
			autoplay: true, /* Automatically start videos: True/False */
			modal: false, /* If set to true, only the close button will close the window */
			deeplinking: true, /* Allow prettyPhoto to update the url to enable deeplinking. */
			overlay_gallery: true, /* If set to true, a gallery will overlay the fullscreen image on mouse over */
			keyboard_shortcuts: true, /* Set to false if you open forms inside prettyPhoto */
			changepicturecallback: function(){}, /* Called everytime an item is shown/changed */
			callback: function(){}, /* Called when prettyPhoto is closed */
			ie6_fallback: true,
			markup: '<div class="pp_pic_holder"> \
						<div class="ppt">&nbsp;</div> \
						<div class="pp_top"> \
							<div class="pp_left"></div> \
							<div class="pp_middle"></div> \
							<div class="pp_right"></div> \
						</div> \
						<div class="pp_content_container"> \
							<div class="pp_left"> \
							<div class="pp_right"> \
								<div class="pp_content"> \
									<div class="pp_loaderIcon"></div> \
									<div class="pp_fade"> \
										<a href="#" class="pp_expand" title="Expand the image">Expand</a> \
										<div class="pp_hoverContainer"> \
											<a class="pp_next" href="#">next</a> \
											<a class="pp_previous" href="#">previous</a> \
										</div> \
										<div id="pp_full_res"></div> \
										<div class="pp_details"> \
											<div class="pp_nav"> \
												<a href="#" class="pp_arrow_previous">Previous</a> \
												<p class="currentTextHolder">0/0</p> \
												<a href="#" class="pp_arrow_next">Next</a> \
											</div> \
											<p class="pp_description"></p> \
											{pp_social} \
											<a class="pp_close" href="#">Close</a> \
										</div> \
									</div> \
								</div> \
							</div> \
							</div> \
						</div> \
						<div class="pp_bottom"> \
							<div class="pp_left"></div> \
							<div class="pp_middle"></div> \
							<div class="pp_right"></div> \
						</div> \
					</div> \
					<div class="pp_overlay"></div>',
			gallery_markup: '<div class="pp_gallery"> \
								<a href="#" class="pp_arrow_previous">Previous</a> \
								<div> \
									<ul> \
										{gallery} \
									</ul> \
								</div> \
								<a href="#" class="pp_arrow_next">Next</a> \
							</div>',
			image_markup: '<img id="fullResImage" src="{path}" />',
			flash_markup: '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="{width}" height="{height}"><param name="wmode" value="{wmode}" /><param name="allowfullscreen" value="true" /><param name="allowscriptaccess" value="always" /><param name="movie" value="{path}" /><embed src="{path}" type="application/x-shockwave-flash" allowfullscreen="true" allowscriptaccess="always" width="{width}" height="{height}" wmode="{wmode}"></embed></object>',
			quicktime_markup: '<object classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab" height="{height}" width="{width}"><param name="src" value="{path}"><param name="autoplay" value="{autoplay}"><param name="type" value="video/quicktime"><embed src="{path}" height="{height}" width="{width}" autoplay="{autoplay}" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/"></embed></object>',
			iframe_markup: '<iframe src ="{path}" width="{width}" height="{height}" frameborder="no"></iframe>',
			inline_markup: '<div class="pp_inline">{content}</div>',
			custom_markup: '',
			social_tools: '<div class="pp_social"><div class="twitter"><a href="http://twitter.com/share" class="twitter-share-button" data-count="none">Tweet</a><script type="text/javascript" src="http://platform.twitter.com/widgets.js"></script></div><div class="facebook"><iframe src="http://www.facebook.com/plugins/like.php?locale=en_US&href='+location.href+'&amp;layout=button_count&amp;show_faces=true&amp;width=500&amp;action=like&amp;font&amp;colorscheme=light&amp;height=23" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:500px; height:23px;" allowTransparency="true"></iframe></div></div>' /* html or false to disable */
		});

  $("#feedback_me a[data-rel^='prettyPhoto']:first").prettyPhoto({
    custom_markup: '<div class="col-lg-12"> \
    <h3 class="heading effect-loaded">Contact <strong>Us</strong></h3> \
    <form action="" method="" name="frmContact" id="contact-form"> \
      <div class="row no-padding"> \
        <div class="col-lg-12 effect-loaded"> \
          <label>Name</label> \
          <input class="input-contact-form" type="text" name="name" maxlength="50"> \
        </div> \
        <div class="col-lg-12 effect-loaded"> \
          <label>Email</label> \
          <input class="input-contact-form" type="text" name="email" maxlength="20"> \
        </div> \
        <div class="col-lg-12 effect-loaded"> \
          <label>Subject</label> \
          <input class="input-contact-form" type="text" name="subject" maxlength="50"> \
        </div> \
        <div class="col-lg-12 effect-loaded"> \
          <label>Message</label> \
          <p> \
            <textarea class="message-contact-form" name="message" maxlength="1000"></textarea> \
          </p> \
          <p> \
            <input type="button" class="btn btn-success contact-form-button" value="Submit" onclick="sendEmail()"> \
            <input type="reset" class="btn btn-danger contact-form-button" value="Reset"> \
          </p> \
        </div> \
      </div> \
    </form> \
    </div>',
    default_width: 500,
    default_height: 400,
    ajaxcallback: function() {},
    changepicturecallback: function(){},
    callback: function(){},
    social_tools: '',
    allow_resize: true,
    allow_expand: false
  });

/*----------------------------------------------------*/
/*	End Document
/*----------------------------------------------------*/		

});

})(this.jQuery);

function sendEmail() {
	jQuery.post("email.php", jQuery('#contact-form').serialize(), function(response){
		window.parent.jQuery.prettyPhoto.close();
	});
}
