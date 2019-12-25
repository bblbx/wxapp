/**
 * 
 */
	 $(function(){
		    $('.shaixuan_panelcont').on('click', '.multiple',
	    	    function() {
			    	let $this = $(this);
			   	    $this.hasClass('active') ? $this.removeClass('active') : $this.addClass('active');
	    	});
		    $('.shaixuan_panelcont').on('click', '.single',
		    	    function() {
			    	let $this = $(this);
			    	$this.hasClass('active') ? $this.removeClass('active') : $this.addClass('active').siblings().removeClass('active');
		    });
		    $('.submit').click(function(){
		    	submit();
		    });
		    $('.reset').click(function(){
		    	 $('.shaixuan_panelcont').find("span").removeClass('active');
		    });
		    $('.shade').click(function(){
		    	submit();
		    	$('.shaixuan_paneldiv').fadeToggle(100);
		    });
	 })
	 function getSelected(name){
		 let ss = [];
		 $('.selectbox[data-selectbox-name='+name+']').find("span").each(function() {
             let $tag = $(this);
             if ($tag.hasClass('active')) {
            	 ss.push($tag.attr('data-'+name));
             }
         });
		 return ss;
	 }
	 function submit(){
		 let ageArr = getSelected('age'),county=getSelected('county');
		 alert(ageArr+county)
	 }