/**
 * 
 */
	 $(function(){
		    $('.shaixuan_panelcont').on('click', '.multiple',
	    	    function() {
			    	let $this = $(this);
			   	    $this.hasClass('active') ? $this.removeClass('active') : $this.addClass('active');
			   	    if($this.hasClass('datacounty') && ($this.siblings().hasClass('active') || $this.hasClass('active'))){
			   	    	$('.datacity[data-city='+$this.parent().attr('data-county-div')+']').addClass('active');
			   	    }else if($this.hasClass('datacounty') && (!$this.siblings().hasClass('active') && !$this.hasClass('active'))){
			   	    	$('.datacity[data-city='+$this.parent().attr('data-county-div')+']').removeClass('active');
			   	    }
	    	});
		    $('.shaixuan_panelcont').on('click', '.single',
		    	    function() {
			    	let $this = $(this);
			    	$this.hasClass('active') ? $this.removeClass('active') : $this.addClass('active').siblings().removeClass('active');
		    });
		    $('.shaixuan_panelcont').on('click', '.datacity',
		    	    function() {
			    	let $this = $(this);
			    	$('div[data-county-div='+$this.attr('data-city')+']').fadeToggle(100).siblings('div').hide();
		    });
		    $('.submit').click(function(){
		    	submit(1);
		    	$('.shaixuan_paneldiv').fadeToggle(100);
		    });
		    $('.reset').click(function(){
		    	 $('.shaixuan_panelcont').find("span").removeClass('active');
		    });
		    $('.shade').click(function(){
		    	submit(1);
		    	$('.shaixuan_paneldiv').fadeToggle(100);
		    });
		    $("input[name=ageOrder]").on('change',function(){
		    	ageViewOrder='age';
		    	submit(1);
			});
			$("input[name=viewOrder]").on('change',function(){
				ageViewOrder='view';
				submit(1);
			});
			submit(1);
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
	 function showDetail(com){
		 let temp=$("input[name=ageOrder]").val()+';'+$("input[name=viewOrder]").val()+";"+ageViewOrder+";"+ageArr+";"+county+";"+$('input[name=search]').val()+";";
		 window.location.href="../company/detail?id="+com+"&openid="+openid+"&sphere="+sphereArr+
		 "&grade="+gradeArr+"&city="+cityArr+"&recomend="+recomendArr+"&county="+countyArr+"&age="+ageArr+
		 "&ageOrder="+$("input[name=ageOrder]").val()+
		 "&viewOrder="+$("input[name=viewOrder]").val()+"&ageViewOrder="+ageViewOrder+
		 "&search="+encodeURI(encodeURI($('input[name=search]').val()));
	 }
	 function submit(p){
		 ageArr = getSelected('age'),sphereArr=getSelected('sphere'),gradeArr=getSelected('grade'),
		 recomendArr=getSelected('recomend'),countyArr=[],cityArr=[];
		 $('.selectbox[data-selectbox-name=area]').find(".datacounty").each(function() {
             let $tag = $(this);
             if ($tag.hasClass('active')) {
            	 countyArr.push($tag.attr('data-county'));
             }
         });
		 $('.selectbox[data-selectbox-name=area]').find(".datacity").each(function() {
             let $tag = $(this);
             if ($tag.hasClass('active')) {
            	 cityArr.push($tag.attr('data-city'));
             }
         });
		//请求参数
        let postData = {};
        postData.age=ageArr+"";
        postData.city=cityArr+"";
        postData.county=countyArr+"";
        postData.viewOrder=$("input[name=viewOrder]").val();
        postData.ageOrder=$("input[name=ageOrder]").val();
        postData.ageViewOrder=ageViewOrder;
        postData.grade=gradeArr+"";
        postData.sphere=sphereArr+"";
        postData.recomend=recomendArr+"";
        postData.openid=openid;
        postData.search=$('input[name=search]').val();
        postData.page=p;
        addmask('jq22-scrollView');
        $.ajax({
            type : "POST",
            url : "../company/getcompanylist",
            data : postData,
            success : function(serverData) {
            	if (serverData.success == 'true') {
					if(p==1){
						$("#viewcompanydiv").html("");
						now=1;
						hasall=false;
					}
 					if (serverData.msg.length > 0) {
 						for (var i = 0; i < serverData.msg.length; i++) {
 							let temp = '<a href="javascript:showDetail(\''+serverData.msg[i].CompanyID+'\')" class="jq22-flex b-line">'+
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
 						page=p+1;
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
