<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<head>
    <meta charset="UTF-8">
    <meta HTTP-EQUIV="pragma" CONTENT="no-cache">
    <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<meta content="telephone=no" name="format-detection">
	 <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
	 <script type="text/javascript" src="../statics/js/menudown.js?v=11"></script>
	 <script type="text/javascript" src="../statics/js/common.js"></script>
	 <link href="../statics/css/common.css?v=1" rel="stylesheet" type="text/css">
	 <link href="../statics/css/menudown.css" rel="stylesheet" type="text/css">

</head>
<body >
<section class="jq22-flexView">
	<header class="jq22-navBar topmenu">
	<dt>
		<div class="selectlist">
			<div class="select_textdiv">
				<input type="hidden" value="年龄升序" name="ageOrder"/>
				<p class="s_text">年龄升序</p><span class="down"><img src="../statics/img/down.png"></span>
			</div>
			<div class="select_textul">
				<ul class="select_first_ul">
					<li class="focus">
						<p>年龄升序</p>
					</li>
					<li>
						<p>年龄降序</p>
					</li>
				</ul>
			</div>
		</div>
	</dt>
		<dt>
		<div class="selectlist">
			<div class="select_textdiv">
				<input type="hidden" value="" name="viewOrder"/>
				<p class="s_text">点击量</p><span class="down"><img src="../statics/img/down.png"></span>
			</div>
			<div class="select_textul">
				<ul class="select_first_ul">
					<li >
						<p>点击量降序</p>
					</li>
					<li>
						<p>点击量升序</p>
					</li>
				</ul>
			</div>
		</div>
	</dt>
		<dt>
		<div class="selectlist">
			<div class="shaixuan_textdiv">
				<span style="width:3rem;height:100%;">筛选
				<img src="../statics/img/shaixuan.png" style="width:1rem;"></span>
			</div>
			<div class="shaixuan_paneldiv">
			<div class="shaixuan_panelcont">
			<input type="checkbox" >
						111 <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>
			woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi <br/>woshi333 <br/>
			</div>
			 <div class="footer-navBar" >
			 <a>确定</a> <a>重置</a>
			</div> 
			</div>
		</div>
	</dt>
	</header>
	<section class="jq22-scrollView">
		<div class="jq22-limit-box" id="viewcompanydiv">
		<c:forEach var="item"  items="${data}">
			<a href="../company/detail?id=${item['CompanyID']}&grade=${item['grade']}&sphere=${item['sphere']}" class="jq22-flex b-line">
			<div class="jq22-flex-time-img">
			<img src="${item['ImgUrl']}" alt="">
			</div>
			<div class="jq22-flex-box">
			<h1>${item['SimpleName']}</h1>
			<div class="jq22-flex jq22-flex-clear-pa">
			<div class="jq22-flex-box">
			<div style="float: left;">
			<h3>服务年龄:<font color="red">${item['BeginAge']}-${item['EndAge']}岁</font></h3>
			</div>
			<div style="float: right;">
			<h3>点击量:${item['VisitNum']}</h3>
			</div>
			</div>
			</div>
			<h2>主营项目:${item['MainBusiness']}</h2>
			</div>
			</a>
			<hr/>
		</c:forEach>
		</div>
		<div style="text-align: center; color: #DDDDDD; display: none;" id="end">--已经到底了--</div>
	</section>
</section>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name=ageOrder]").on('change',function(){
		alert(this.value+""+$("input[name=viewOrder]").val());
	});
	$("input[name=viewOrder]").on('change',function(){
		alert(this.value+""+$("input[name=ageOrder]").val());
	});
})



var page=2;
var now=1;
var winH = $(window).height(); //页面可视区域高度
var hasall=false;
 //滚动触发
$('section').scroll(function () {
	var pageH = $(document.body).height();
	var scrollT = $(window).scrollTop(); //滚动条top
	var aa = (pageH - winH - scrollT) / winH;
	if (aa <= 0.01 && !hasall && page != now) {
		now = page;
		var postData = {};
		postData.page = page;
		postData.openid = "${openid}";
		postData.grade = "${grade}";
		postData.sphere = "${sphere}";
		loadingShow('viewcompanydiv');
		$.ajax({
			type : 'POST',
			url : "../company/getcompanylist",
			data : postData,
			success : function(serverData) {
				if (serverData.success == 'true') {
					if (serverData.msg.length > 0) {
						for (var i = 0; i < serverData.msg.length; i++) {
							var html = "<a href='../company/detail?id="+serverData.msg[i].CompanyID+"&grade=${grade}&sphere=${sphere}' class='jq22-flex b-line'>"+
							"<div class='jq22-flex-time-img'>"+
							"<img src='"+serverData.msg[i].ImgUrl+"' >"+
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
							$("#viewcompanydiv").append(html);
						} 
						if (serverData.msg.length < 10) {
							hasall = true;//已经到底
							$("#end").show();
						}
						page++;
					} else {
						hasall = true;//已经到底
						$("#end").show();
					}
					loadingHide();
				} else {
					hasall = true;//已经到底
					$("#end").show();
					loadingHide();
				}
			},
			dataType : "json"
		});
	}
});

</script>
</html>