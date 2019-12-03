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
		<div class="center position"><b>气源供应商</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;">
		<c:forEach var="place"  items="${places}">
			<div class="weui-form-preview">
	            <div class="weui-form-preview__hd">
	                <a href="../servlet/otherweb?requestpage=liquid/PlaceEdit&openid=${openid}&callliquidid=${place['Guid']}" >
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">供应商名称</label>
	                    <em class="weui-form-preview__value">${place['PlaceName']}</em>
	                </div>
	                 </a>
	            </div>
	            <div class="weui-form-preview__bd">
	            
	                 <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">创建时间:</label>
	                    <span class="weui-form-preview__value">${place['CreateTime']}</span>
	                </div>
	            
	             
	            </div>
	      
	        </div>
	        <br>
        </c:forEach>
        <br>
    </div>
	    
    <div class="foot" style="background-color: #F2F2F2;z-index: 100;"> 
            <div class="weui-btn-area" style="float:left;margin-top:8px;">
   				<a href="../servlet/otherweb?requestpage=liquid/PlaceAdd&openid=${openid}" class="weui-btn weui-btn_primary" id="btn" style="background-color: #fff;font-size:0.8em;color:#000000;border:1px solid rgba(0,0,0,.2)">添加</a>
  			</div>
      
  </div>
	
    <div class="js_dialog" id="RepairTipsDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairTipsDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:$('#RepairTipsDialog').fadeOut(10);" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
    <div class="js_dialog" id="RepairTipsRefreshDialog" style="display: none;">
        <div class="weui-mask"></div>
        <div class="weui-dialog">
            <div class="weui-dialog__hd"><strong class="weui-dialog__title">提示</strong></div>
            <div class="weui-dialog__bd" id="RepairTipsRefreshDialogContent">提示</div>
            <div class="weui-dialog__ft">
                <a href="javascript:repairRequstRefresh();" class="weui-dialog__btn weui-dialog__btn_primary">知道了</a>
            </div>
        </div>
    </div>
    
	<div id="loadingToast" style="display:none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">保存中</p>
        </div>
    </div>
</body>
</html>