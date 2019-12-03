<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>维修人员管理</title>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/> 
    <link rel="stylesheet" href="../statics/wx/css/other.css"/> 
    <style type="text/css">
    	.placeholder {    
    		margin: 5px;
   	 	padding: 0 10px;
    	background-color: #f7f7f7;
    	height: 2.3em;
    	line-height: 2.3em;
    	text-align: center;
    	color: rgba(0,0,0,.3);}
    </style>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>维修人员管理</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	<div class="page"  style="margin-top: 55px;">
	    <div class="page__bd page__bd_spacing">
	    	<!--  取消新增人员，修改为从阶梯系统创建账号 20190701
	    	<a href="../servlet/otherweb?requestpage=repair/addRepairman&openid=${openid}" >
	        	<div class="weui-flex">
	            	<div class="weui-flex__item"><div class="placeholder">新增人员</div></div>
	        	</div>
	        </a> -->
	        
	        <a href="../servlet/otherweb?requestpage=repair/updateRepairman&openid=${openid}" >
	        	<div class="weui-flex">
		            <div class="weui-flex__item"><div class="placeholder">修改人员</div></div>
		        </div>
	        </a>
	        
	        <a href="../servlet/otherweb?requestpage=repair/viewRepairman&openid=${openid}" >
	        	<div class="weui-flex">
		            <div class="weui-flex__item"><div class="placeholder">查看人员</div></div>
		        </div>
	        </a>
	    </div>
	</div>
</body>
</html>