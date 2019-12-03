<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>昆仑新奥</title>
    <link rel="stylesheet" href="../statics/wx/css/other.css?version=1.0"/>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/>
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
    <script> 
    
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>驳回派液单</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;">
		<c:forEach var="callliquidRequest"  items="${callliquidRequest}">
			<div class="weui-form-preview">
	            <div class="weui-form-preview__hd">
	                <a href="../servlet/otherweb?requestpage=liquid/ReDistributeLiquid1&openid=${openid}&callliquidid=${callliquidRequest['ID']}" >
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">派液单</label>
	                    <em class="weui-form-preview__value">${callliquidRequest['ID']}</em>
	                </div>
	                 </a>
	            </div>
	            <div class="weui-form-preview__bd">
	        
	             	<div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">车牌号</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['CarNo']}</span>
	                </div>
	                 <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">派液时间</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['CreateTime']}</span>
	                </div>
	             
	            </div>
	        </div>
	        <br>
        </c:forEach>
        <br>
    </div>
</body>
</html>