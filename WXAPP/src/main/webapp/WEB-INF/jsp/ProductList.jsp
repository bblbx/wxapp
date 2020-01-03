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
	 <script type="text/javascript" src="../statics/js/productlist.js?v=1.1"></script>
	 <link href="../statics/css/common.css?v=1.0" rel="stylesheet" type="text/css">
	 <link href="../statics/css/menudown.css?v=1.1" rel="stylesheet" type="text/css">
	 <link href="../statics/css/productlist.css?v=1.0"  rel="stylesheet" type="text/css">
	 <link href="../statics/css/load.css?v=1.0"  rel="stylesheet" type="text/css">
	 <script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
</head>
<body >
<div class="jq22-flexView">
	<header class="jq22-navBar topmenu">
	<dt>
		<div class="selectlist">
			<div class="select_textdiv">
				<input type="hidden" value="0" name="ageOrder"/>
				<p class="s_text" name="ageOrderTxt">年龄升序</p><span class="down"><img src="../statics/img/down.png"></span>
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
				<p class="s_text" name="viewOrderTxt">点击量降序</p><span class="down"><img src="../statics/img/down.png"></span>
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
			<div class="selectbox" data-selectbox-name="search">
				<div class="title">
				<span>自定义搜索(公司名或地址，最长15字符。)</span>
				</div>
				<div class="content" style="text-align: left;">
				<input name="search" style="margin: 0.5rem; border: 1px solid #fe6604;width: 90%;font-size: 0.8rem;height: 1.5rem;" maxlength="15">
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
		</div>
		<div style="text-align: center; color: #DDDDDD; display: none;" id="end">--已经到底了--</div>
	</section>
</section>
</body>
<script type="text/javascript">
var sphere = '${sphere}',grade='${grade}',openid='${openid}',ageViewOrder='age',city='${city}',oth='${oth}';
var page=1,now=1,hasall=false;
var ageArr =[],county=[];
if(oth!=null &&oth!=""){
	let oths = oth.split(";");
	if(oths[0]=='1'){
		$("input[name=ageOrder]").val(oths[0]);
		$("p[name=ageOrderTxt]").val("年龄降序");
	}
	if(oths[1]=='0'){
		$("input[name=viewOrder]").val(oths[1]);
		$("p[name=viewOrderTxt]").val("点击量升序");
	}
	ageViewOrder=oths[2];
	if(oths[3]!=""){
		ageArr = oths[3].split(",");
		ageArr.forEach(function(value,index,self){
			$('span[data-age='+value+']').addClass('active');
		});
	}
	if(oths[4]!=""){
		county.push( oths[4]);
		$('span[data-county='+oths[4]+']').addClass('active');
	}
	if(oths[5]!=""){
		$('input[name=search]').val(oths[5]);
	}
}
//滚动触发
$('.jq22-scrollView').scroll(function () {
	var $this =$(this);
	var pageH = $this.get(0).scrollHeight;
	var winH = $this.innerHeight();         //盒子高度
	var scrollT = $this.scrollTop(); ////滚动条top值
	var aa = (pageH - winH - scrollT) / winH;
	if (aa <= 0.01 && !hasall && page != now) {
		now = page;
		submit(page);
	}
});

wx.config({
    debug: false,
    appId: "${appId }",
    timestamp: "${timeStamp }",
    nonceStr: "${nonceStr }",
    signature: "${signature }",
    jsApiList: [
	'hideMenuItems'
    ]
});
wx.ready(function(){
	wx.hideMenuItems({
		  menuList: ['menuItem:editTag','menuItem:delete','menuItem:copyUrl',
		             'menuItem:originPage','menuItem:readMode','menuItem:openWithQQBrowser',
		             'menuItem:openWithSafari','menuItem:share:email','menuItem:share:brand'] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
	});
});
</script>
</html>