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
	 <script type="text/javascript" src="../statics/js/menudown.js?v=1.0"></script>
	 <script type="text/javascript" src="../statics/js/load-min.js?v=1.0"></script>
	 <script type="text/javascript" src="../statics/js/productlist.js?v=1.0"></script>
	 <link href="../statics/css/common.css?v=1.0" rel="stylesheet" type="text/css">
	 <link href="../statics/css/menudown.css?v=1.0" rel="stylesheet" type="text/css">
	 <link href="../statics/css/productlist.css?v=1.0"  rel="stylesheet" type="text/css">
	 <link href="../statics/css/load.css?v=1.0"  rel="stylesheet" type="text/css">
</head>
<body >
<div class="jq22-flexView">
	<header class="jq22-navBar topmenu">
	<dt>
		<div class="selectlist">
			<div class="select_textdiv">
				<input type="hidden" value="0" name="ageOrder"/>
				<p class="s_text">年龄升序</p><span class="down"><img src="../statics/img/down.png"></span>
			</div>
			<div class="select_textul">
				<ul class="select_first_ul">
					<li class="focus">
						<p data-value='0'>年龄升序</p>
					</li>
					<li>
						<p data-value='1'>年龄降序</p>
					</li>
				</ul>
			</div>
		</div>
	</dt>
		<dt>
		<div class="selectlist">
			<div class="select_textdiv">
				<input type="hidden" value="1" name="viewOrder"/>
				<p class="s_text">点击量降序</p><span class="down"><img src="../statics/img/down.png"></span>
			</div>
			<div class="select_textul">
				<ul class="select_first_ul">
					<li >
						<p data-value='0'>点击量升序</p>
					</li>
					<li>
						<p data-value='1'>点击量降序</p>
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
			<div class="shaixuan_panelcont" style="z-index: 10;">
			<div class="selectbox" data-selectbox-name="age">
				<div class="title">
				<span>年龄</span>
				</div>
				<div class="content" style="text-align: center;">
				<c:forEach var="item" begin="0"  end="17">
				<span class="multiple" data-age=${item}>${item}</span>
				</c:forEach>
				</div>
			</div>
			<div class="selectbox" data-selectbox-name="county">
				<div class="title">
				<span>区县</span>
				</div>
				<div class="content" style="text-align: center;">
				<c:forEach var="item" items="${county}">
				<span class="single" data-county=${item['Name']}>${item['Name']}</span>
				</c:forEach>
				</div>
			</div>
			</div>
			 <div class="footer-navBar" style="z-index: 10;">
			 <a class="submit">确定</a> <a  class="reset">重置</a>
			</div>
			<div class="shade"></div>
			</div>
		</div>
	</dt>
	</header>
	<div class="jq22-scrollView" id="jq22-scrollView">
		<div class="jq22-limit-box" id="viewcompanydiv">
		<c:forEach var="item"  items="${data}">
			<a href="../company/detail?id=${item['CompanyID']}&grade=${item['grade']}&sphere=${item['sphere']}&openid=${openid}" class="jq22-flex b-line">
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
var sphere = '${sphere}',grade='${grade}',openid='${openid}';
var ageArr =[],county=[];
var page=2;
var now=1;
//var winH = $(window).height(); //页面可视区域高度
var hasall=false;
//滚动触发
$('.jq22-scrollView').scroll(function () {
	var $this =$(this);
	var pageH = $this.get(0).scrollHeight;
	var winH = $this.innerHeight();         //盒子高度
	var scrollT = $this.scrollTop(); ////滚动条top值
	var aa = (pageH - winH - scrollT) / winH;
	if (aa <= 0.01 && !hasall && page != now) {
		now = page;
		var postData = {};
       postData.age=ageArr+"";
       postData.county=county+"";
       postData.viewOrder=$("input[name=viewOrder]").val();
       postData.ageOrder=$("input[name=ageOrder]").val();
       postData.grade=grade;
       postData.sphere=sphere;
       postData.openid=openid;
       postData.page=page;
		addmask('jq22-scrollView');
		$.ajax({
			type : 'POST',
			url : "../company/getcompanylist",
			data : postData,
			success : function(serverData) {
				if (serverData.success == 'true') {
					if (serverData.msg.length > 0) {
						for (var i = 0; i < serverData.msg.length; i++) {
							var html = "<a href='../company/detail?id="+serverData.msg[i].CompanyID+"&grade=${grade}&sphere=${sphere}&openid=${openid}' class='jq22-flex b-line'>"+
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
					removemask('jq22-scrollView');
				} else {
					hasall = true;//已经到底
					$("#end").show();
					removemask('jq22-scrollView');
				}
			},
			dataType : "json"
		});
	}
});
</script>
</html>