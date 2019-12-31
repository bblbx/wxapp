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
	 <script type="text/javascript" src="../statics/js/HappyImage.js"></script>
	 <link href="../statics/css/common.css" rel="stylesheet" type="text/css">
	 <link href="../statics/css/productdetail.css"  rel="stylesheet" type="text/css">
	 <style>

        </style>
</head>
<body  >
<div class="jq22-flexView">
<header class="jq22-navBar" >
<a href="../servlet/otherweb?grade=${grade}&openid=${openid}&sphere=${sphere}&openid=${openid}" class="jq22-navBar-item">
<i class="icon icon-return"></i>
</a>
<div class="jq22-center">
<span class="jq22-center-title">${company[0].SimpleName}</span>
</div>
</header>
<section style="overflow-y:scroll ;">
<div class="slide" id="target-1">
<div>
<c:forEach var="item"  items="${img}">
<c:if test="${item['Type']=='02'}">
<div><img src="${item['ImgUrl']}"></div>
</c:if>
</c:forEach>
</div>
</div>
<div class="item">
<div class="title"><p>品牌名称</p></div>
<div class="content">${company[0].SimpleName}</div>
</div>
<div class="item">
<div class="title"><p>是否连锁品牌</p></div>
<div class="content">${company[0].IsChainBrand?'否':'是'}</div>
</div>
<div class="item">
<div class="title"><p>品牌介绍</p></div>
<c:forEach var="item"  items="${rich}">
<c:if test="${item['Type']=='01'}">
<div class="content">${item['Text']}</div>
</c:if>
</c:forEach>
</div>
<div class="item">
<div class="title"><p>主营项目</p></div>
<div class="content">${company[0].MainBusiness}</div>
</div>
<div class="item">
<div class="title"><p>服务年龄</p></div>
<div class="content">${company[0].BeginAge}-${company[0].EndAge}岁</div>
</div>
<div class="item">
<div class="title"><p>经营地址</p></div>
<div class="content">${company[0].City} ${company[0].County} ${company[0].Address}</div>
</div>
<div class="item">
<div class="title"><p>开办时间</p></div>
<div class="content">${company[0].CompanyStartDate}</div>
</div>
<div class="item">
<div class="title"><p>教学面积</p></div>
<div class="content">${company[0].TeachingArea}平方米</div>
</div>
<div class="item">
<div class="title"><p>露天活面积</p></div>
<div class="content">${company[0].OpenArea}平方米</div>
</div>
<div class="item">
<div class="title"><p>教职人员情况</p></div>
<c:forEach var="item"  items="${rich}">
<c:if test="${item['Type']=='02'}">
<div class="content">${item['Text']}</div>
</c:if>
</c:forEach>
</div>
<div class="item">
<div class="title"><p>开设课程</p></div>
<c:forEach var="item"  items="${rich}">
<c:if test="${item['Type']=='03'}">
<div class="content">${item['Text']}</div>
</c:if>
</c:forEach>
</div>
<div class="item">
<div class="title"><p>现场情况</p></div>
<c:forEach var="item"  items="${rich}">
<c:if test="${item['Type']=='04'}">
<div class="content">${item['Text']}</div>
</c:if>
</c:forEach>
</div>
</section>
<footer class="foot"><a href="tel:${company[0].Phone1}"><div ><img src="../statics/img/phone.png" style="width: 1.5rem;position: relative;bottom: -0.3rem;margin-right: 1rem;">打电话咨询、预约体验课</div></a></footer>
</div>
</body>
<script>
            $( "#target-1" ).HappyImage({
            	effect: "slide",//fade
            	duration: 1000,
            	autoplay: 3000
            });
            /*width: null,
            height: null,
            effect: "slide",
            duration: 700,
            arrow: !0,
            dot: !0,
            defaultIndex: 0,
            arrowHoverShow: !1,
            dotAlign: "center",
            autoplay: 0,
            onChange: a.noop*/
        </script>
</html>