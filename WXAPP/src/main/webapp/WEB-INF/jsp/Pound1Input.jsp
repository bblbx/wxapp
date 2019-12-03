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
		<div class="center position"><b>一次磅码数</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="page__bd" style="margin-top: 55px;">
		<c:forEach var="callliquidRequest"  items="${callliquidRequest}">
			<div class="weui-form-preview">
	            <div class="weui-form-preview__hd">
	                <a href="../servlet/otherweb?requestpage=liquid/Pound1InputOrder&openid=${openid}&callliquidid=${callliquidRequest['ID']}" >
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">叫液单号</label>
	                    <em class="weui-form-preview__value">${callliquidRequest['ID']}</em>
	                </div>
	                 </a>
	            </div>
	            <div class="weui-form-preview__bd">
	        
	             	<div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">叫液站点</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['StationName']}</span>
	                </div>
	                 <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">叫液量</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['LiquidCount']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">联系人</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['ContactPerson']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">联系电话</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['ContactTel']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">期望到达时间</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['ExpectedArriveTime']}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                    <label class="weui-form-preview__label">订单状态</label>
	                    <span class="weui-form-preview__value">${callliquidRequest['Status']}</span>
	                     
	                </div>
	             
	            </div>
	           <%--  <div class="weui-form-preview__ft">
	                <a class="weui-form-preview__btn weui-form-preview__btn_default" href="javascript:$('#RepairmanSearchDialog').fadeIn(10);$('#hiddenrepairorderid').val('${repairRequest['OrderID']}');">委派</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:repairArriveStation('${repairRequest}');">到达</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="../servlet/otherweb?requestpage=repair/DoRepair&openid=${openid}&repairorderid=${repairRequest['OrderID']}&type=YC">远程</a>
	                <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="../servlet/otherweb?requestpage=repair/DoRepair&openid=${openid}&repairorderid=${repairRequest['OrderID']}&type=XC">现场</a>
	            </div> --%>
	        </div>
	        <br>
        </c:forEach>
        <br>
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