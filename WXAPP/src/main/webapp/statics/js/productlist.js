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
		    	$('.shaixuan_paneldiv').fadeToggle(100);
		    });
		    $('.reset').click(function(){
		    	 $('.shaixuan_panelcont').find("span").removeClass('active');
		    });
		    $('.shade').click(function(){
		    	submit();
		    	$('.shaixuan_paneldiv').fadeToggle(100);
		    });
		    $("input[name=ageOrder]").on('change',function(){
		    	submit();
			});
			$("input[name=viewOrder]").on('change',function(){
				submit();
			});
	 });
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
		 ageArr = getSelected('age'),county=getSelected('county');
		//请求参数
        let postData = {};
        postData.age=ageArr+"";
        postData.county=county+"";
        postData.viewOrder=$("input[name=viewOrder]").val();
        postData.ageOrder=$("input[name=ageOrder]").val();
        postData.grade=grade;
        postData.sphere=sphere;
        postData.openid=openid;
        postData.page='1';
        addmask('jq22-scrollView');
        $.ajax({
            type : "POST",
            url : "../company/getcompanylist",
            data : postData,
            success : function(serverData) {
            	if (serverData.success == 'true') {
            		$("#viewcompanydiv").html("");
 					if (serverData.msg.length > 0) {
 						for (var i = 0; i < serverData.msg.length; i++) {
 							let temp = "<a href='../company/detail?id="+serverData.msg[i].CompanyID+"&grade="+grade+"&sphere="+sphere+"' class='jq22-flex b-line'>"+
 							"<div class='jq22-flex-time-img'>"+
 							(serverData.msg[i].ImgUrl==null?"<img >":"<img src='"+serverData.msg[i].ImgUrl+"' >")+
 							"</div>"+
 							"<div class='jq22-flex-box'>"+
 							"<h1>"+serverData.msg[i].SimpleName+"</h1>"+
 							"<div class='jq22-flex jq22-flex-clear-pa'>"+
 							"<div class='jq22-flex-box'>"+
 							"<div style='float: left;'>"+
 							"<h3>服务年龄:<font color='red'>"+serverData.msg[i].BeginAge+"-"+serverData.msg[i].EndAge+"岁</font></h3>"+
 							"</div>"+
 							"<div style='float: right;'>"+
 							"<h3>点击量:"+serverData.msg[i].VisitNum+"</h3>"+
 							"</div>"+
 							"</div>"+
 							"</div>"+
 							"<h2>主营项目:"+serverData.msg[i].MainBusiness+"</h2>"+
 							"</div>"+
 							"</a><hr/>"
 							;
 							$("#viewcompanydiv").append(temp);
 						} 
 						if (serverData.msg.length < 20) {
 							hasall = true;//已经到底
 							$("#end").show();
 						}
 						page++;
 					} else {
 						hasall = true;//已经到底
 						$("#end").show();
 					}
 					removemask('jq22-scrollView');
 				} else {
 					hasall = true;//已经到底
 					$("#end").show();
 					removemask('jq22-scrollView');
 				}
            },
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
	 }
