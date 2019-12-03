<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>昆仑新奥</title>
    <link rel="stylesheet" href="../statics/wx/css/weui.min.css?version=1.0"/> 
    <link rel="stylesheet" href="../statics/wx/css/other.css"/> 
    <script type="text/javascript" src="../statics/js/jquery-3.2.1.min.js"></script>
    <script> 
    	function selectDate(){
    		var financedate = $('#salessummaryreportsearchdate').val();
    		$(location).attr('href', '../servlet/otherweb?requestpage=SalesSummaryReport&openid=${openid}&financedate='+financedate);
		}
	</script>
</head>
<body>
	<div class="header">
		<div class="left"><a href="../servlet/webhome?requestpage=other&openid=${openid}"><img src="../statics/img/back.png"></a></div>
		<div class="center position"><b>销量汇总</b></div>
		<div class="right"><a href="../servlet/webhome?requestpage=other&openid=${openid }"><img src="../statics/img/home.png"></a></div>
	</div>
	
	<div class="weui-cells weui-cells_form" style="margin-top: 55px;">
		<div class="weui-cell">
        	<div class="weui-cell__hd"><label for="" class="weui-label">日期</label></div>
            <div class="weui-cell__bd">
            	<input class="weui-input" type="date" id="salessummaryreportsearchdate" value="${financedate}" onchange="javascript:selectDate();"/>
            </div>
        </div>
		
		<div class="weui-cell">
       		<div class="weui-cell__hd"><label class="weui-label">产品类型</label></div>
       		<div class="weui-cell__hd"><label class="weui-label">销量(升)</label></div>
       		<div class="weui-cell__hd"><label class="weui-label">销量(吨)</label></div>
        </div>
        
        <c:forEach var="salesSummary"  items="${salessummarylist}">
        	<div class="weui-cell">
       			<div class="weui-cell__hd"><label class="weui-label">${salesSummary['Name']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label">${salesSummary['SalesCount']}</label></div>
       			<div class="weui-cell__hd"><label class="weui-label">${salesSummary['SalesCountDun']}</label></div>
        	</div>
        </c:forEach>
	</div>
	<div class="weui-form-preview__ft">
        <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:$(location).attr('href', '../servlet/otherweb?requestpage=LPGSalesReport&openid=${openid}');">LPG销量</a>
        <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:$(location).attr('href', '../servlet/otherweb?requestpage=OilSalesReport&openid=${openid}');">油品销量</a>
        <a class="weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:$(location).attr('href', '../servlet/otherweb?requestpage=MaoYiReport&openid=${openid}');">贸易量 </a>
    </div>
</body>
</html>