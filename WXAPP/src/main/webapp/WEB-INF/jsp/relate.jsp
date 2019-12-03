<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>关联结果</title>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/> 
    <link rel="stylesheet" href="../statics/wx/css/other.css"/> 
</head>
<body>
   	<div class="js_dialog" id="Dialog" style="display: none;position:absolute;z-index:99;">
      	<div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="DialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="http://tship.enn.cn/KLAPP/servlet/otherMainPage?openid=${openid}" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
	<script> 
		$(function(){
     		$('#DialogContent').html('${msg}');
    		$('#Dialog').fadeIn(10);
		});
    </script> 
</body>
</html>