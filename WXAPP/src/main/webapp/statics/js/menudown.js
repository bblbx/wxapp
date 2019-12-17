$(function(){
    var v_width= $(document.body).width();
    var v_height= $(window).height();
    $(".select_textul").width(v_width);
    $(".shaixuan_panelcont").width(v_width*0.8);
    $(".shaixuan_panelcont").height(v_height-$(".shaixuan_textdiv").height()-$(".footer-navBar").height());
    $(".footer-navBar").width($(".shaixuan_panelcont").width());
    $(".footer-navBar").css('bottom',-v_height+$(".shaixuan_textdiv").height());
    
    $(".select_textdiv").click(function(){
		$(this).parent().parent().siblings().find(".select_textul").hide();
		$(this).parent().parent().siblings().find(".shaixuan_paneldiv").hide();
    	$(".select_textdiv").removeClass("divfocus");
    	$(this).addClass("divfocus");
    	$(this).siblings(".select_textul").fadeToggle(100);
        var lilength = $(this).siblings(".select_textul").find("li.focus").has(".select_second_ul").length;
    	if(lilength > 0){
    		$(this).siblings(".select_textul").find("li.focus>.select_second_ul").show();
    	}else{
    		$(".select_first_ul>li>p").css("width","100%");
    	}
    })
	$(".select_first_ul>li>p").click(function(){
		$(".select_second_ul").hide();
		$(this).parent("li").addClass("focus").siblings("li").removeClass("focus");
		var ynul = $(this).parent("li").has(".select_second_ul").length;
        if(ynul == 0){
        	
        	var choose = $(this).text();
			$(this).parents(".select_textul").siblings(".select_textdiv").find(".s_text").text(choose);
			$(this).parents(".select_textul").siblings("input").val(choose);
			$(this).parents(".select_textul").fadeOut(100);
        }else{
        	$(".select_second_ul").hide();
		    $(this).siblings(".select_second_ul").show();
		    event.stopPropagation();
			chooseclick();
        }
		
	});
	
	chooseclick();
	function chooseclick(){
		$(".select_second_ul>li").click(function(){
			var choose = $(this).text();
			$(this).addClass("focusli").siblings("li").removeClass("focusli");
			$(this).parents(".select_textul").siblings(".select_textdiv").find(".s_text").text(choose);
			$(this).parents(".select_textul").siblings("input").val(choose);
			$(this).parents(".select_textul").fadeOut(100);
			
			event.stopPropagation();
		});
	}
    $(".shaixuan_textdiv").click(function(){
		$(this).parent().parent().siblings().find(".select_textul").hide();
    	$(".select_textdiv").removeClass("divfocus");
    	$(this).siblings(".shaixuan_paneldiv").fadeToggle(100);
    })	
})
