<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>功能列表</title>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/> 
    <link rel="stylesheet" href="../statics/wx/css/other.css"/> 
</head>
<body>
	<c:forEach var="functionmap"  items="${functions}">
		<h4 class="hea_car_tit">
            <span></span>
            ${functionmap.key}
    	</h4>
    	<div class="weui-grids">
		   <c:forEach var="function"  items="${functionmap.value}">
	        <a href="../servlet/otherweb?requestpage=${function['PageCode']}&openid=${openid}" class="weui-grid">
	            <div class="weui-grid__icon">
	                <img src="../statics/img/${function['Picture']}" alt="">
	            </div>
	            <p class="weui-grid__label">${function['PageName']}</p>
	        </a>
	        </c:forEach>
	    </div>
  	</c:forEach>   
	
   	<div class="js_dialog" id="Dialog" style="display: none;position:absolute;z-index:99;">
      	<div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="DialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:$('#Dialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
      
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
	<script> 
		$(function(){
			var flag = "${flag }";
	     	if(flag == 'false'){
	     		$('#DialogContent').html('请先与阶梯系统中账号绑定并授权后，再使用此公众号中功能。');
	    		$('#Dialog').fadeIn(10);
	     	}
		});
    </script> 
</body>
</html>